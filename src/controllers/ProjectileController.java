package controllers;

import model.Projectile;
import model.gameEntities.towers.*;
import view.projectile.*;

import java.util.HashMap;

public class ProjectileController {
    private Projectile model;
    private static HashMap<Integer, ProjectileView> projectileViews = new HashMap<>();

    public ProjectileController(Projectile p) {
        this.model = p;
        getView(p);
    }

    public ProjectileView getView(Projectile projectile){
        if(projectile.getShooter() instanceof ArcherTower){
            int level = projectile.getShooter().getLevel();
            int size = projectile.getShooter().getPosition().getSize() / 3;
            if (!projectileViews.containsKey(level)) {
                projectileViews.put(level, new ArrowView(level, size));
            }
            return projectileViews.get(level);
        }
        if(projectile.getShooter() instanceof SlingShotTower){
            int level = projectile.getShooter().getLevel();
            int size = projectile.getShooter().getPosition().getSize() / 3;
            if (!projectileViews.containsKey(level)) {
                projectileViews.put(level, new SlingShotView(level, size));
            }
            return projectileViews.get(level);
        }
        if(projectile.getShooter() instanceof CrystalTower){
            int level = projectile.getShooter().getLevel();
            int size = projectile.getShooter().getPosition().getSize() / 3;
            if (!projectileViews.containsKey(level)) {
                projectileViews.put(level, new CrystalShotView(level, size));
            }
            return projectileViews.get(level);
        }
        if(projectile.getShooter() instanceof MagicCatapultTower){
            int level = projectile.getShooter().getLevel();
            int size = projectile.getShooter().getPosition().getSize() / 3;
            if (!projectileViews.containsKey(level)) {
                projectileViews.put(level, new CatapultShotView(level, size));
            }
            return projectileViews.get(level);
        }
        return null;
    }

    public ProjectileView getView() {
        return getView(model);
    }

    public Projectile getModel() {
        return model;
    }
}
