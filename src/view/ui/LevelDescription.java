package view.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

// This class is used to display the description of the level
public class LevelDescription extends JLabel {
    private ImageIcon image;
    private String description = "";

    public LevelDescription(int level) {
        super();

        // We set the image of the description
        try {
            BufferedImage image = ImageIO.read(
                    getClass().getResourceAsStream("/prinblesGUI/asset/png/Panel/Body/Rounded/Background.png"));
            this.setIcon(new javax.swing.ImageIcon(image));
            // We enter the description in the center of this panel
            switch (level) {
                case 1:
                    description += "Level 1:<br>The start of an adventure !<br>Select a tower with your keyboard then<br>left click to place it.<br>"
                            +
                            "Double click to upgrade your tower.<br>Right click to sell it.<br>Good luck hehihiha !";
                    break;
                case 2:
                    description += "Level 2:<br>You think you are that good ?<br>" +
                            "Face it you look just dumb<br>trying to stop me hehihiha !";
                    break;
                case 3:
                    description += "Level 3:<br>You are still here ?<br>" +
                            "I am starting to think you are not<br>that dumb after all hehihiha !";
                    break;
                default:
                    break;
            }
            this.setForeground(java.awt.Color.BLUE);

            // Resize the image based on the preferred size of the text
            ImageIcon icon = new ImageIcon(image);
            this.image = icon;
            this.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPreferredSize(Dimension d) {
        super.setPreferredSize(d);
        // Resize the image based on the preferred size of the text
        Image scaledImage = image.getImage().getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);

        // Create a new image that combines the original image and the text
        BufferedImage combinedImage = new BufferedImage(d.width, d.height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = combinedImage.getGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.setColor(Color.BLUE);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, d.height / 15));
        int lineHeight = g.getFontMetrics().getHeight();
        int y = d.height / 2 - lineHeight * description.split("<br>").length / 2;
        for (String line : description.split("<br>")) {
            int lineWidth = g.getFontMetrics().stringWidth(line);
            int x = (d.width - lineWidth) / 2;
            g.drawString(line, x, y + g.getFontMetrics().getAscent());
            y += lineHeight;
        }

        ImageIcon icon = new ImageIcon(combinedImage);
        this.image = icon;
        this.setIcon(icon);
    }
}
