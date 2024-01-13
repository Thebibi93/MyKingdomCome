package states;

import view.ui.LevelDescription;
import view.ui.buttons.EasyButton;
import view.ui.buttons.HardButton;
import view.ui.buttons.InfiniteButton;
import view.ui.buttons.MediumButton;
import view.ui.buttons.ReturnButton;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class DifficultySelection extends State {

    private static DifficultySelection instance = new DifficultySelection();
    private int selectedDifficulty;
    private int selectedLevel;

    private DifficultySelection() {
        super("DifficultySelection");
    }

    public void setSelectedLevel(int level) {
        selectedLevel = level;
    }

    public static DifficultySelection getInstance() {
        return instance;
    }

    @Override
    public void enter() {
        System.out.println("Entering DifficultySelection");
        // We have four difficulties to choose from Easy, Medium and Hard and then
        // INFINITE
        EasyButton easyButton = new EasyButton(selectedLevel);
        MediumButton mediumButton = new MediumButton(selectedLevel);
        HardButton hardButton = new HardButton(selectedLevel);
        InfiniteButton infiniteButton = new InfiniteButton(selectedLevel);

        // Set the preferred size of the buttons to be 1/4 of the screen height
        Dimension buttonSize = new Dimension(Menu.getInstance().background.getWidth() / 5,
                Menu.getInstance().background.getHeight() / 5);
        easyButton.setPreferredSize(buttonSize);
        mediumButton.setPreferredSize(buttonSize);
        hardButton.setPreferredSize(buttonSize);
        infiniteButton.setPreferredSize(buttonSize);

        // We load the level description
        LevelDescription levelDescription = new LevelDescription(selectedLevel);
        levelDescription.setPreferredSize(new Dimension(Menu.getInstance().background.getWidth(),
                Menu.getInstance().background.getHeight() / 2));

        JPanel gridJPanel = new JPanel();
        gridJPanel.setOpaque(false);
        gridJPanel.setBounds(0, 0, Menu.getInstance().background.getWidth(),
                Menu.getInstance().background.getHeight());
        gridJPanel.setLayout(new GridLayout(2, 1));

        JPanel buttonsDifficultyWrapper = new JPanel();
        buttonsDifficultyWrapper.setOpaque(false);
        buttonsDifficultyWrapper.setLayout(new BoxLayout(buttonsDifficultyWrapper, BoxLayout.X_AXIS));

        // Add buttons to the wrapper with padding
        buttonsDifficultyWrapper.add(Box.createHorizontalGlue()); // Add padding
        buttonsDifficultyWrapper.add(easyButton);
        buttonsDifficultyWrapper.add(Box.createHorizontalStrut(10)); // Add 10 pixels of padding
        buttonsDifficultyWrapper.add(mediumButton);
        buttonsDifficultyWrapper.add(Box.createHorizontalStrut(10)); // Add 10 pixels of padding
        buttonsDifficultyWrapper.add(hardButton);
        buttonsDifficultyWrapper.add(Box.createHorizontalStrut(10)); // Add 10 pixels of padding
        buttonsDifficultyWrapper.add(infiniteButton);
        buttonsDifficultyWrapper.add(Box.createHorizontalGlue()); // Add padding

        JPanel returnButtonPlace = new JPanel();
        returnButtonPlace.setOpaque(false);
        returnButtonPlace.setBounds(0, 0, Menu.getInstance().background.getWidth(),
                Menu.getInstance().background.getHeight());
        returnButtonPlace.setLayout(new BorderLayout());
        ReturnButton returnButton = new ReturnButton(LevelSelection.getInstance());
        returnButton.setSize(Menu.getInstance().background.getWidth() / 5,
                Menu.getInstance().background.getHeight() / 5);
        returnButtonPlace.add(returnButton, BorderLayout.SOUTH);
        returnButtonPlace.validate();
        Menu.getInstance().background.add(returnButtonPlace);
        
        gridJPanel.add(buttonsDifficultyWrapper);
        gridJPanel.validate();
        gridJPanel.add(levelDescription);
        gridJPanel.validate();

        Menu.getInstance().background.add(gridJPanel);
        Menu.getInstance().background.validate();
    }

    @Override
    public void exit() {
        System.out.println("Exiting DifficultySelection");
        Menu.getInstance().background.removeAll();
    }

    @Override
    public void transitionTo(State s) {
        System.out.println("Transitioning from DifficultySelection to " + s.showState());
    }

    @Override
    public void updateGraphics(java.awt.Graphics g) {

    }

    @Override
    public void updateLogic(long deltaT) {

    }
}
