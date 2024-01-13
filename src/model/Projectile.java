package model;

import model.gameEntities.enemies.Enemy;
import model.gameEntities.towers.Tower;
import util.geometry.RealCoordinates;
import java.util.ArrayList;
import java.beans.PropertyChangeSupport;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyChangeListener;

// This class is used to represent the projectiles logic fired by the towers
public class Projectile {
    private Tower shooter;
    private double speed;
    private double angle;
    private Damage damage;
    private RealCoordinates coordinates;
    private Enemy target;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private boolean hasReachedTarget = false;

    public Projectile(double speed, Damage damage, double x, double y, Enemy target, Tower shooter) {
        this.speed = speed;
        this.damage = damage;
        this.coordinates = new RealCoordinates(x, y);
        this.target = target;
        this.shooter = shooter;
    }

    public void update(long deltaT) {
        if (target != null && shooter != null) {
            double xDistance = target.getPosition().x() - coordinates.x();
            double yDistance = target.getPosition().y() - coordinates.y();

            // Calculate the angle between the projectile and the target
            double rotation = Math.atan2(yDistance, xDistance);
            this.angle = rotation + Math.PI / 2;

            // Calculate the velocity vector
            double velocityX = speed * Math.cos(rotation);
            double velocityY = speed * Math.sin(rotation);

            // Move the projectile along the velocity vector
            coordinates = coordinates
                    .plus(new RealCoordinates(velocityX * deltaT * 1E-9, velocityY * deltaT * 1E-9));

            if (target == null) {
                propertyChangeSupport.firePropertyChange("projectileDestroyed", this, null);
            }
            // Check if the projectile has reached the target
            if (hasReachedTarget(deltaT)) {
                propertyChangeSupport.firePropertyChange("projectileDestroyed", this, null);
                if (damage.isSplash()) {
                    // We draw a circle around the target and check if any enemies are inside
                    Ellipse2D splashRange = new Ellipse2D.Double(
                            target.getPosition().x() - damage.getSplashRange().getWidth() / 2,
                            target.getPosition().y() - damage.getSplashRange().getHeight() / 2,
                            damage.getSplashRange().getWidth(),
                            damage.getSplashRange().getHeight());
                    // We check if any enemies are inside the splash range
                    ArrayList<Enemy> enemies = new ArrayList<>();
                    enemies.addAll(shooter.getBoard().getEnemies());
                    for (Enemy enemy : enemies) {
                        if (splashRange.intersects(enemy.getHurtbox())) {
                            enemy.takeDamage(damage);
                        }
                    }
                }
                target.takeDamage(damage);
            }
        } else {
            propertyChangeSupport.firePropertyChange("projectileDestroyed", this, null);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removeAllPropertyChangeListeners() {
        for (PropertyChangeListener listener : propertyChangeSupport.getPropertyChangeListeners()) {
            this.propertyChangeSupport.removePropertyChangeListener(listener);
        }
    }

    public boolean hasReachedTarget(double deltaT) {
        double xDistance = target.getPosition().x() - coordinates.x();
        double yDistance = target.getPosition().y() - coordinates.y();
        double distance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
        boolean hasReachedTarget = distance <= speed * 1E-9 * deltaT;
        this.hasReachedTarget = hasReachedTarget;
        return hasReachedTarget;
    }

    public boolean hasReachedTarget() {
        return hasReachedTarget;
    }

    public double getAngle() {
        return angle;
    }

    public Tower getShooter() {
        return shooter;
    }

    public Enemy getTarget() {
        return target;
    }

    public RealCoordinates getPosition() {
        return coordinates;
    }

    public int getX() {
        return (int) coordinates.x();
    }

    public int getY() {
        return (int) coordinates.y();
    }

    public void setTarget(Enemy target) {
        this.target = target;
    }

    public void setShooter(Tower shooter) {
        this.shooter = shooter;
    }

}
