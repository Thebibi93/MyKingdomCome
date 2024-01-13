package util;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class OutlinedLabel extends JPanel {

    private String text = "";
    private boolean isCentered = false;
    
    public OutlinedLabel(String text) {
        super();
        this.text = text;
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 20));
    }

    public OutlinedLabel(String text, boolean isCentered) {
        super();
        this.text = text;
        this.isCentered = isCentered;
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 20));
    }

    public void setText(String text) {
        this.text = text;
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
        int x = isCentered ? (getWidth() - fm.stringWidth(text)) / 2 : 0;
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, x - 1, y - 1);
        g2d.drawString(text, x - 1, y + 1);
        g2d.drawString(text, x + 1, y - 1);
        g2d.drawString(text, x + 1, y + 1);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
        g2d.dispose();
    }
}
