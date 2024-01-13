package view.maps;

import javax.swing.JPanel;

import controllers.CellsController;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import model.Cells;
import java.awt.Image;
import java.awt.Graphics2D;

public class MapBuilder {
    public static int[][] getMap1() {

        int[][] lvl1 = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 21 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 6, 6, 6, 6, 17, 18 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 1, 1, 1, 1, 17, 18 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 1, 4, 5, 1, 19, 22 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 6, 6, 2, 1, 9, 8, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 2, 1, 1, 1, 1, 9, 13, 7, 7, 7 },
                { 0, 0, 0, 0, 0, 0, 0, 20, 21, 1, 1, 1, 4, 7, 7, 12, 0, 0, 0, 0 },
                { 0, 0, 20, 15, 15, 15, 15, 23, 18, 1, 4, 7, 12, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 17, 14, 14, 14, 14, 14, 18, 1, 3, 6, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 19, 16, 16, 16, 16, 16, 22, 1, 1, 1, 20, 21, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 8, 1, 1, 1, 1, 9, 0, 7, 5, 1, 17, 18, 0, 0, 0, 0, 0, 0 },
                { 0, 10, 2, 1, 4, 5, 1, 9, 0, 0, 8, 1, 17, 18, 0, 0, 0, 0, 0, 0 },
                { 0, 8, 1, 1, 9, 8, 1, 3, 6, 6, 2, 1, 17, 18, 0, 0, 0, 0, 0, 0 },
                { 6, 2, 1, 4, 12, 8, 1, 1, 1, 1, 1, 1, 17, 18, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 9, 0, 0, 20, 15, 15, 15, 15, 15, 23, 18, 0, 0, 0, 0, 0, 0 },
                { 7, 7, 7, 12, 0, 0, 19, 16, 16, 16, 16, 16, 16, 22, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        };

