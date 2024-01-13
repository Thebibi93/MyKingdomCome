package view.ui;

import javax.swing.JLabel;
import controllers.TowerController;
import model.gameEntities.towers.*;
import util.OutlinedLabel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

// This class is used to display the icon of the tower to show if he press a key which tower he is going to buy
public class TowerIcon extends JPanel {

    private ImageIcon image;
    private ImageIcon highlightImage;

    public TowerIcon(TowerController towerController, int key) {
        super();
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        JLabel towerIcon = new JLabel();
        add(towerIcon, BorderLayout.CENTER);

        if (towerController.getModel() instanceof ArcherTower) {
            // We import the image of the tower
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/icons/IconArcherTower.png"));
                ImageIcon icon = new ImageIcon(image);
                this.image = icon;

                // We import the highlighted image of the tower
                BufferedImage highlightImage = ImageIO
                        .read(getClass().getResourceAsStream("/icons/IconArcherTowerHighlight.png"));
                ImageIcon highlightIcon = new ImageIcon(highlightImage);
                this.highlightImage = highlightIcon;

                towerIcon.setIcon(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (towerController.getModel() instanceof SlingShotTower) {
            // We import the image of the tower
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/icons/IconSlingShotTower.png"));
                ImageIcon icon = new ImageIcon(image);
                this.image = icon;

                // We import the highlighted image of the tower
                BufferedImage highlightImage = ImageIO
                        .read(getClass().getResourceAsStream("/icons/IconSlingShotTowerHighlight.png"));
                ImageIcon highlightIcon = new ImageIcon(highlightImage);
                this.highlightImage = highlightIcon;

                towerIcon.setIcon(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (towerController.getModel() instanceof CrystalTower) {
            // We import the image of the tower
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/icons/IconCrystalTower.png"));
                ImageIcon icon = new ImageIcon(image);
                this.image = icon;

                // We import the highlighted image of the tower
                BufferedImage highlightImage = ImageIO
                        .read(getClass().getResourceAsStream("/icons/IconCrystalTowerHighlight.png"));
                ImageIcon highlightIcon = new ImageIcon(highlightImage);
                this.highlightImage = highlightIcon;

                towerIcon.setIcon(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(towerController.getModel() instanceof MagicCatapultTower){
            // We import the image of the tower
            try {
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/icons/IconMagicCatapultTower.png"));
                ImageIcon icon = new ImageIcon(image);
                this.image = icon;

                // We import the highlighted image of the tower
                BufferedImage highlightImage = ImageIO
                        .read(getClass().getResourceAsStream("/icons/IconMagicCatapultTowerHighlight.png"));
                ImageIcon highlightIcon = new ImageIcon(highlightImage);
                this.highlightImage = highlightIcon;

                towerIcon.setIcon(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Based on the key value we set an image that corresponds to the key value
        switch (key) {
            case 0:
                // The key 0 corresponds to the key A
                add(loadKeyLabel("/icons/A-Key.png"), BorderLayout.WEST);
                break;
            case 1:
                // The key 1 corresponds to the key Z
                add(loadKeyLabel("/icons/Z-Key.png"), BorderLayout.WEST);
                break;
            case 2:
                // The key 2 corresponds to the key E
                add(loadKeyLabel("/icons/E-Key.png"), BorderLayout.WEST);
                break;
            case 3:
                // The key 3 corresponds to the key R
                add(loadKeyLabel("/icons/R-Key.png"), BorderLayout.WEST);
                break;
            default:
                break;
        }

        // On the bottom right corner of the icon we display the cost of the tower
        String costText = Integer.toString(towerController.getModel().getCost()) + "$";
        OutlinedLabel cost = new OutlinedLabel(costText);
        cost.setOpaque(false);
        cost.setLayout(new BorderLayout());
        add(cost, BorderLayout.SOUTH);
    }

    private JLabel loadKeyLabel(String path) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
            BufferedImage icon = image.getSubimage(0, 0, 32, 32);
            JLabel label = new JLabel(new ImageIcon(icon));
            return label;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (getParent() != null) {
            // We set the size of our tower icon to be slightly smaller than the size of the
            // parent
            setSize(getParent().getWidth() - 10, getParent().getHeight() - 10);
        }
    }

    void highlight() {
        // We change our tower preview image to a highlighted version
        ((JLabel) this.getComponent(0)).setIcon(highlightImage);
    }

    void unhighlight() {
        // We change our tower preview image to a normal version
        ((JLabel) this.getComponent(0)).setIcon(image);
    }
}
