package view.maps;

import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.Color;
import javax.swing.SwingWorker;

import view.towers.TowerView;

// This class is responsible for drawing the cells on the screen
public class CellsView extends JPanel {
    private Highlight color = Highlight.NONE;
    private BufferedImage cellImage;
    private TowerView towerPreview;
    private TowerView towerView;

    public CellsView(BufferedImage cellImage) {
        this.cellImage = cellImage;
        setLayout(new java.awt.GridLayout(1, 1));
    }

    // Copy constructor
    public CellsView(CellsView cellsView) {
        this.cellImage = cellsView.getCellImage();
        setLayout(new java.awt.GridLayout(1, 1));
    }

    public BufferedImage getCellImage() {
        return cellImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cellImage != null) {
            g.drawImage(cellImage, 0, 0, this);
        }
        if (!color.equals(Highlight.NONE)) {
            Graphics2D g2 = (Graphics2D) g.create();
            double thickness = 4;
            Stroke oldStroke = g2.getStroke();
            g2.setStroke(new BasicStroke((float) thickness));

            // Set the color based on the highlight field
            switch (color) {
                case BLUE:
                    g2.setColor(Color.BLUE);
                    break;
                case WHITE:
                    g2.setColor(Color.WHITE);
                    break;
                case RED:
                    g2.setColor(Color.RED);
                    break;
                case NONE:
                    break;
            }
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            g2.setStroke(oldStroke);
            g2.dispose();
        }
    }

    public void setCellImage(BufferedImage cellImage) {
        this.cellImage = cellImage;
    }

    public void highlight(Highlight color) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                CellsView.this.color = color;
                return null;
            }

            @Override
            protected void done() {
                repaint();
            }
        };
        worker.execute();
    }

    public void unhighlight() {
        color = Highlight.NONE;
        repaint();
    }

    public void addTowerPreview(TowerView towerPreview) {
        if(towerView != null){
            return;
        }
        removeTowerPreview();
        this.towerPreview = towerPreview;
        this.add(towerPreview);
    }

    public void buildTower(TowerView towerView) {
        this.towerView = towerView;
        this.add(towerView);
    }

    public void removeTower() {
        if (this.towerView != null) {
            this.remove(this.towerView);
            this.towerView = null;
        }
    }

    public void removeTowerPreview() {
        if (this.towerPreview != null){
            this.remove(this.towerPreview);
            this.towerPreview = null;
        }
    }

    public TowerView getTowerPreview() {
        return towerPreview;
    }

    public TowerView getTowerView() {
        return towerView;
    }
}
