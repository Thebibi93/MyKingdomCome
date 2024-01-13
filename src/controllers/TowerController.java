package controllers;

import model.Projectile;
import model.gameEntities.enemies.Enemy;
import model.gameEntities.towers.Tower;
import view.towers.TowerView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class TowerController implements PropertyChangeListener {
    private Tower model;
    private TowerView view;

    public TowerController(Tower model, TowerView view) {
        this.model = model;
        this.view = view;

        model.addPropertyChangeListener(this);
    }

    public void upgrade() {
        view.upgrade();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("attack")) {
            Enemy enemy = (Enemy) evt.getNewValue();
            Projectile projectile = (Projectile) evt.getOldValue();
            view.update(enemy, projectile);
            System.out.println("The new value of attack is " + view.isAttacking());
        }
    }

    public Tower getModel() {
        return model;
    }

    public TowerView getView() {
        return view;
    }
}
