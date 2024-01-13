package inputs;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import view.main.Game;

public class Mouse implements MouseListener,MouseMotionListener{

    private Game game;
    public static final Mouse INSTANCE = new Mouse(Game.INSTANCE);

    private Mouse(Game game){
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e){

        if(e.getButton() == MouseEvent.BUTTON1) {
            switch(Game.getGameState()){
                case MENU: this.game.getMenu().mouseClicked(e.getX(),e.getY());
                    break;
                case PLAYING: this.game.getPlaying().mouseClicked(e.getX(),e.getY());
                    break;
                case SETTINGS: this.game.getSettings().mouseClicked(e.getX(),e.getY());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + Game.getGameState());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
