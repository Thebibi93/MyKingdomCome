package view.main;

import java.awt.Graphics;

public class Affichage {

    public Affichage() {
      
    }

    public void updateGraphics(Graphics g) {
        Game.getGameState().updateGraphics(g);
    }
}
