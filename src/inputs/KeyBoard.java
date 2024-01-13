package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import states.*;
import view.main.Game;

public class KeyBoard implements KeyListener{

    public static final KeyBoard INSTANCE = new KeyBoard();
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_M) {
            Game.getGameState().changeState(Menu.getInstance());
            System.out.println("M pressed");
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Game.getGameState().changeState(Playing.getInstance());
            System.out.println("Enter pressed");
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            Game.getGameState().changeState(Settings.getInstance());
            System.out.println("S pressed");
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
