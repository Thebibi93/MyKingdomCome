package states;

import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import view.main.Game;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import view.ui.buttons.*;

public class Menu extends State {

    private final static Menu INSTANCE = new Menu("Menu");
    protected JLabel background;

    // We want to have only one instance of the menu
    private Menu(String name) {
        super(name);
    }

    public static Menu getInstance() {
        return INSTANCE;
    }

    public void enter() {
        System.out.println("Menu");
        if (background == null || background.getParent() == null) {
            background = new JLabel();
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/menuBG.png"));
                ImageIcon icon = new ImageIcon(image);
                background = new JLabel(icon);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            background.setBounds(0, 0, Game.INSTANCE.getGameScreen().getPanelSize().width,
                    Game.INSTANCE.getGameScreen().getPanelSize().height);
            Game.INSTANCE.getGameScreen().add(background);
        }

        JPanel fireManPosition = new JPanel();
        fireManPosition.setOpaque(false);
        fireManPosition.setBounds(0, 0, Game.INSTANCE.getGameScreen().getPanelSize().width,
                Game.INSTANCE.getGameScreen().getPanelSize().height);
        // We load the image of the fireman
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/fireman.png"));
            // We scale the image to fit 1/4 of the screen
            Image scaledImage = image.getScaledInstance(Game.INSTANCE.getGameScreen().getPanelSize().width / 3,
                    Game.INSTANCE.getGameScreen().getPanelSize().height / 3, java.awt.Image.SCALE_SMOOTH);
            BufferedImage newImage = new BufferedImage(Game.INSTANCE.getGameScreen().getPanelSize().width / 3,
                    Game.INSTANCE.getGameScreen().getPanelSize().height / 3, BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.createGraphics();
            g.drawImage(scaledImage, 0, 0, null);
            g.dispose();
            ImageIcon icon = new ImageIcon(newImage);
            JLabel fireMan = new JLabel(icon);
            // Set it at the bottom right of the screen
            fireManPosition.setBounds(Game.INSTANCE.getGameScreen().getPanelSize().width - icon.getIconWidth(),
                    Game.INSTANCE.getGameScreen().getPanelSize().height - icon.getIconHeight(), icon.getIconWidth(),
                    icon.getIconHeight());
            fireManPosition.add(fireMan);
            fireManPosition.setOpaque(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        background.add(fireManPosition);
        JLabel title = new JLabel();
        title.setText("My Kingdom Come");
        // Change the color of the text
        title.setForeground(java.awt.Color.BLACK);
        // Center the text
        title.setHorizontalAlignment(JLabel.CENTER);
        // Change the font of the text
        title.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD,
                Game.INSTANCE.getGameScreen().getPanelSize().width / 20));

        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/title.png"));
            ImageIcon icon = new ImageIcon(image);
            title = new JLabel(icon);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        JPanel panelMenu = new JPanel(new GridBagLayout());
        panelMenu.setBounds(0, 0, Game.INSTANCE.getGameScreen().getPanelSize().width,
                Game.INSTANCE.getGameScreen().getPanelSize().height);
        panelMenu.setOpaque(false);

        PlayButton playButton = new PlayButton();
        SettingButton settingButton = new SettingButton();
        QuitButton quitButton = new QuitButton();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE; // This makes the button not fill its display area
        gbc.anchor = GridBagConstraints.CENTER; // This centers the button within its display area
        gbc.insets = new Insets(10, 0, 10, 0); // This adds 10 pixels of padding above and below each button

        gbc.weighty = 1; // This makes the row with the title grow to fill the available vertical space
        panelMenu.add(title, gbc);

        gbc.weighty = 0; // This makes the row with the play button not grow taller than its preferred
                         // height
        panelMenu.add(playButton, gbc);

        panelMenu.add(settingButton, gbc);
        panelMenu.add(quitButton, gbc);
        panelMenu.validate();
        background.add(panelMenu);
        background.validate();
        System.out.println("Menu Loaded");
    }

    public void exit() {
        System.out.println("Exit Menu");
        if (background != null)
            background.removeAll();
    }

    public void transitionTo(State s) {

    }

    @Override
    public void updateGraphics(Graphics g) {

    }

    @Override
    public void updateLogic(long deltaT) {

    }

    public void mouseClicked(int x, int y) {

    }
}
