package view.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import view.main.Game;
import view.ui.buttons.ReturnButton;
import view.ui.buttons.ReplayButton;
import java.awt.GridBagConstraints;
import java.awt.Font;

public class GameOverPanel extends JPanel {
    public GameOverPanel(int wave) {
        super();
        this.setLayout(new java.awt.GridBagLayout());
        this.setOpaque(false);
        this.setPreferredSize(new java.awt.Dimension((int)(Game.INSTANCE.getGameScreen().getWidth() / 1.5),
            (int)(Game.INSTANCE.getGameScreen().getHeight() / 1.5)));
        this.setMaximumSize(new java.awt.Dimension((int)(Game.INSTANCE.getGameScreen().getWidth() / 1.5),
            (int)(Game.INSTANCE.getGameScreen().getHeight() / 1.5)));
        JLabel gameOverLabel = new JLabel();
        gameOverLabel.setBackground(java.awt.Color.BLACK);
        gameOverLabel.setText("Game Over - You survived " + wave + " waves");
        String text = gameOverLabel.getText();
        // We calculate the correct font size based on the number of characters in the text and the size of the panel
        int fontSize = (int)(this.getPreferredSize().width / text.length() * 1.5);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        gameOverLabel.setHorizontalTextPosition(JLabel.CENTER);
        gameOverLabel.setVerticalTextPosition(JLabel.CENTER);
        gameOverLabel.setForeground(java.awt.Color.WHITE);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(true);
        textPanel.setBackground(java.awt.Color.BLACK);
        textPanel.add(gameOverLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER; // Add this line

        this.add(textPanel, gbc);

        // We add a button to go back to the main menu
        ReturnButton returnButton = new ReturnButton(Game.INSTANCE.getMenu());
        ReplayButton replayButton = new ReplayButton(Game.INSTANCE.getPlaying());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(returnButton);
        buttonPanel.add(replayButton);

        gbc.gridy = 1; // Set this to the next row
        this.add(buttonPanel, gbc);
        this.validate();
    }
}