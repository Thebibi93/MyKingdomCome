package view.maps;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import controllers.ProjectileController;
import model.gameEntities.towers.Tower;
import util.AnimatedSprite;
import util.geometry.RealCoordinates;
import view.ui.GameOverPanel;
import view.ui.GameUiPanel;
import view.ui.GameWinPanel;
import view.main.GameScreen;
import view.projectile.ProjectileView;
import java.awt.Transparency;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Color;
import java.awt.AlphaComposite;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;

public class BoardView extends JLayeredPane {
    private JPanel cellPanel = new JPanel();
    private JPanel enemyPanel = new JPanel();
    private JPanel projectilePanel = new JPanel();
    private GameUiPanel menuPanel = new GameUiPanel();
    private GameScreen gameScreen;
    private ArrayList<CellsView> cellsViews = new ArrayList<CellsView>();
    private ArrayList<ProjectileController> projectiles = new ArrayList<ProjectileController>();
    private ConcurrentHashMap<AnimatedSprite, RealCoordinates> explosions = new ConcurrentHashMap<>();
    private int tileSize;
    private JPanel rangePreviewPanel = new JPanel();
    private Ellipse2D rangePreview;

    public BoardView(GameScreen gameScreen, int lvl) {
        this.setPreferredSize(gameScreen.getPanelSize());
        this.setMaximumSize(gameScreen.getPanelSize());
        this.setMinimumSize(gameScreen.getPanelSize());
        this.setSize(gameScreen.getPanelSize());
        this.setOpaque(false);

        enemyPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(BoardView.this.getWidth(), BoardView.this.getHeight());
            }
        };
        enemyPanel.setOpaque(false);

        rangePreviewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (rangePreview != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setColor(Color.BLUE);
                    g2d.draw(rangePreview);
                }
            }
        };

        projectilePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                for (Map.Entry<AnimatedSprite, RealCoordinates> entry : explosions.entrySet()) {
                    // If the explosion is finished, remove it from the list
                    // Otherwise, draw it
                    RealCoordinates position = entry.getValue();
                    AnimatedSprite explosion = entry.getKey();
                    BufferedImage nextFrame = explosion.displayNextFrame();
                
                    // Accelerate the image loading
                    GraphicsConfiguration gc = g2d.getDeviceConfiguration();
                    BufferedImage image = gc.createCompatibleImage(nextFrame.getWidth(), nextFrame.getHeight(), Transparency.TRANSLUCENT);
                    Graphics2D g2d2 = image.createGraphics();
                    // We need to center the explosion
                    int x = (int) position.x();
                    int y = (int) position.y();
                    g2d2.setComposite(AlphaComposite.Src);
                    g2d2.drawImage(nextFrame, 0, 0, null);
                    g2d2.dispose();
                
                    g2d.drawImage(image, x, y, null);
                }
                ArrayList<ProjectileController> copy = new ArrayList<>(projectiles);
                for (ProjectileController projectile : copy) {
                    RealCoordinates position = projectile.getModel().getPosition();
                    double rotation = projectile.getModel().getAngle();
                    ProjectileView view = projectile.getView();
                    double imageWidth = view.getImage().getWidth(null);
                    double imageHeight = view.getImage().getHeight(null);

                    AffineTransform oldTransform = g2d.getTransform();
                    g2d.translate(position.x() + imageWidth / 2, position.y() + imageHeight / 2);
                    g2d.rotate(rotation);
                    g2d.translate(-imageWidth / 2, -imageHeight / 2);
                    g2d.drawImage(view.getImage(), 0, 0, null);
                    g2d.setTransform(oldTransform);
                }
                g2d.dispose();
            }
        };

        int[][] level = MapBuilder.getMap(lvl);
        int levelHeight = level.length;
        this.gameScreen = gameScreen;
        tileSize = (int) (gameScreen.getPanelSize().getHeight() / levelHeight);
        enemyPanel.setOpaque(false);
        projectilePanel.setOpaque(false);
        rangePreviewPanel.setOpaque(false);
        cellPanel.setOpaque(false);
        menuPanel.setOpaque(false);
    }

    @Override
    public void doLayout() {
        super.doLayout();
        int width = this.getWidth();
        int height = this.getHeight();
        cellPanel.setSize(width, height);
        enemyPanel.setSize(width, height);
        projectilePanel.setSize(width, height);
        rangePreviewPanel.setSize(width, height);
    }

    public void addExplosion(AnimatedSprite explosion, RealCoordinates position) {
        explosions.put(explosion, position);
    }

    public void removeExplosion(AnimatedSprite explosion) {
        explosions.remove(explosion);
        System.out.println("Explosion removed");
    }

    public void displayGameOver(int score) {
        // We create a new panel to display the game over screen
        GameOverPanel gameOverPanel = new GameOverPanel(score);
        gameOverPanel.setSize(this.getSize());
        this.add(gameOverPanel, Integer.valueOf(6));
    }

    public void displayGameWon(){
        GameWinPanel gameWonPanel = new GameWinPanel();
        gameWonPanel.setSize(this.getSize());
        this.add(gameWonPanel, Integer.valueOf(6));
    }

    public void createGraphics() {
        // Add the panels to this layered pane
        this.add(cellPanel, Integer.valueOf(0));
        this.add(enemyPanel, Integer.valueOf(1));
        this.add(projectilePanel, Integer.valueOf(2));
        this.add(rangePreviewPanel, Integer.valueOf(3));
        this.add(menuPanel, Integer.valueOf(4));

        for (CellsView cellView : cellsViews) {
            cellPanel.add(cellView);
        }
        gameScreen.removeAll();
        gameScreen.add(this);
        this.validate();
    }

    public void showTowerStats(Tower t) {
        menuPanel.showTowerStats(t);
    }

    public void hideTowerStats() {
        menuPanel.hideTowerStats();
    }

    public void createRangePreview(Ellipse2D range) {
        this.rangePreview = range;
    }

    public void removeRangePreview() {
        this.rangePreview = null;
    }

    public void updateRangePreviewPosition(int x, int y) {
        if (rangePreview != null) {
            int centerX = (int) (x - rangePreview.getWidth() / 2);
            int centerY = (int) (y - rangePreview.getHeight() / 2);
            rangePreview.setFrame(centerX, centerY, rangePreview.getWidth(), rangePreview.getHeight());
            this.repaint();
        }
    }

    public void setProjectileController(ArrayList<ProjectileController> projectileController) {
        this.projectiles = projectileController;
    }

    public Ellipse2D getRangePreview() {
        return rangePreview;
    }

    public GameUiPanel getMenuPanel() {
        return menuPanel;
    }

    public JPanel getCellPanel() {
        return this.cellPanel;
    }

    public int getTileSize() {
        return tileSize;
    }

    public ArrayList<CellsView> getCellsViews() {
        return cellsViews;
    }

    public void setCellsViews(ArrayList<CellsView> cellsViews) {
        this.cellsViews = cellsViews;
    }

    public JPanel getEnemyPanel() {
        return enemyPanel;
    }

    public JPanel getProjectilePanel() {
        return projectilePanel;
    }
}