package controllers;

import javax.swing.JPanel;

import model.Direction;
import model.gameEntities.enemies.Enemy;
import util.geometry.RealCoordinates;
import view.ennemies.EnemyView;

import java.beans.PropertyChangeListener;

public class EnemyController implements PropertyChangeListener {
    private Enemy model;
    private EnemyView view;

    public EnemyController(Enemy enemyModel, JPanel enemyPanel, int cellSize) {
        this.model = enemyModel;
        this.view = EnemyView.getEnemyView(enemyModel, cellSize);
        view.setLocation(enemyModel.getPosition().round().x(), enemyModel.getPosition().round().y());
        view.createHealthBar(cellSize, enemyModel.getHealth(), enemyModel.getArmorLevel(),
                enemyModel.getMagicArmorLevel());
        enemyPanel.add(view);
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("position")){
            RealCoordinates position = (RealCoordinates) evt.getNewValue();
            view.setLocation(position.round().x(), position.round().y());
        }
        if(evt.getPropertyName().equals("direction")){
            view.setDirection((Direction)evt.getNewValue());
        }
        if(evt.getPropertyName().equals("health")){
            view.getHealthBar().updateHealth((int) evt.getNewValue());
        }
        if(evt.getPropertyName().equals("baseDestroyed") || evt.getPropertyName().equals("enemyReward")){
            view.destroy();
        }
    }

    public Enemy getModel() {
        return model;
    }

    public EnemyView getView() {
        return view;
    }
}