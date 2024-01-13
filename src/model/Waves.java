package model;

import java.util.ArrayList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class Waves implements PropertyChangeListener {
    private ArrayList<Wave> waves;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Waves(ArrayList<Wave> waves) {
        this.waves = waves;
    }

    public Waves() {
        this(new ArrayList<Wave>());
    }

    public void addWave(Wave wave) {
        waves.add(wave);
        wave.addPropertyChangeListener(this);
    }

    public void addEventListeners(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removeEventListeners(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("enemySpawned")) {
            // We notify the board that an enemy has spawned
            propertyChangeSupport.firePropertyChange("enemySpawned", null, evt.getNewValue());
        }
    }

    public boolean isFinished() {
        System.out.println(waves.size());
        return waves.isEmpty();
    }

    public Wave getCurrentWave() {
        if(waves.size() > 0)
        return waves.get(0);
        return new Wave();
    }

    public boolean nextWave() {
        if (waves.size() > 0) {
            waves.get(0).removePropertyChangeListener(this);
            waves.remove(0);
            if (waves.size() > 0) {
                waves.get(0).start();
                return true;
            }
        }
        return false;
    }

    public int getWavesTotal() {
        return waves.size();
    }
}
