package model;

import java.util.ArrayList;
import model.gameEntities.enemies.Enemy;
import model.gameEntities.towers.Tower;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class Board implements PropertyChangeListener {

    private int level;
    private int difficulty;
    private ArrayList<Cells> cells;
    private Waves waves = null;
    private Wave currentWave = new Wave(); // To avoid null pointer exception
    private CopyOnWriteArrayList<Enemy> currentEnnemies = new CopyOnWriteArrayList<Enemy>();
    private ArrayList<Tower> towers = new ArrayList<Tower>();
    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    private int totalMoney;
    private int totalLives;
    private int cellSize = 32;
    private int totalWaves;
    private int currentWaveNumber = 0;
    private boolean isInfinite = false;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    // TODO: Set a specific tile as a base

    public Board(int level, int difficulty) {
        switch (level) {
            case 1:
                switch (difficulty) {
                    case 1:
                        totalMoney = 150;
                        totalLives = 20;
                        break;
                    case 2:
                        totalMoney = 120;
                        totalLives = 10;
                    case 3:
                        totalMoney = 100;
                        totalLives = 5;
                    default:
                        totalMoney = 200;
                        totalLives = 20;
                        break;
                }
            break;
            case 2:
                switch (difficulty) {
                    case 1:
                        totalMoney = 200;
                        totalLives = 20;
                        break;
                    case 2:
                        totalMoney = 150;
                        totalLives = 10;
                    case 3:
                        totalMoney = 120;
                        totalLives = 5;
                    default:
                        totalMoney = 200;
                        totalLives = 20;
                        break;
                }
            break;
            case 3:
                switch (difficulty) {
                    case 1:
                        totalMoney = 250;
                        totalLives = 20;
                        break;
                    case 2:
                        totalMoney = 200;
                        totalLives = 10;
                    case 3:
                        totalMoney = 150;
                        totalLives = 5;
                    default:
                        totalMoney = 200;
                        totalLives = 20;
                        break;
                }
            break;
        }
        this.level = level;
        if (difficulty == 4) {
            isInfinite = true;
        }
        this.difficulty = difficulty;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("baseDestroyed")) {
            takeDamage();
            propertyChangeSupport.firePropertyChange("baseDestroyed", null, evt.getNewValue());
            ((Enemy) evt.getOldValue()).removeAllPropertyChangeListeners();
            currentEnnemies.remove(evt.getOldValue());
        }
        if (evt.getPropertyName().equals("enemySpawned")) {
            Enemy enemy = (Enemy) evt.getNewValue();
            propertyChangeSupport.firePropertyChange("enemySpawned", null, enemy);
            currentEnnemies.add(enemy);
            enemy.addPropertyChangeListener(this);
        }
        if (evt.getPropertyName().equals("enemyReward")) {
            System.out.println("Enemy killed");
            int reward = (int) evt.getNewValue();
            totalMoney += reward;
            propertyChangeSupport.firePropertyChange("enemyReward", null, reward);
            ((Enemy) evt.getOldValue()).removeAllPropertyChangeListeners();
            currentEnnemies.remove(evt.getOldValue());
        }
        if (evt.getPropertyName().equals("projectileCreated")) {
            Projectile projectile = (Projectile) evt.getNewValue();
            projectiles.add(projectile);
            projectile.addPropertyChangeListener(this);
            propertyChangeSupport.firePropertyChange("projectileCreated", null, projectile);
        }
        if (evt.getPropertyName().equals("projectileDestroyed")) {
            System.out.println("Projectile destroyed in board");
            Projectile projectile = (Projectile) evt.getOldValue();
            projectiles.remove(projectile);
            projectile.removeAllPropertyChangeListeners();
            propertyChangeSupport.firePropertyChange("projectileDestroyed", projectile, null);
        }
    }

    public void init() {
        waves = WavesBuilder.getWaves(level, difficulty, this, currentWaveNumber);
        this.cellSize = cells.get(0).getSize();
        totalWaves = waves.getWavesTotal();
        currentWave = waves.getCurrentWave();
        currentWave.start();
        currentWaveNumber++;
    }

    public void nextWave() {
        if (waves.nextWave()) {
            currentWave = waves.getCurrentWave();
            currentWave.start();
            currentWaveNumber++;
            propertyChangeSupport.firePropertyChange("waveStarted", null, currentWave);
        }
    }

    public void update(long deltaT) {
        for (Tower t : towers) {
            t.update();
        }
        Iterator<Enemy> iterator = currentEnnemies.iterator();
        while (iterator.hasNext()) {
            Enemy e = iterator.next();
            e.update(deltaT);
        }
        ArrayList<Projectile> projectilesCopy = new ArrayList<Projectile>(projectiles);
        for (Projectile p : projectilesCopy) {
            p.update(deltaT);
        }
        if (hasLost()) {
            propertyChangeSupport.firePropertyChange("gameLost", null, null);
        }
        if (currentWave.isFinished() && currentEnnemies.isEmpty()) {
            // System.out.println("Wave finished");
            nextWave();
            if (isInfinite && waves.isFinished()) {
                waves = WavesBuilder.getWaves(level, difficulty, this, currentWaveNumber);
                nextWave();
            }
            if (waves.isFinished() && currentWave.isFinished()) {
                System.out.println("You won");
                propertyChangeSupport.firePropertyChange("gameWon", null, null);
            }
        }
    }

    public Cells getCell(int i) {
        return cells.get(i);
    }

    public Cells getCellAt(int x, int y) {
        for (Cells cell : cells) {
            if (x >= cell.getX() && x <= cell.getX() + cell.getSize() &&
                    y >= cell.getY() && y <= cell.getY() + cell.getSize()) {
                return cell;
            }
        }
        return null;
    }

    public boolean buyTower(Tower tower, Cells position) {
        if (totalMoney < tower.getCost() || !position.isBuildable() || position.getTower() != null)
            return false; // The purchase failed
        totalMoney -= tower.getCost();
        position.setBuildable(false);
        towers.add(tower);
        tower.setPosition(position);
        position.setTower(tower);
        System.out.println("Tower bought in model");
        tower.setBoard(this);
        tower.addPropertyChangeListener(this);
        return true;
    }

    public void sellTower(Cells cells) {
        if (cells.getTower() == null)
            return;
        totalMoney += cells.getTower().getSellPrice();
        towers.remove(cells.getTower());
        cells.getTower().removeAllPropertyChangeListeners();
        cells.setBuildable(true);
        cells.getTower().remove();
        System.out.println("Tower sold");
    }

    public void removeTower(Tower tower) {
        if (!towers.contains(tower))
            return;
        towers.remove(tower);
    }

    public boolean upgradeTower(Tower tower) {
        if (!towers.contains(tower))
            return false;
        if (totalMoney < tower.getUpgradePrice())
            return false;
        if (tower.getLevel() == 3)
            return false;
        totalMoney -= tower.getUpgradePrice();
        tower.upgrade();
        return true;
    }

    public CopyOnWriteArrayList<Enemy> getEnemies() {
        return currentEnnemies;
    }

    public int getLevel() {
        return level;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void takeDamage() {
        totalLives -= 1;
    }

    public void setWave(Waves waves) {
        this.waves = waves;
    }

    public int getCurrentWaveNumber() {
        return currentWaveNumber;
    }

    public ArrayList<Enemy> getCurrentEnnemies() {
        return new ArrayList<Enemy>(currentEnnemies);
    }

    public int getTotalWaves() {
        return totalWaves;
    }

    public boolean hasLost() {
        return totalLives <= 0;
    }

    public void setCells(ArrayList<Cells> cells) {
        this.cells = cells;
    }

    public int getMoney() {
        return totalMoney;
    }

    public int getLife() {
        return totalLives;
    }
}
