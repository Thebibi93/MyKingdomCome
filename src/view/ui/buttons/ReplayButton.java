package view.ui.buttons;

import javax.swing.JLabel;
import states.State;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import view.main.Game;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ReplayButton extends JLabel {
    private ImageIcon image;
    private ImageIcon highlightImage;
    
    public ReplayButton(State changeState){
        super();
        // We load the image of the button
        try{
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/prinblesGUI/asset/png/Buttons/Square-Medium/Repeat/Default.png"));
            BufferedImage highlightImage = ImageIO.read(getClass().getResourceAsStream("/prinblesGUI/asset/png/Buttons/Square-Medium/Repeat/Hover.png"));
            ImageIcon icon = new ImageIcon(image);
            ImageIcon highlightIcon = new ImageIcon(highlightImage);
            this.image = icon;
            this.highlightImage = highlightIcon;
            this.setIcon(icon);
            setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        }
        catch(IOException e){
            e.printStackTrace();
        };

        // When the mouse is on the button, we change the image
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setIcon(highlightImage);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setIcon(image);
            }
        });

        // When the button is clicked, we change the state of the game
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Game.getGameState().changeState(changeState);
            }
        });
    }
}