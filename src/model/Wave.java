package model;

import javax.swing.Timer;

import model.gameEntities.enemies.Enemy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Wave {
    private ArrayList<Enemy> enemies;
    private Timer spawnTime;
    private int currentEnemyIndex;

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    // Constructor to avoid null pointer exception
    public Wave() {
        this.enemies = new ArrayList<Enemy>();
        this.currentEnemyIndex = 0;
        this.spawnTime = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnEnemy();
            }
        });
    }

    public Wave(ArrayList<Enemy> enemies, int delay, Board board, int spawn, int target) {
        this.enemies = enemies;
        Cells spawnCell = board.getCell(spawn);
        Cells targetCell = board.getCell(target);
        for (Enemy e : enemies) {
            e.setSpawn(spawnCell);
            e.setTarget(targetCell);
        }
        this.currentEnemyIndex = 0;
        this.spawnTime = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spawnEnemy();
            }
        });
        spawnTime.setRepeats(false);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void start() {
        spawnTime.start();
    }

    public boolean isFinished() {
        return currentEnemyIndex >= enemies.size();
    }

    public void spawnEnemy() {
        if (currentEnemyIndex < enemies.size()) {
            Enemy enemy = enemies.get(currentEnemyIndex);
            // Notify listeners that an enemy has been spawned
            propertyChangeSupport.firePropertyChange("enemySpawned", null, enemy);
            currentEnemyIndex++;
            spawnTime.restart();
        } else {
            spawnTime.stop();
        }
    }
}
