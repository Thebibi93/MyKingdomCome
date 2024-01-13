package view.ui;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.border.Border;

import model.gameEntities.towers.Tower;
import view.main.Game;

// This class is responsible for drawing the tower stats on the screen
public class TowerStats extends JPanel {
        private JLabel level;
        private JLabel target;
        private JLabel damage;
        private JLabel attackSpeed;
        private JLabel sellPrice;
        private JLabel upgradePrice;

        // We need a default constructor for the TowerStats class
        public TowerStats() {
                this.setVisible(false);
                this.setPreferredSize(new java.awt.Dimension(50, 25));
                this.setBackground(new java.awt.Color(200, 200, 200)); // Light gray background
                this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

                // We add the tower stats
                level = new JLabel("Level: 1");
                level.setOpaque(false);
                target = new JLabel("Target: Single");
                target.setOpaque(false);
                damage = new JLabel("Damage: 10/PHYSICAL");
                damage.setOpaque(false);
                // Calculate the number of attacks per second
                int attackSpeedInMs = 1000;
                double attacksPerSecond = 1000.0 / attackSpeedInMs;
                attackSpeed = new JLabel(String.format("Attacks per second: %.2f", attacksPerSecond));
                attackSpeed.setOpaque(false);
                sellPrice = new JLabel("Sell price: 70");
                sellPrice.setOpaque(false);
                upgradePrice = new JLabel("Upgrade price: 70");

                // We add the tower stats to the panel
                this.add(level);
                this.add(target);
                this.add(damage);
                this.add(attackSpeed);
                this.add(sellPrice);
                this.add(upgradePrice);
        }

        public TowerStats(Tower t) {
                this.setBackground(new java.awt.Color(200, 200, 200)); // Light gray background
                this.setLayout(new GridLayout(2, 3)); // Two rows, three columns

                // Add a border around the panel
                Border border = BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2);
                this.setBorder(border);

                // We add the tower stats
                level = new JLabel(
                                "<html><div style='text-align: center; color: red;'><b>Level: " + t.getLevel()
                                                + "</b></div></html>");
                boolean isSingleTarget = !t.getDamage().isSplash();
                String targetText = isSingleTarget ? "Target: Single" : "Target: Splash";
                target = new JLabel(
                                "<html><div style='text-align: center; color: green;'><b>" + targetText
                                                + "</b></div></html>");
                damage = new JLabel("<html><div style='text-align: center; color: blue;'><b>Damage: "
                                + t.getDamage().getValue()
                                + "<br>" + t.getDamage().getType() + "</b></div></html>");
                int attackSpeedInMs = t.getAttackSpeed().getDelay();
                double attacksPerSecond = 1000.0 / attackSpeedInMs;
                String attacksPerSecondStr = String.format("%.2f", attacksPerSecond); // Limit to 2 decimal places
                attackSpeed = new JLabel("<html><div style='text-align: center; color: orange;'><b>Speed: "
                                + attacksPerSecondStr + "</b></div></html>");
                sellPrice = new JLabel(
                                "<html><div style='text-align: center; color: purple;'><b>Sell: " + t.getSellPrice()
                                                + "</b></div></html>");
                int price = t.getUpgradePrice();
                if (price == -1)
                        upgradePrice = new JLabel(
                                        "<html><div style='text-align: center; color: brown;'><b>Upgrade: MAX</b></div></html>");
                else
                        upgradePrice = new JLabel("<html><div style='text-align: center; color: brown;'><b>Upgrade: "
                                        + t.getUpgradePrice() + "</b></div></html>");

                // We add padding, change the font
                Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
                // We need to calculate the font size based on the screen size
                Dimension windowResolution = Game.INSTANCE.getGameScreen().getSize();
                int fontSize = (int) (windowResolution.getWidth() / 70);
                Font font = new Font("Arial", Font.BOLD, fontSize);

                JLabel[] labels = { level, target, damage, attackSpeed, sellPrice, upgradePrice };
                for (JLabel label : labels) {
                        label.setBorder(padding);
                        label.setFont(font);
                        this.add(label);
                }
        }
}
