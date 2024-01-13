package view.ennemies;

import util.AnimatedSprite;
import javax.swing.JPanel;
import java.awt.Graphics;
import model.Direction;
import model.gameEntities.enemies.*;

import javax.swing.BorderFactory;
import java.awt.Color;
import view.ui.HealthBar;

public abstract class EnemyView extends JPanel {
    private AnimatedSprite U_WALK;
    private AnimatedSprite D_WALK;
    private AnimatedSprite L_WALK;
    private AnimatedSprite R_WALK;
    private AnimatedSprite sprite;
    private Direction direction = Direction.NONE;
    private HealthBar healthBar;

    public EnemyView(AnimatedSprite D_WALK, AnimatedSprite R_WALK, AnimatedSprite U_WALK, AnimatedSprite L_WALK,
            int cellSize) {
        this.D_WALK = D_WALK;
        this.R_WALK = R_WALK;
        this.U_WALK = U_WALK;
        this.L_WALK = L_WALK;
        sprite = D_WALK;
        rescale(cellSize);
        this.setSize(cellSize, cellSize);
        this.setOpaque(false);
    }

    public static EnemyView getEnemyView(Enemy model, int cellSize) {
        if (model instanceof Bee) {
            return new BeeView((int) (cellSize * 1.5));
        }
        if(model instanceof Goblin){
            return new GoblinView((int) (cellSize * 1.5));
        }
        if(model instanceof Slime){
            return new SlimeView((int) (cellSize * 1.5));
        }
        if(model instanceof Wolf){
            return new WolfView((int) (cellSize * 1.5));
        }
        return null;
    }

    public void createHealthBar(int cellSize, int health, int armorLevel, int magicArmorLevel) {
        healthBar = new HealthBar(cellSize, health);
        this.add(healthBar);
        healthBar.setLocation((this.getWidth() - healthBar.getWidth()) / 2, 0);
    
        JPanel lastArmorRectangle = null;
    
        // Create armor level panel
        for (int i = 0; i < armorLevel; i++) {
            JPanel rectangle = new JPanel();
            rectangle.setBackground(Color.GRAY); // Set your desired color
            rectangle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Add black outline
            rectangle.setSize(cellSize / 4, 10); // Set your desired size
            this.add(rectangle);
            rectangle.setLocation((this.getWidth() - rectangle.getWidth()) / 2 + i * rectangle.getWidth(),
                    healthBar.getHeight());
    
            lastArmorRectangle = rectangle;
        }
    
        // Create magic armor level panel
        for (int i = 0; i < magicArmorLevel; i++) {
            JPanel rectangle = new JPanel();
            rectangle.setBackground(new Color(0, 51, 254)); // Set your desired color
            rectangle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Add black outline
            rectangle.setSize(cellSize / 4, 10); // Set your desired size
            this.add(rectangle);
            int y = lastArmorRectangle != null ? lastArmorRectangle.getY() + lastArmorRectangle.getHeight() : healthBar.getHeight();
            rectangle.setLocation((this.getWidth() - rectangle.getWidth()) / 2 + i * rectangle.getWidth(), y);
        }
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public void rescale(int cellSize) {
        this.D_WALK = D_WALK.resize(cellSize, cellSize);
        this.R_WALK = R_WALK.resize(cellSize, cellSize);
        this.U_WALK = U_WALK.resize(cellSize, cellSize);
        this.L_WALK = L_WALK.resize(cellSize, cellSize);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        getCorrectSprite().reset();
    }

    public AnimatedSprite getCorrectSprite() {
        switch (direction) {
            case NORTH:
                sprite = U_WALK;
                break;
            case SOUTH:
                sprite = D_WALK;
                break;
            case WEST:
                sprite = L_WALK;
                break;
            case EAST:
                sprite = R_WALK;
                break;
            default:
                sprite = D_WALK;
                break;
        }
        return sprite;
    }

    public void destroy() {
        if (this.getParent() != null)
            this.getParent().remove(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        getCorrectSprite();
        this.setSize(sprite.getWidth(), sprite.getHeight());
        g.drawImage(sprite.displayNextFrame(), 0, 0, null);
    }
}
