package view.ui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

// This class is used to display the health of an enemy
public class HealthBar extends JPanel {
    public int initialHealth;
    public int currentHealth;
    private int healthBarWidth;

    public HealthBar(int size, int initialHealth) {
        this.initialHealth = initialHealth;
        this.currentHealth = initialHealth;
        this.healthBarWidth = size;
        this.setSize(size, size / 4);
        this.setOpaque(false);
    }

    public void updateHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create(); // Create a Graphics2D object
    
        int widthCurrentHealth = (int) ((currentHealth * 1.0 / initialHealth) * healthBarWidth);
        int widthLostHealth = healthBarWidth - widthCurrentHealth;
    
        // Draw the current health in green
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, widthCurrentHealth, getHeight());
    
        // Draw the lost health in red
        g2d.setColor(Color.RED);
        g2d.fillRect(widthCurrentHealth, 0, widthLostHealth, getHeight());
    
        // Draw a black outline around the life bar
        g2d.setColor(Color.BLACK);
        float thickness = 3f; // Set your desired thickness
        g2d.setStroke(new BasicStroke(thickness));
        g2d.drawRect(0, 0, healthBarWidth - 1, getHeight() - 1); // Subtract 1 to fit within the component
    
        g2d.dispose(); // Dispose the Graphics2D object when done
    }
}
