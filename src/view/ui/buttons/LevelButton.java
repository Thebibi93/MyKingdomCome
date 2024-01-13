package view.ui.buttons;

import javax.swing.JLabel;

import states.DifficultySelection;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import view.main.Game;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LevelButton extends JLabel {
    private ImageIcon image;
    private ImageIcon highlightImage;
    private int levelNumber;
    
    public LevelButton(int levelNumber){
        super();
        // We load the image of the button
        try{
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/prinblesGUI/asset/png/Level/Button/Unlocked/Default.png"));
            BufferedImage highlightImage = ImageIO.read(getClass().getResourceAsStream("/prinblesGUI/asset/png/Level/Button/Unlocked/Hover.png"));
            ImageIcon icon = new ImageIcon(image);
            ImageIcon highlightIcon = new ImageIcon(highlightImage);
            this.levelNumber = levelNumber;
            this.image = icon;
            this.highlightImage = highlightIcon;
            this.setIcon(icon);
            setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        }
        catch(IOException e){
            e.printStackTrace();
        };

        // Set the text of the JLabel to be the level number and align it to the center
        this.setText(String.valueOf(levelNumber));
        // Set the color of the text to be white
        this.setForeground(java.awt.Color.WHITE);
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);

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
                // We set the level number of the game
                DifficultySelection.getInstance().setSelectedLevel(levelNumber);
                Game.getGameState().changeState(DifficultySelection.getInstance());
            }
        });
    }

    @Override 
    public void setPreferredSize(Dimension d){
        super.setPreferredSize(d);
        this.image = new ImageIcon(image.getImage().getScaledInstance(d.width, d.height, java.awt.Image.SCALE_SMOOTH));
        this.highlightImage = new ImageIcon(highlightImage.getImage().getScaledInstance(d.width, d.height, java.awt.Image.SCALE_SMOOTH));
        // We change the size of the font
        this.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, d.width / 2));
        this.setIcon(image);
    }
}

