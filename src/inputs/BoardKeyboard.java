package inputs;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import controllers.BoardController;
import controllers.CellsController;
import controllers.TowerController;

import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;

public class BoardKeyboard implements KeyListener {

    private TowerController selectedTower;
    private BoardController boardController;
    private ArrayList<TowerController> selectedTowers;
    private int previousKeyNumber = -1;

    public BoardKeyboard(ArrayList<TowerController> selectedTowers, BoardController boardController) {
        this.selectedTowers = selectedTowers;
        this.boardController = boardController;

    }

    // This class is used to handle the keyboard inputs of the player when he is in
    // an active game
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed");
        int keyNumber = previousKeyNumber;
        boardController.getView().removeRangePreview();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                selectedTower = selectedTowers.get(0);
                keyNumber = 0;
                System.out.println("A");
                break;
            case KeyEvent.VK_Z:
                selectedTower = selectedTowers.get(1);
                keyNumber = 1;
                System.out.println("Z");
                break;
            case KeyEvent.VK_E:
                selectedTower = selectedTowers.get(2);
                keyNumber = 2;
                System.out.println("E");
                break;
            case KeyEvent.VK_R:
                selectedTower = selectedTowers.get(3);
                keyNumber = 3;
                System.out.println("R");
                break;
            default:
                // Handle other keys if necessary
                break;
        }
        if (selectedTower == null) {
            return;
        }
        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePos, boardController.getView());
        CellsController currentCell = boardController.getCellAt(mousePos.x, mousePos.y);
        // Unhighlight the previously selected tower
        if (previousKeyNumber != -1) {
            boardController.getView().getMenuPanel().unhighlightTowerIcon(previousKeyNumber);
        }
        boardController.getView().getMenuPanel().highlightTowerIcon(keyNumber);
        previousKeyNumber = keyNumber;
        if (currentCell == null) {
            return;
        }
        currentCell.addTowerPreview(selectedTower.getView());
        boardController.getView().createRangePreview(selectedTower.getModel().getRange());
        boardController.getView().updateRangePreviewPosition(currentCell.getModel().getCenter().x(),
                currentCell.getModel().getCenter().y());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public TowerController getSelectedTower() {
        return selectedTower;
    }
}