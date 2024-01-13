package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import controllers.BoardController;
import controllers.CellsController;
import controllers.TowerController;
import model.Cells;
import view.maps.Highlight;

public class BoardMouse implements MouseListener, MouseMotionListener {

    private BoardController boardController;
    private CellsController hoveredCell;
    private BoardKeyboard keyboard;

    /*
     * We want to get the cell corresponding to the coordinates of the mouse
     * We can get the coordinates of the mouse by calling the getX() and getY()
     * methods of the MouseEvent
     */

    public BoardMouse(BoardController boardController, BoardKeyboard keyboard) {
        // We want to get the boardController to be able to access the cells
        this.boardController = boardController;
        this.keyboard = keyboard;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int cellIndex = 0;
        for (CellsController cell : boardController.getCellsController()) {
            if (isMouseOnCell(e, cell.getModel())) {
                cellClicked(cell, e, cell.getModel().getX(), cell.getModel().getY(), cell.getModel().getSize());
                System.out.println("Cell index: " + cellIndex); // Useful for debugging
                break;
            }
            cellIndex++;
        }
    }

    private boolean isMouseOnCell(MouseEvent e, Cells cell) {
        // Vérifier si le clic de la souris est effectué sur la cellule
        return e.getX() >= cell.getX() && e.getX() <= cell.getX() + cell.getSize() &&
                e.getY() >= cell.getY() && e.getY() <= cell.getY() + cell.getSize();
    }

    /*
     * If the player right clicks on a cell with a tower we want to sell the tower
     * If the player left clicks on a cell without a tower we want to select the
     * cell and then if he presses the a key we want to buy a tower on the selected
     * cell
     * If the player double left clicks on a cell with a tower we want to upgrade
     * the tower
     */

    private void cellClicked(CellsController c, MouseEvent e, int x, int y, int size) {
        System.out.println("Cell clicked on " + x + " " + y);
        System.out.println("Is Buildable " + c.getModel().isBuildable());
        System.out.println("Tower in cell : " + c.getModel().getTower());
        System.out.println("Is walkable " + c.getModel().isWalkable());
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                if (e.getClickCount() == 2 && c.getModel().getTower() != null) {
                    System.out.println("Double click");
                    boardController.upgradeTower(c);
                    return;
                }
                if (!c.getModel().isBuildable()) {
                    return;
                } else if (c.getModel().getTower() == null) {
                    TowerController tower = keyboard.getSelectedTower();
                    if (tower != null) {
                        System.out.println("Build tower");
                        boardController.buyTower(tower, c);
                        return;
                    }
                }
                break;
            case MouseEvent.BUTTON3:
                if (c.getModel().getTower() == null) {
                    System.out.println("No tower");
                    c.getView().highlight(Highlight.RED);
                } else {
                    System.out.println("Sell tower");
                    boardController.sellTower(c);
                }
                break;
            default:
                // Handle other buttons if necessary
                break;
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        CellsController cell = boardController.getCellAt(e.getX(), e.getY());
        if (cell.getModel().getTower() != null) {
            // We want to display the range of the tower
            boardController.getView().createRangePreview(cell.getModel().getTower().getRange());
            updateRangePreviewPosition(e.getX(), e.getY());
            // We want to display the tower stats
            boardController.getView().showTowerStats(cell.getModel().getTower());
            // We also remove the tower preview from the previous cell
            if (hoveredCell != null) {
                hoveredCell.getView().removeTowerPreview();
                hoveredCell.getView().unhighlight();
                hoveredCell = cell;
            }
            // And we highlight the cell in blue
            cell.getView().highlight(Highlight.BLUE);
            return;
        }
        if (keyboard.getSelectedTower() != null) {
            boardController.getView().createRangePreview(keyboard.getSelectedTower().getModel().getRange());
            updateRangePreviewPosition(e.getX(), e.getY());
            if (hoveredCell != cell && hoveredCell != null) {
                boardController.getView().hideTowerStats();
                hoveredCell.getView().removeTowerPreview();
                cell.addTowerPreview(keyboard.getSelectedTower().getView());
            }
        } else {
            if (hoveredCell != null) {
                hoveredCell.getView().removeTowerPreview();
            }
        }
        if (cell != null) {
            highlightCell(cell);
        }
        if (hoveredCell != null && hoveredCell != cell) {
            unhighlightCell(hoveredCell);
        }
        hoveredCell = cell;
    }

    private void updateRangePreviewPosition(int mouseX, int mouseY) {
        int cellSize = boardController.getView().getTileSize();
        int cellCenterX = (mouseX / cellSize) * cellSize + cellSize / 2;
        int cellCenterY = (mouseY / cellSize) * cellSize + cellSize / 2;
        boardController.getView().updateRangePreviewPosition(cellCenterX, cellCenterY);
    }

    private void unhighlightCell(CellsController cell) {
        cell.getView().unhighlight();
    }

    private void highlightCell(CellsController cell) {
        if (cell.getModel().isBuildable()) {
            cell.getView().highlight(Highlight.WHITE);
        } else if (cell.getModel().getTower() != null) {
            cell.getView().highlight(Highlight.BLUE);
        } else {
            cell.getView().highlight(Highlight.RED);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
    }

}
