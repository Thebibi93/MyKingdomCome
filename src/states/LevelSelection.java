package states;

import view.main.Game;
import view.ui.buttons.LevelButton;
import view.ui.buttons.ReturnButton;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class LevelSelection extends State {

    private static LevelSelection instance = new LevelSelection();
    private int selectedLevel;

    private LevelSelection() {
        super("LevelSelection");
    }

    public static LevelSelection getInstance() {
        return instance;
    }

    @Override
    public void enter() {
        System.out.println("Entering LevelSelection");
        // We want to display the three levels available
        JPanel borderJPanel = new JPanel(new BorderLayout());
        borderJPanel.setBounds(0, 0, Game.INSTANCE.getGameScreen().getPanelSize().width,
                Game.INSTANCE.getGameScreen().getPanelSize().height);
        // We add the return button to the background at the bottom left
        ReturnButton returnButton = new ReturnButton(Menu.getInstance());
        borderJPanel.add(returnButton, BorderLayout.SOUTH);
        borderJPanel.validate();
        Menu.getInstance().background.add(borderJPanel);

        borderJPanel.setOpaque(false);
        JPanel buttonsLevelWrapper = new JPanel();
        buttonsLevelWrapper.setOpaque(false);
        buttonsLevelWrapper.setBounds(0, 0, Game.INSTANCE.getGameScreen().getPanelSize().width,
                Game.INSTANCE.getGameScreen().getPanelSize().height);

        // We set the layout to a GridBagLayout
        // We set the layout to a BorderLayout
        buttonsLevelWrapper.setLayout(new BoxLayout(buttonsLevelWrapper, BoxLayout.X_AXIS));

        // We create the buttons
        LevelButton level1 = new LevelButton(1);
        LevelButton level2 = new LevelButton(2);
        LevelButton level3 = new LevelButton(3);

        // Set the preferred size of the buttons to be 1/4 of the screen height
        Dimension buttonSize = new Dimension(buttonsLevelWrapper.getWidth() / 4, buttonsLevelWrapper.getHeight() / 4);
        level1.setPreferredSize(buttonSize);
        level2.setPreferredSize(buttonSize);
        level3.setPreferredSize(buttonSize);

        // We add the buttons to the wrapper with glue for padding and alignment
        buttonsLevelWrapper.add(Box.createHorizontalGlue()); // Add padding
        buttonsLevelWrapper.add(level1); // Add the first button to the left
        buttonsLevelWrapper.add(Box.createHorizontalGlue()); // Add padding
        buttonsLevelWrapper.add(level2); // Add the second button to the center
        buttonsLevelWrapper.add(Box.createHorizontalGlue()); // Add padding
        buttonsLevelWrapper.add(level3); // Add the third button to the right
        buttonsLevelWrapper.add(Box.createHorizontalGlue()); // Add padding
        buttonsLevelWrapper.validate();

        // We add the return button to the background at the bottom left
        

        // We add the wrapper to the background
        Menu.getInstance().background.add(buttonsLevelWrapper);
        Menu.getInstance().background.validate();
    }

    @Override
    public void exit() {
        System.out.println("Exiting LevelSelection");
        // We remove the buttons from the background
        Menu.getInstance().background.removeAll();
    }

    @Override
    public void transitionTo(State s) {
        System.out.println("Transitioning from LevelSelection to " + s.showState());
    }

    @Override
    public void updateGraphics(java.awt.Graphics g) {

    }

    @Override
    public void updateLogic(long deltaT) {

    }
}
