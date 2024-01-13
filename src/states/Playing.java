package states;

import java.awt.Graphics;
import controllers.BoardController;
import view.main.Game;

public class Playing extends State {

    private static final Playing INSTANCE = new Playing("Playing");
    private BoardController boardController;
    private boolean isInitialized = false;
    private int selectedLevel;
    private int selectedDifficulty;

    public Playing(String name) {
        super(name);
    }

    public void setSelectedLevel(int level){
        selectedLevel = level;
    }

    public void setSelectedDifficulty(int difficulty){
        selectedDifficulty = difficulty;
    }

    public static Playing getInstance(){
        return INSTANCE;
    }

    public void enter(){
        System.out.println("Playing");
        boardController = new BoardController(selectedLevel, selectedDifficulty);
        isInitialized = true;
    }

    public void exit(){
        System.out.println("Exit Playing");
        isInitialized = false;
        // We clear the screen
        Game.INSTANCE.getGameScreen().removeAll();
    }

    @Override
    public void updateLogic(long deltaT) {
        if(!isInitialized)
            return;
        boardController.updateLogic(deltaT);
    }

    @Override
    public void updateGraphics(Graphics g) {
    }

    public void mouseClicked(int x, int y){

    }

    public void transitionTo(State s){

    }
}
