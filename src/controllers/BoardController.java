package controllers;

import model.Board;
import model.Cells;
import model.Projectile;
import model.gameEntities.enemies.Enemy;
import model.gameEntities.towers.*;
import util.AnimatedSprite;
import view.main.Game;
import view.maps.BoardView;
import view.maps.CellsView;
import view.maps.MapBuilder;
import view.towers.*;
import view.ui.TowerIcon;
import view.ui.TowersIcons;
import inputs.BoardMouse;
import inputs.KeyBoard;
import inputs.Mouse;
import inputs.BoardKeyboard;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class BoardController implements PropertyChangeListener {

    private Board model;
    private BoardView view;
    private BoardMouse mouse;
    private BoardKeyboard keyboard;
    private boolean isFinished = false;
    private ArrayList<CellsController> cellsController = new ArrayList<CellsController>();
    private ArrayList<EnemyController> currentEnemies = new ArrayList<EnemyController>();
    private ArrayList<TowerController> towerController = new ArrayList<TowerController>();
    private ArrayList<ProjectileController> projectileController = new ArrayList<ProjectileController>();

    public BoardController(int level, int difficulty) {

        this.model = new Board(level, difficulty);
        this.view = new BoardView(Game.INSTANCE.getGameScreen(), level);

        // We create the cells
        createCells();

        view.setProjectileController(projectileController);

        view.createGraphics();
        model.init();

        this.keyboard = new BoardKeyboard(createTowersIcons(), this);
        this.mouse = new BoardMouse(this, keyboard);

        configureListeners();
        // We want to set the game ui with the correct values
        view.getMenuPanel().addUiInfo(model.getLife(), model.getMoney(), model.getTotalWaves(), difficulty);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("enemySpawned")) {
            EnemyController enemyController = new EnemyController((Enemy) evt.getNewValue(), view.getProjectilePanel(),
                    view.getTileSize());
            System.out.println("Enemy spawned");
            currentEnemies.add(enemyController);
        }
        if (evt.getPropertyName().equals("enemyReward")) {
            Enemy enemy = (Enemy) evt.getOldValue();
            currentEnemies.removeIf(ec -> ec.getModel().equals(enemy));
            view.getMenuPanel().setMoney(model.getMoney());
        }
        if (evt.getPropertyName().equals("baseDestroyed")) {
            Enemy enemy = (Enemy) evt.getOldValue();
            currentEnemies.removeIf(ec -> ec.getModel().equals(enemy));
            view.getMenuPanel().setLife(model.getLife());
        }
        if (evt.getPropertyName().equals("projectileCreated")) {
            ProjectileController projectileController = new ProjectileController((Projectile) evt.getNewValue());
            this.projectileController.add(projectileController);
        }
        if (evt.getPropertyName().equals("projectileDestroyed")) {
            System.out.println("Projectile destroyed in boardController");
            Projectile projectile = (Projectile) evt.getOldValue();
            ProjectileController projectileController = this.projectileController.stream()
                    .filter(pc -> pc.getModel().equals(projectile)).findFirst().orElse(null);
            AnimatedSprite explosion = new AnimatedSprite(projectileController.getView().getExplosion());
            explosion.setOnFinished(() -> {
                view.removeExplosion(explosion);
            });
            view.addExplosion(explosion, projectileController.getModel().getPosition());
            this.projectileController.remove(projectileController);
        }
        if(evt.getPropertyName().equals("waveStarted")){
            System.out.println("Wave started");
            view.getMenuPanel().updateStateWave(model.getCurrentWaveNumber());
        }
        if(evt.getPropertyName().equals("gameLost")){
            isFinished = true;
            // We remove the mouse and keyboard listeners
            Game.INSTANCE.getGameScreen().removeMouseListener(mouse);
            Game.INSTANCE.getGameScreen().removeMouseMotionListener(mouse);
            Game.INSTANCE.getGameScreen().removeKeyListener(keyboard);
            // Then we display the game over screen
            view.displayGameOver(model.getCurrentWaveNumber());
        }
        if(evt.getPropertyName().equals("gameWon")){
            isFinished = true;
            // We remove the mouse and keyboard listeners
            Game.INSTANCE.getGameScreen().removeMouseListener(mouse);
            Game.INSTANCE.getGameScreen().removeMouseMotionListener(mouse);
            Game.INSTANCE.getGameScreen().removeKeyListener(keyboard);
            // Then we display the game over screen
            view.displayGameWon();
        }
    }

    private void createCells() {
        cellsController = MapBuilder.buildMap(model.getLevel(), view.getCellPanel(), view.getTileSize());
        ArrayList<Cells> cells = new ArrayList<Cells>();
        for (CellsController cellController : cellsController) {
            cells.add(cellController.getModel());
        }
        model.setCells(cells);
        ArrayList<CellsView> cellsViews = new ArrayList<CellsView>();
        for (CellsController cellController : cellsController) {
            cellsViews.add(cellController.getView());
        }
        view.setCellsViews(cellsViews);
    }

    private ArrayList<TowerController> createTowersIcons() {
        ArrayList<TowerController> selectedTowers = new ArrayList<>();
        selectedTowers.add(
                new TowerController(new ArcherTower(view.getTileSize()), new ArcherTowerView(1, view.getTileSize())));
        selectedTowers.add(
                new TowerController(new SlingShotTower(view.getTileSize()), new SlingShotTowerView(1, view.getTileSize())));
        selectedTowers.add(
                new TowerController(new CrystalTower(view.getTileSize()), new CrystalTowerView(1, view.getTileSize())));
        selectedTowers.add(
                new TowerController(new MagicCatapultTower(view.getTileSize()), new MagicCatapultTowerView(1, view.getTileSize())));

        TowerIcon towerIcon1 = new TowerIcon(selectedTowers.get(0), 0);
        TowerIcon towerIcon2 = new TowerIcon(selectedTowers.get(1), 1);
        TowerIcon towerIcon3 = new TowerIcon(selectedTowers.get(2), 2);
        TowerIcon towerIcon4 = new TowerIcon(selectedTowers.get(3), 3);

        TowersIcons towersIcons = new TowersIcons(towerIcon1, towerIcon2, towerIcon3, towerIcon4);
        view.getMenuPanel().addTowersIcons(towersIcons);

        return selectedTowers;
    }

    private void configureListeners() {
        for (KeyListener listener : Game.INSTANCE.getKeyListeners()) {
            if (listener == KeyBoard.INSTANCE) {
                Game.INSTANCE.removeKeyListener(listener);
            }
        }

        model.addPropertyChangeListener(this);
        // We remove the old mouse and keyboard listeners
        Game.INSTANCE.getGameScreen().removeMouseListener(Mouse.INSTANCE);

        Game.INSTANCE.getGameScreen().addMouseListener(mouse);
        Game.INSTANCE.getGameScreen().addMouseMotionListener(mouse);
        Game.INSTANCE.getGameScreen().addKeyListener(keyboard);
        Game.INSTANCE.getGameScreen().requestFocus();
    }

    public void updateLogic(long deltaTime) {
        // We want to call this method every ups (60 times per second)
        if(isFinished)
            return;
        model.update(deltaTime);
    }

    public CellsController getCellAt(int x, int y) {
        for (CellsController cell : cellsController) {
            if (x >= cell.getModel().getX() && x <= cell.getModel().getX() + cell.getModel().getSize() &&
                    y >= cell.getModel().getY() && y <= cell.getModel().getY() + cell.getModel().getSize()) {
                return cell;
            }
        }
        return null;
    }

    public void buyTower(TowerController tower, CellsController cell) {
        Tower copy = (Tower) tower.getModel().copy(model.getCellSize());
        // We buy the tower
        if (model.buyTower(copy, cell.getModel())) {
            System.out.println("Tower bought");
        } else {
            System.out.println("Not enough money");
            return;
        }
        TowerView towerView = cell.getView().getTowerView();
        TowerController newTower = new TowerController(copy, towerView);
        // We add the tower to the list of towers
        towerController.add(newTower);
        // We update the view
        view.getMenuPanel().setMoney(model.getMoney());
        view.showTowerStats(copy);
    }

    public void sellTower(CellsController cell) {
        if (cell.getModel().getTower() == null)
            return;
        // Update towerController list by removing the corresponding TowerController
        towerController.removeIf(tc -> tc.getModel().equals(cell.getModel().getTower()));
        // We sell the tower
        model.sellTower(cell.getModel());
        // We update the view
        view.getMenuPanel().setMoney(model.getMoney());
        view.hideTowerStats();
    }

    public void upgradeTower(CellsController cell) {
        if (cell.getModel().getTower() == null)
            return;
        // We upgrade the tower
        if(model.upgradeTower(cell.getModel().getTower())){
            System.out.println("Tower upgraded");
        } else {
            System.out.println("Not enough money");
            return;
        }
        towerController.stream().filter(tc -> tc.getModel().getId() == cell.getModel().getTower().getId()).findFirst()
                .orElse(null).upgrade();
        // We update the view
        view.getMenuPanel().setMoney(model.getMoney());
        view.showTowerStats(cell.getModel().getTower());
        view.createRangePreview(cell.getModel().getTower().getRange());
        view.updateRangePreviewPosition(cell.getModel().getCenter().x(), cell.getModel().getCenter().y());
    }

    public Board getModel() {
        return model;
    }

    public BoardView getView() {
        return view;
    }

    public ArrayList<CellsController> getCellsController() {
        return cellsController;
    }
}