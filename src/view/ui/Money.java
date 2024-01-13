package view.ui;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Dimension;

public class Money extends JPanel {
    int value = 0;
    String text = "";

    public Money(int money) {
        this.value = money;
        this.text = "Money: " + money;
        setFont(new java.awt.Font("Arial", 1, 20));
        setOpaque(false);
    }

    @Override
    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(getFont());
        int width = fm.stringWidth(text) + 10; // Add 10 for a little extra space
        int height = fm.getHeight() + 4; // Add 4 for a little extra space
        return new Dimension(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, x - 1, y - 1);
        g2d.drawString(text, x - 1, y + 1);
        g2d.drawString(text, x + 1, y - 1);
        g2d.drawString(text, x + 1, y + 1);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
    }

    public void setMoneyText(int money) {
        this.value = money;
        this.text = "Money: " + money;
    }
}
