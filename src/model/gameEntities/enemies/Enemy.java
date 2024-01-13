package model.gameEntities.enemies;

import model.AStar;
import model.Cells;
import model.Damage;
import model.DamageType;
import model.Direction;
import util.Hurtbox;
import java.util.List;
import util.geometry.RealCoordinates;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public abstract class Enemy {
    protected RealCoordinates position;
    protected Hurtbox hurtbox;
    protected int health;
    protected double speed;
    protected int reward;
    protected double defense;
    protected double magicDefense;
    protected int level;
    protected Cells target;
    private Direction direction;
    private Cells spawn;
    protected boolean isDead = false;
    protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private List<Cells> path;
    private int currentPathIndex;

    public Enemy(int health, double speed, int reward, double defense, double magicDefense, int level,
            double hpGrowth) {
        this.speed = speed;
        this.reward = reward;
        this.defense = defense;
        this.magicDefense = magicDefense;
        this.level = level;
        for (int i = 0; i < level; i++) {
            this.health += health * hpGrowth;
        }
        this.direction = Direction.NONE;
    }

    public void update(long deltaT) {
        move(deltaT);
        hurtbox.move(position.x(), position.y());
        willDestroyBase();
    }

    public void willDestroyBase() {
        if (currentPathIndex >= path.size()) {
            switch (direction) {
                case NORTH:
                    if (position.y() < 0) {
                        propertyChangeSupport.firePropertyChange("baseDestroyed", this, true);
                        isDead = true;
                    }
                    break;
                case SOUTH:
                    if (position.y() > path.get(currentPathIndex - 1).getY() + path.get(currentPathIndex - 1).getSize()) {
                        propertyChangeSupport.firePropertyChange("baseDestroyed", this, true);
                        isDead = true;
                    }
                    break;
                case EAST:
                    if (position.x() > path.get(currentPathIndex - 1).getX() + path.get(currentPathIndex - 1).getSize()) {
                        propertyChangeSupport.firePropertyChange("baseDestroyed", this, true);
                        isDead = true;
                    }
                    break;
                case WEST:
                    if (position.x() < 0) {
                        propertyChangeSupport.firePropertyChange("baseDestroyed", this, true);
                        isDead = true;
                    }
                    break;
                default:
                    break;
            }
        }
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

    public int getHealth() {
        return health;
    }

    public int getArmorLevel(){
        if(defense < 0.2){
            return 0;
        }
        if(defense < 0.3){
            return 1;
        }
        if(defense < 0.5){
            return 2;
        }
        return 3;
    }

    public int getMagicArmorLevel(){
        if(magicDefense < 0.2){
            return 0;
        }
        if(magicDefense < 0.3){
            return 1;
        }
        if(magicDefense < 0.5){
            return 2;
        }
        return 3;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }

    public int getReward() {
        return reward;
    }

    public int getLevel() {
        return level;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setMagicDefense(int magicDefense) {
        this.magicDefense = magicDefense;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public RealCoordinates getPosition() {
        return position;
    }

    public void takeDamage(Damage damage) {
        if (damage.getType().equals(DamageType.PHYSICAL)) {
            double damageValue = damage.getValue() * (1 - defense);
            health -= damageValue + 1;
            propertyChangeSupport.firePropertyChange("health", null, health);
        } else if (damage.getType().equals(DamageType.MAGICAL)) {
            double damageValue = damage.getValue() * (1 - magicDefense);
            health -= damageValue + 1;
            propertyChangeSupport.firePropertyChange("health", null, health);
        }
        if (health <= 0) {
            isDead = true;
            propertyChangeSupport.firePropertyChange("enemyReward", this, reward);
        }
    }

    private void moveTo(Cells cell, long deltaT) {
        // Calculate the direction vector
        RealCoordinates direction = new RealCoordinates(cell.getX() - position.x(), cell.getY() - position.y());

        // Normalize the direction vector
        double length = Math.sqrt(direction.x() * direction.x() + direction.y() * direction.y());
        RealCoordinates normalizedDirection = new RealCoordinates(direction.x() / length, direction.y() / length);

        // Calculate the new position
        RealCoordinates newPosition = position.plus(normalizedDirection.times(speed * deltaT * 1E-9));

        // Update the direction
        if (Math.abs(normalizedDirection.x()) > Math.abs(normalizedDirection.y())) {
            Direction newDirection = normalizedDirection.x() > 0 ? Direction.EAST : Direction.WEST;
            // Fire the property change event if the direction has changed
            if (newDirection != this.direction) {
                propertyChangeSupport.firePropertyChange("direction", this.direction, newDirection);
                System.out.println("Direction changed : " + newDirection);
                this.direction = newDirection;
            }
        } else {
            Direction newDirection = normalizedDirection.y() > 0 ? Direction.SOUTH : Direction.NORTH;
            // Fire the property change event if the direction has changed
            if (newDirection != this.direction) {
                propertyChangeSupport.firePropertyChange("direction", this.direction, newDirection);
                System.out.println("Direction changed : " + newDirection);
                this.direction = newDirection;
            }
        }

        // Update the position
        position = newPosition;

        // Check if the enemy has reached the target cell
        if (Math.abs(cell.getX() - position.x()) < speed * deltaT * 1E-9
                && Math.abs(cell.getY() - position.y()) < speed * deltaT * 1E-9) {
            currentPathIndex++;
        }
        propertyChangeSupport.firePropertyChange("position", null, position);
    }

    public void move(long deltaT) {
        if (currentPathIndex < path.size()) {
            // Move to the next cell
            Cells nextCell = path.get(currentPathIndex);
            moveTo(nextCell, deltaT);
        } else {
            System.out.println("Reached the last cell");
            // Continue moving in the same direction after reaching the last cell
            double dx = direction == Direction.EAST ? 1 : direction == Direction.WEST ? -1 : 0;
            double dy = direction == Direction.SOUTH ? 1 : direction == Direction.NORTH ? -1 : 0;
            position = position.plus(new RealCoordinates(dx * speed * deltaT / 1000.0, dy * speed * deltaT / 1000.0));
            propertyChangeSupport.firePropertyChange("position", null, position);
        }
    }

    public Hurtbox getHurtbox() {
        return hurtbox;
    }

    public void setHurtBox(Hurtbox hurtbox) {
        this.hurtbox = hurtbox;
    }

    Cells getCurrentCell(){
        return path.get(currentPathIndex);
    }

    public void setSpawn(Cells spawn) {
        this.spawn = spawn;
        currentPathIndex = 1;
    }

    public void setTarget(Cells target) {
        path = AStar.findPath(spawn, target);
        // We then set the position to be off screen by calculating which direction the
        // enemy will come from
        // This is done by calculating the direction vector between the spawn cell and
        // the first cell of the path
        RealCoordinates direction = new RealCoordinates(path.get(1).getX() - spawn.getX(),
                path.get(1).getY() - spawn.getY());
        // We then normalize the direction vector
        double length = Math.sqrt(direction.x() * direction.x() + direction.y() * direction.y());
        RealCoordinates normalizedDirection = new RealCoordinates(direction.x() / length, direction.y() / length);
        // We then set the position to be off screen
        position = new RealCoordinates(spawn.getX() - normalizedDirection.x() * 100,
                spawn.getY() - normalizedDirection.y() * 100);
        this.hurtbox = new Hurtbox(position.x(), position.y(), spawn.getSize(), spawn.getSize());
        this.target = target;
    }

    public boolean isDead() {
        return isDead;
    }
}
