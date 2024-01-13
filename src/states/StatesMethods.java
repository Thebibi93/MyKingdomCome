package states;

import java.awt.Graphics;

public interface StatesMethods{

    // This is the contract that all states must follow

    public void updateGraphics(Graphics g);

    public void updateLogic(long deltaT);

    public void mouseClicked(int x, int y);

    public void exit();

    public void transitionTo(State s);

    public void enter();

    public static void getInstance(){

    };
}
