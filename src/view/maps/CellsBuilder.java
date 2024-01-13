package view.maps;

import javax.imageio.ImageIO;

import controllers.CellsController;
import model.Cells;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

// This class is responsible for importing the background image and creating the cells

public class CellsBuilder {
    public CellsController GRASS, ANGLE1, ANGLE2, ANGLE3, ANGLE4,
            ROAD, TOP, BOTTOM, RIGHT, LEFT,
            CORNER1, CORNER2, CORNER3, CORNER4,
            WATER, WATER_TOP, WATER_BOTTOM, WATER_RIGHT, WATER_LEFT,
            WATER_ANGLE1, WATER_ANGLE2, WATER_ANGLE3, WATER_ANGLE4,
            WATER_CORNER1, WATER_CORNER2, WATER_CORNER3, WATER_CORNER4;
    public BufferedImage background_img;
    public ArrayList<CellsController> cells = new ArrayList<>();

    public CellsBuilder() {
        importImg();
        buildCells();
    }

    private void importImg() {
        InputStream inputStream = getClass().getResourceAsStream("/background_img.png");

        try {
            background_img = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildCells() {
        cells.add(GRASS = new CellsController(new Cells(),
                new CellsView(getImage(1, 6)))); // 0
        cells.add(ROAD = new CellsController(new Cells(true),
                new CellsView(getImage(1, 17)))); // 1
        cells.add(ANGLE1 = new CellsController(new Cells(),
                new CellsView(getImage(6, 4)))); // 2
        cells.add(ANGLE2 = new CellsController(new Cells(),
                new CellsView(getImage(0, 5)))); // 3
        cells.add(ANGLE3 = new CellsController(new Cells(),
                new CellsView(getImage(2, 4)))); // 4
        cells.add(ANGLE4 = new CellsController(new Cells(),
                new CellsView(getImage(4, 4)))); // 5
        cells.add(TOP = new CellsController(new Cells(),
                new CellsView(getImage(4, 3)))); // 6
        cells.add(BOTTOM = new CellsController(new Cells(),
                new CellsView(getImage(4, 2)))); // 7
        cells.add(LEFT = new CellsController(new Cells(),
                new CellsView(getImage(0, 3)))); // 8
        cells.add(RIGHT = new CellsController(new Cells(),
                new CellsView(getImage(0, 2)))); // 9
        cells.add(CORNER1 = new CellsController(new Cells(false, true),
                new CellsView(getImage(4, 0)))); // 10
        cells.add(CORNER2 = new CellsController(new Cells(false, true),
                new CellsView(getImage(0, 1)))); // 11
        cells.add(CORNER3 = new CellsController(new Cells(false, true),
                new CellsView(getImage(1, 0)))); // 12
        cells.add(CORNER4 = new CellsController(new Cells(false, true),
                new CellsView(getImage(2, 0)))); // 13
        cells.add(WATER = new CellsController(new Cells(false, false),
                new CellsView(getImage(0, 13)))); // 14
        cells.add(WATER_TOP = new CellsController(new Cells(false, false),
                new CellsView(getImage(4, 9)))); // 15
        cells.add(WATER_BOTTOM = new CellsController(new Cells(false, false),
                new CellsView(getImage(4, 10)))); // 16
        cells.add(WATER_LEFT = new CellsController(new Cells(false, false),
                new CellsView(getImage(0, 9)))); // 17
        cells.add(WATER_RIGHT = new CellsController(new Cells(false, false),
                new CellsView(getImage(0, 10)))); // 18
        cells.add(WATER_ANGLE1 = new CellsController(new Cells(false, false),
                new CellsView(getImage(0, 12)))); // 19
        cells.add(WATER_ANGLE2 = new CellsController(new Cells(false, false),
                new CellsView(getImage(2, 11)))); // 20
        cells.add(WATER_ANGLE3 = new CellsController(new Cells(false, false),
                new CellsView(getImage(4, 11)))); // 21
        cells.add(WATER_ANGLE4 = new CellsController(new Cells(false, false),
                new CellsView(getImage(6, 11)))); // 22
        cells.add(WATER_CORNER1 = new CellsController(new Cells(false, false),
                new CellsView(getImage(1, 7)))); // 23
        cells.add(WATER_CORNER2 = new CellsController(new Cells(false, false),
                new CellsView(getImage(2, 7)))); // 24
        cells.add(WATER_CORNER3 = new CellsController(new Cells(false, false),
                new CellsView(getImage(0, 8)))); // 25
        cells.add(WATER_CORNER4 = new CellsController(new Cells(false, false),
                new CellsView(getImage(4, 7)))); // 26
    }

    public BufferedImage getImage(int id) {
        return cells.get(id).getView().getCellImage();
    }

    private BufferedImage getImage(int x, int y) {
        return background_img.getSubimage(x * 32, y * 32, 32, 32);
    }
}