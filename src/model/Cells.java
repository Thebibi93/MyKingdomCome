package model;

import java.util.ArrayList;

import model.gameEntities.towers.Tower;
import util.geometry.IntCoordinates;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Cells{

    private Tower tower;
    private boolean isWalkable;
    private boolean isBuildable;
    private Cells northNeighbor;
    private Cells southNeighbor;
    private Cells eastNeighbor;
    private Cells westNeighbor;
    private IntCoordinates coordinates;
    private int size;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Cells(Cells original) {
        // Copy fields from original to this new object
        this.coordinates = original.coordinates;
        this.isWalkable = original.isWalkable();
        this.isBuildable = original.isBuildable();
    }

    public Cells(){
        this.isWalkable = false;
        this.isBuildable = true;
    }

    public Cells(boolean isWalkable){
        this.isWalkable = isWalkable;
        this.isBuildable = false;
    }

    public Cells(boolean isWalkable, boolean isBuildable){
        this.isWalkable = isWalkable;
        this.isBuildable = isBuildable;
    }

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

    public void setNeighbors(Cells northNeighbor, Cells southNeighbor, Cells eastNeighbor, Cells westNeighbor){
        this.northNeighbor = northNeighbor;
        this.southNeighbor = southNeighbor;
        this.eastNeighbor = eastNeighbor;
        this.westNeighbor = westNeighbor;
    }

    public Cells getNorthNeighbor(){
        return northNeighbor;
    }

    public Cells getSouthNeighbor(){
        return southNeighbor;
    }

    public Cells getEastNeighbor(){
        return eastNeighbor;
    }

    public Cells getWestNeighbor(){
        return westNeighbor;
    }

    public void setNorthNeighbor(Cells northNeighbor){
        this.northNeighbor = northNeighbor;
    }

    public void setSouthNeighbor(Cells southNeighbor){
        this.southNeighbor = southNeighbor;
    }

    public void setEastNeighbor(Cells eastNeighbor){
        this.eastNeighbor = eastNeighbor;
    }

    public void setWestNeighbor(Cells westNeighbor){
        this.westNeighbor = westNeighbor;
    }

    public ArrayList<Cells> getNeighbors(){
        ArrayList<Cells> neighbors = new ArrayList<>();
        neighbors.add(northNeighbor);
        neighbors.add(southNeighbor);
        neighbors.add(eastNeighbor);
        neighbors.add(westNeighbor);
        return neighbors;
    }

    public boolean isWalkable(){
        return isWalkable;
    }

    public boolean isBuildable(){
        return isBuildable;
    }

    public void setBuildable(boolean isBuildable){
        this.isBuildable = isBuildable;
    }

    public IntCoordinates getCoordinates(){
        return coordinates;
    }

    public IntCoordinates getCenter() {
        int centerX = coordinates.x() + size / 2;
        int centerY = coordinates.y() + size / 2;
        return new IntCoordinates(centerX, centerY);
    }

    public void setX(int x){
        if(coordinates == null)
            coordinates = new IntCoordinates(x, 0);
        else
            coordinates = new IntCoordinates(x, coordinates.y());
    }

    public void setY(int y){
        if(coordinates == null)
            coordinates = new IntCoordinates(0, y);
        else
            coordinates = new IntCoordinates(coordinates.x(), y);
    }

    public void setCoordinates(IntCoordinates coordinates){
        this.coordinates = coordinates;
    }

    public int getX(){
        return coordinates.x();
    }

    public int getY(){
        return coordinates.y();
    }

    public Tower getTower(){
        return tower;
    }

    public void setTower(Tower tower){
        propertyChangeSupport.firePropertyChange("tower", this.tower, tower);
        System.out.println("Tower set in model");
        System.out.println("Tower in cell : " + tower);
        this.tower = tower;
    }

    public int getSize(){
        return size;
    }

    public void setSize(int size){
        this.size = size;
    }
}
