package view.main;

import java.awt.Graphics;

import states.*;

//Simple enum to keep track of the current state of the game

public enum GameStates {
    MENU(Menu.getInstance()),
    PLAYING(Playing.getInstance()),
    SETTINGS(Settings.getInstance()),
    LEVEL_SELECTION(LevelSelection.getInstance()),;

    private State currentState;

    private GameStates(State initial_state) {
        currentState = initial_state;
    }

    public State getState() {
        return currentState;
    }

    public String showState(){
        return currentState.showState();
    }

    public void changeState(State s) {
        currentState.exit();
        currentState.transitionTo(s);
        currentState = s;
        currentState.enter();
    }

    // This method is called every time the game is repainted to update the graphics

    public void updateGraphics(Graphics g) {
        currentState.updateGraphics(g);
    }

    // This method is called every frame to update the game logic

    public void updateLogic(long deltaT) {
        currentState.updateLogic(deltaT);
    }
}