        return lvl1;
    }

    public static int[][] getMap2() {
        int[][] lvl2 = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 1, 9, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 1, 9, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 20, 15, 15, 21, 1, 9, 0, 0, 0, 0, 0, 20, 21},
                {0, 0, 0, 0, 0, 0, 0, 17, 14, 14, 18, 1, 9, 10, 6, 6, 6, 6, 17, 18},
                {0, 0, 0, 0, 0, 0, 0, 17, 14, 14, 18, 1, 9, 8, 1, 1, 1, 1, 17, 18},
                {0, 0, 0, 0, 0, 0, 0, 17, 14, 14, 18, 1, 9, 8, 1, 4, 5, 1, 19, 22},
                {0, 0, 0, 0, 0, 0, 0, 17, 14, 14, 18, 1, 3, 2, 1, 9, 8, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 17, 14, 16, 22, 1, 1, 1, 1, 9, 13, 7, 7, 7},
                {0, 0, 0, 0, 0, 0, 0, 17, 18, 1, 1, 1, 4, 7, 7, 12, 0, 0, 0, 0},
                {0, 0, 20, 15, 15, 15, 15, 23, 18, 1, 4, 7, 12, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 17, 14, 14, 14, 14, 14, 18, 1, 3, 6, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 19, 16, 16, 16, 16, 16, 22, 1, 1, 1, 20, 21, 0, 0, 0, 0, 0, 0},
                {0, 0, 8, 1, 1, 1, 1, 9, 0, 7, 5, 1, 17, 18, 0, 0, 0, 0, 0, 0},
                {0, 10, 2, 1, 4, 5, 1, 9, 0, 0, 8, 1, 17, 18, 0, 0, 0, 0, 0, 0},
                {0, 8, 1, 1, 9, 8, 1, 3, 6, 6, 2, 1, 17, 18, 0, 0, 0, 0, 0, 0},
                {6, 2, 1, 4, 12, 8, 1, 1, 1, 1, 1, 1, 17, 18, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 9, 0, 0, 20, 15, 15, 15, 15, 15, 23, 18, 0, 0, 0, 0, 0, 0},
                {7, 7, 7, 12, 0, 0, 19, 16, 16, 16, 16, 16, 16, 22, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        return lvl2;
    }

    public static int[][] getMap3() {
        int[][] lvl3 = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 1, 9, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 10, 6, 2, 1, 9, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 6, 2, 1, 1, 1, 9, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 20, 21, 1, 1, 1, 4, 7, 12, 0, 0, 0, 0, 0, 0, 0 },
                { 20, 15, 15, 15, 15, 23, 18, 1, 4, 7, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 17, 14, 14, 14, 14, 14, 18, 1, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 17, 14, 14, 14, 14, 14, 18, 1, 9, 0, 0, 20, 15, 15, 21, 0, 0, 0, 0, 0 },
                { 17, 14, 14, 14, 14, 14, 18, 1, 3, 6, 6, 19, 16, 25, 18, 0, 0, 0, 0, 0 },
                { 17, 14, 14, 14, 14, 14, 18, 1, 1, 1, 1, 1, 1, 17, 18, 0, 0, 0, 0, 0 },
                { 19, 16, 25, 14, 14, 14, 24, 21, 1, 4, 7, 5, 1, 17, 18, 0, 0, 0, 0, 0 },
                { 0, 0, 19, 16, 16, 16, 16, 22, 1, 9, 0, 8, 1, 19, 22, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 8, 1, 1, 1, 1, 9, 0, 8, 1, 9, 0, 10, 6, 6, 6, 6 },
                { 0, 0, 0, 0, 8, 1, 4, 7, 7, 12, 0, 8, 1, 9, 0, 8, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 8, 1, 9, 0, 0, 0, 0, 8, 1, 3, 6, 2, 1, 4, 7, 7 },
                { 0, 0, 10, 6, 2, 1, 20, 15, 15, 15, 15, 21, 1, 1, 1, 1, 1, 9, 0, 0 },
                { 6, 6, 2, 1, 1, 1, 17, 14, 14, 14, 14, 24, 15, 15, 21, 7, 7, 12, 0, 0 },
                { 1, 1, 1, 1, 4, 7, 17, 14, 14, 14, 14, 14, 14, 14, 18, 0, 0, 0, 0, 0},
                { 7, 7, 7, 7, 12, 0, 17, 14, 14, 14, 14, 14, 14, 14, 18, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 19, 16, 16, 16, 16, 16, 16, 16, 22, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };

        return lvl3;
    }

    public static int[][] getMap(int level) {
        switch (level) {
            case 1:
                return getMap1();
            case 2:
                return getMap2();
            case 3:
                return getMap3();
            default:
                return getMap1();
        }
    }

    public static ArrayList<CellsController> buildMap(int level, JPanel panel, int tileSize) {
        System.out.println("Building map...");
        CellsBuilder cellsBuilder = new CellsBuilder();
        int[][] map = getMap(level);
        int numRows = map.length;
        int numCols = map[0].length;
        ArrayList<CellsController> cells = new ArrayList<CellsController>();
        panel.removeAll();
        panel.setLayout(new java.awt.GridLayout(numRows, numCols));

        // We want to loop through the map and draw each cell
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int cellType = map[i][j];
                // Create an instance of the cell for each type of cell
                CellsController cell = new CellsController(cellsBuilder.cells.get(cellType));

                // Define the position of the cell
                cell.getModel().setX(j * tileSize);
                cell.getModel().setY(i * tileSize);
                cell.getModel().setSize(tileSize);

                cell.getView().setSize(tileSize, tileSize);

                // Convert BufferedImage to Image and scale it
                Image image = cell.getView().getCellImage().getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);

                // Convert the scaled Image back to a BufferedImage
                BufferedImage scaledImage = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = scaledImage.createGraphics();
                g.drawImage(image, 0, 0, null);
                g.dispose();

                cell.getView().setCellImage(scaledImage);

                cells.add(cell);
                panel.add(cell.getView());
            }
        }

        // We want to set the neighbors for each cell
        // We want to set the neighbors for each cell
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Cells cell = cells.get(i * numCols + j).getModel();
                if (j < numCols - 1) { // East neighbor
                    Cells eastNeighbor = cells.get(i * numCols + (j + 1)).getModel();
                    cell.setEastNeighbor(eastNeighbor);
                } else {
                    cell.setEastNeighbor(null);
                }
                if (i < numRows - 1) { // South neighbor
                    Cells southNeighbor = cells.get((i + 1) * numCols + j).getModel();
                    cell.setSouthNeighbor(southNeighbor);
                } else {
                    cell.setSouthNeighbor(null);
                }
                if (j > 0) { // West neighbor
                    Cells westNeighbor = cells.get(i * numCols + (j - 1)).getModel();
                    cell.setWestNeighbor(westNeighbor);
                } else {
                    cell.setWestNeighbor(null);
                }
                if (i > 0) { // North neighbor
                    Cells northNeighbor = cells.get((i - 1) * numCols + j).getModel();
                    cell.setNorthNeighbor(northNeighbor);
                } else {
                    cell.setNorthNeighbor(null);
                }
            }
        }

        // We want to sort the cells by their y coordinate so we can use them in the
        // Board class
        cells.sort((c1, c2) -> c1.getModel().getY() - c2.getModel().getY());

        // We want to return the cells so we can use them in the Board class
        return cells;
    }
}
