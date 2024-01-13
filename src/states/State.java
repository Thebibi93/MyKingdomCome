package states;

import java.awt.Graphics;

public class State implements StatesMethods {

    // This is the default state class, it is used to create new states

    private final String stateName;

    public State(String stateName) {
        this.stateName = stateName;
    }
    

    public String showState() {
        return stateName;
    }

    @Override
    public void updateGraphics(Graphics g) {

    }

    @Override
    public void updateLogic(long deltaT) {

    }

    @Override
    public void mouseClicked(int x, int y) {

    }

    @Override
    public void exit() {

    }

    @Override
    public void transitionTo(State s) {

    }

    @Override
    public void enter() {

    }
}
