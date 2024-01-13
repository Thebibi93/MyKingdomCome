package view.ui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
// This class warp all four tower icons

public class TowersIcons extends JPanel {

    public TowersIcons(TowerIcon towerIcon1, TowerIcon towerIcon2, TowerIcon towerIcon3, TowerIcon towerIcon4) {
        super();
        this.setPreferredSize(new java.awt.Dimension(200, 100));
        this.setOpaque(false);
        // We want the tower icons to be displayed horizontally so we set the layout to horizontal
        this.setLayout(new java.awt.GridLayout(1, 4));
    
        // Wrap each TowerIcon in a JPanel with a GridBagLayout
        this.add(createPanelForIcon(towerIcon1));
        this.add(createPanelForIcon(towerIcon2));
        this.add(createPanelForIcon(towerIcon3));
        this.add(createPanelForIcon(towerIcon4));
    }
    
    private JPanel createPanelForIcon(TowerIcon towerIcon) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.add(towerIcon);
        return panel;
    }

    void highlight (int key) {
        // We highlight the tower icon that corresponds to the key value
        switch (key) {
            case 0:
                // The key 0 corresponds to the key A
                ((TowerIcon) ((JPanel) this.getComponent(0)).getComponent(0)).highlight();
                break;
            case 1:
                // The key 1 corresponds to the key Z
                ((TowerIcon) ((JPanel) this.getComponent(1)).getComponent(0)).highlight();
                break;
            case 2:
                // The key 2 corresponds to the key E
                ((TowerIcon) ((JPanel) this.getComponent(2)).getComponent(0)).highlight();
                break;
            case 3:
                // The key 3 corresponds to the key R
                ((TowerIcon) ((JPanel) this.getComponent(3)).getComponent(0)).highlight();
                break;
            default:
                break;
        }
    }

    void unhighlight(int key){
        // We unhighlight the tower icon that corresponds to the key value
        switch (key) {
            case 0:
                // The key 0 corresponds to the key A
                ((TowerIcon) ((JPanel) this.getComponent(0)).getComponent(0)).unhighlight();
                break;
            case 1:
                // The key 1 corresponds to the key Z
                ((TowerIcon) ((JPanel) this.getComponent(1)).getComponent(0)).unhighlight();
                break;
            case 2:
                // The key 2 corresponds to the key E
                ((TowerIcon) ((JPanel) this.getComponent(2)).getComponent(0)).unhighlight();
                break;
            case 3:
                // The key 3 corresponds to the key R
                ((TowerIcon) ((JPanel) this.getComponent(3)).getComponent(0)).unhighlight();
                break;
            default:
                break;
        }
    }
}
