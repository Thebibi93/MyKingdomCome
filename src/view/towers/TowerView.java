package view.towers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.Transparency;
import model.gameEntities.towers.*;
import model.gameEntities.enemies.Enemy;
import util.AnimatedSprite;
import model.Projectile;
// This class is responsible for drawing the towers on the screen

public abstract class TowerView extends JPanel {
    protected BufferedImage sprite;
    protected AnimatedSprite weapon;
    protected int level;
    protected int cellSize;
    protected double rotation;

    protected enum State {
        ATTACKING, IDLE
    };

    protected State state;

    public TowerView(BufferedImage sprite, AnimatedSprite weapon) {
        this.sprite = sprite;
        this.weapon = weapon;
        this.setOpaque(false);
        this.setSize(sprite.getWidth(), sprite.getHeight());
        this.state = State.IDLE;
        weapon.setOnFinished(() -> {
            state = State.IDLE;
        });
    }

    public abstract void upgrade();

    public static TowerView createTowerView(Tower t, int cellSize) {
        if (t instanceof ArcherTower) {
            return new ArcherTowerView(t.getLevel(), cellSize);
        }
        if (t instanceof SlingShotTower) {
            return new SlingShotTowerView(t.getLevel(), cellSize);
        }
        if (t instanceof CrystalTower) {
            return new CrystalTowerView(t.getLevel(), cellSize);
        }
        if (t instanceof MagicCatapultTower) {
            return new MagicCatapultTowerView(t.getLevel(), cellSize);
        }
        return null;
    }

    public boolean isAttacking() {
        return state == State.ATTACKING;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sprite, 0, 0, this);
        handleGraphics(g);
    }

    public void handleGraphics(Graphics g) {
        if (state == State.ATTACKING) {
            attackAnimation(g);
        } else {
            drawWeapon(g);
        }
    }

    public void update(Enemy target, Projectile p) {
        if (target != null) {
            System.out.println("Attacking enemy in tower view");
            state = State.ATTACKING;
            // Calculate the angle between the tower and the enemy
            double xDistance = target.getPosition().x() - p.getPosition().x();
            double yDistance = target.getPosition().y() - p.getPosition().y();

            // Calculate the angle between the projectile and the target
            rotation = Math.atan2(yDistance, xDistance) + Math.PI / 2;
            System.out.println("The value of attack is " + state);
        }
    }

    public void attackAnimation(Graphics g) {
        // We want to play the animation only once when the tower attacks
        if (weapon != null) {
            GraphicsConfiguration gc = getGraphicsConfiguration();
            BufferedImage image = gc.createCompatibleImage(weapon.getWidth(), weapon.getHeight(), Transparency.TRANSLUCENT);
            
            Graphics2D g2d = (Graphics2D) image.createGraphics(); // Create a copy of the graphics instance
            int centerX = sprite.getWidth() / 2;
            int centerY = sprite.getHeight() / 2;
            g2d.rotate(rotation, centerX, centerY); // Rotate the graphics
    
            int weaponX = centerX - weapon.getWidth() / 2;
            int weaponY = centerY - weapon.getHeight() / 2;
    
            g2d.drawImage(weapon.displayNextFrame(), weaponX, weaponY, null);
    
            g2d.dispose(); // Dispose the graphics copy

            g.drawImage(image, weaponX, weaponY, null);
        }
    }

    public void drawWeapon(Graphics g) {
        if (weapon != null) {

            GraphicsConfiguration gc = getGraphicsConfiguration();
            BufferedImage image = gc.createCompatibleImage(weapon.getWidth(), weapon.getHeight(), Transparency.TRANSLUCENT);
             
            Graphics2D g2d = (Graphics2D) image.createGraphics(); // Create a copy of the graphics instance
            int centerX = sprite.getWidth() / 2;
            int centerY = sprite.getHeight() / 2;
            g2d.rotate(rotation, centerX, centerY); // Rotate the graphics

            int weaponX = sprite.getWidth() / 2 - weapon.getWidth() / 2;
            int weaponY = sprite.getHeight() / 2 - weapon.getHeight() / 2;

            // Draw the weapon into the accelerated image
            g2d.drawImage(weapon.displayCurrentFrame(), weaponX, weaponY, null);
            g2d.dispose(); // Dispose the graphics copy
    
            switch (level) {
                case 1:
                    g.drawImage(image, weaponX, weaponY, null);
                    break;
                case 2:
                    g.drawImage(image, weaponX, weaponY - this.getHeight() / 4, null);
                    break;
                case 3:
                    g.drawImage(image, weaponX, weaponY - this.getHeight() / 4, null);
                    break;
                default:
                    break;
            }
        }
    }

    public void erase() {
        this.getGraphics().clearRect(0, 0, sprite.getWidth(), sprite.getHeight());
    }
}
