package view.ui;

import javax.swing.JPanel;

import model.gameEntities.towers.Tower;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;

import util.OutlinedLabel;

// This class is responsible for drawing the game ui on the screen
public class GameUiPanel extends JPanel {
    private Life life;
    private Money money;
    private JPanel wrapperPanelBottom;
    private JPanel wrapperPanelTop;
    private TowersIcons towersIcons;
    private TowerStats towerStats;
    private OutlinedLabel statsDisplay = new OutlinedLabel("Wave 1/10", true);
    private int waveNumber = 1;
    private boolean isInfinite = false;
    private int wavesTotal;
    // Add the other ui elements here when they are implemented

    public GameUiPanel() {
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
    }

    public void addUiInfo(int life, int money, int totalWaves, int difficulty) {
        this.life = new Life(life);
        this.money = new Money(money);

        this.wavesTotal = totalWaves;
        if (difficulty == 4) {
            isInfinite = true;
            statsDisplay = new OutlinedLabel("Wave " + waveNumber, true);
        } else {
            statsDisplay = new OutlinedLabel("Wave " + waveNumber + "/" + wavesTotal, true);
        }
        statsDisplay.setOpaque(false);

        wrapperPanelTop = new JPanel(new GridBagLayout());
        wrapperPanelTop.setOpaque(false);
        wrapperPanelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add 10 pixels of padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new java.awt.Insets(10, 30, 10, 30);
        gbc.gridx = 0;
        gbc.gridy = 0;
        wrapperPanelTop.add(this.life, gbc);

        gbc.gridx = 1;
        wrapperPanelTop.add(this.money, gbc);

        gbc.gridx = 2;
        wrapperPanelTop.add(statsDisplay, gbc);

        this.add(wrapperPanelTop, java.awt.BorderLayout.NORTH);

        // Refresh the panel
        this.validate();
    }

    public void updateStateWave(int waveNumber) {
        this.waveNumber = waveNumber;
        if (isInfinite)
            statsDisplay.setText("Wave " + waveNumber);
        else {
            statsDisplay.setText("Wave " + waveNumber + "/" + wavesTotal);
        }
    }

    public void addTowersIcons(TowersIcons towersIcons) {
        // Create a new JPanel with a CardLayout
        wrapperPanelBottom = new JPanel(new GridLayout(1, 2));
        wrapperPanelBottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add 10 pixels of padding
        wrapperPanelBottom.setOpaque(false);

        towersIcons.setOpaque(false);
        this.towersIcons = towersIcons;

        // Add the towers icons to the wrapper panel
        wrapperPanelBottom.add(towersIcons);
        TowerStats towerStats = new TowerStats();
        wrapperPanelBottom.add(towerStats);
        this.towerStats = towerStats;

        this.add(wrapperPanelBottom, java.awt.BorderLayout.SOUTH);
    }

    public void showTowerStats(Tower tower) {
        wrapperPanelBottom.remove(this.towerStats);
        this.towerStats = new TowerStats(tower);
        wrapperPanelBottom.add(this.towerStats);
        wrapperPanelBottom.validate();
    }

    public void hideTowerStats() {
        // We remove the old tower stats
        wrapperPanelBottom.remove(this.towerStats);
        this.towerStats = new TowerStats();
        // We add the new tower stats
        wrapperPanelBottom.add(this.towerStats);
    }

    public void highlightTowerIcon(int key) {
        this.towersIcons.highlight(key);
    }

    public void unhighlightTowerIcon(int key) {
        this.towersIcons.unhighlight(key);
    }

    public void setLife(int l) {
        life.setLifeText(l);
    }

    public void setMoney(int m) {
        money.setMoneyText(m);
    }

    public TowersIcons getTowersIcons() {
        return this.towersIcons;
    }

    @Override
    public void addNotify() {
        super.addNotify();

        if (getParent() != null) {
            Dimension parentSize = getParent().getSize();
            setPreferredSize(parentSize);
            setSize(parentSize);
            setMaximumSize(parentSize);
        }

        this.validate();
    }
}
