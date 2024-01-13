package controllers;

import model.Cells;
import model.gameEntities.towers.Tower;
import view.maps.CellsView;
import view.towers.TowerView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

// This class serves as a bridge between the model and the view

public class CellsController implements PropertyChangeListener {
    public Cells model;
    public CellsView view;

    public CellsController(Cells model, CellsView view){
        this.model = model;
        this.view = view;
    }

    // Copy constructor
    public CellsController(CellsController cellsController){
        this.model = new Cells(cellsController.getModel());
        this.view = new CellsView(cellsController.getView());
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("tower")){
            if(evt.getNewValue() != null){
                TowerView towerView = TowerView.createTowerView((Tower) evt.getNewValue(), model.getSize());
                view.buildTower(towerView);
            }
            else{
                view.removeTower();
            }
        }
    }

    public void addTowerPreview(TowerView towerView){
        view.addTowerPreview(towerView);
    }

    public Cells getModel() {
        return model;
    }

    public CellsView getView() {
        return view;
    }
}
