package model.gameEntities.towers;

import java.awt.geom.Ellipse2D;
import javax.swing.Timer;
import java.util.Stack;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import model.Board;
import model.Cells;
import model.Damage;
import model.Projectile;
import model.gameEntities.enemies.Enemy;

public abstract class Tower {
    private int id;
    private static Stack<Integer> usedIds = new Stack<Integer>();
    private Damage damage;
    private Timer attackSpeed;
    private int cost;
    private int sellPrice;
    private int upgradePrice;
    private int level;
    private Ellipse2D range;
    private Cells position;
    private Board board;
    private Enemy target;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Tower(Damage damage, Timer attackSpeed, int cost, int sellPrice, int level, int upgradePrice,
            Ellipse2D range) {
        if (usedIds.size() == Integer.MAX_VALUE)
            usedIds = new Stack<Integer>();
        this.id = usedIds.size();
        usedIds.push(id);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        // When the timer is finished, we can attack
        attackSpeed.setRepeats(false);
        attackSpeed.start();
        this.cost = cost;
        this.sellPrice = sellPrice;
        this.upgradePrice = upgradePrice;
        this.level = level;
        this.range = range;
        target = null;
    }

    public int getId() {
        return id;
    }

    public abstract Tower copy(int cellSize);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void removeAllPropertyChangeListeners() {
        for (PropertyChangeListener listener : propertyChangeSupport.getPropertyChangeListeners()) {
            this.propertyChangeSupport.removePropertyChangeListener(listener);
        }
    }

    public void update() {
        if (canAttack()) {
            target = null;
        }
    }

    public boolean canAttack() {
        // If there are no targets, we look for one in the range of the tower and attack
        // it if we find one
        if (board == null){
            System.out.println("Board is null");
            return false;
        }
        if (target == null) {
            for (Enemy enemy : board.getCurrentEnnemies()) {
                if (range.intersects(enemy.getHurtbox()) && !attackSpeed.isRunning() && !enemy.isDead()) {
                    target = enemy;
                    System.out.println("Target found");
                    return attack(enemy);
                }
            }
        }
        // If there is a target, we attack it if it is still in range
        else if (range.intersects(target.getHurtbox()) && !attackSpeed.isRunning() && !target.isDead()) {
            System.out.println("Target still in range");
            return attack(target);
        }
        return false;
    }

    // When attack speed is finished, we can attack and then we restart the timer
    public boolean attack(Enemy target) {
        // Create a new projectile
        Projectile projectile = new Projectile(target.getSpeed() * 6, damage, position.getX(), position.getY(), target,
                this);
        projectile.setTarget(target);
        propertyChangeSupport.firePropertyChange("attack", projectile, target);
        propertyChangeSupport.firePropertyChange("projectileCreated", null, projectile);
        // Restart the attack speed timer
        attackSpeed.restart();
        return true;
    }

    public void setPosition(Cells position) {
        this.position = position;
        double centerX = position.getX() + position.getSize() / 2.0;
        double centerY = position.getY() + position.getSize() / 2.0;
        range.setFrame(centerX - range.getWidth() / 2, centerY - range.getHeight() / 2, range.getWidth(),
                range.getHeight());
    }

    public void remove() {
        position.setBuildable(true);
        position.setTower(null);
        position = null;
        board.removeTower(this);
    }

    public Ellipse2D getRange() {
        return range;
    }

    public Damage getDamage() {
        return damage;
    }

    public void setAttack(int attack) {
        this.damage.setValue(attack);
    }

    public void upgrade() {

    }

    public Board getBoard() {
        return board;
    }

    public Timer getAttackSpeed() {
        return attackSpeed;
    }

    public int getCost() {
        return cost;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getLevel() {
        return level;
    }

    public Cells getPosition() {
        return position;
    }

    public void setAttackSpeed(Timer attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setUpgradePrice(int upgradePrice) {
        this.upgradePrice = upgradePrice;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public void setRange(Ellipse2D range) {
        this.range = range;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
