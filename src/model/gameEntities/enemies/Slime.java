package model.gameEntities.enemies;

import model.Damage;
import model.DamageType;

public class Slime extends Enemy{
    int cellSize;
    
    public Slime(int level, int cellSize){
        super(30, cellSize * 2.5, 20, 0.5, 0, level, 1.05);
        this.cellSize = cellSize;
    }

    @Override
     public void takeDamage(Damage damage) {
        if (damage.getType().equals(DamageType.PHYSICAL)) {
            double damageValue = damage.getValue() * (1 - defense);
            health -= damageValue + 1;
            propertyChangeSupport.firePropertyChange("health", null, health);
        } else if (damage.getType().equals(DamageType.MAGICAL)) {
            double damageValue = damage.getValue() * (1 - magicDefense);
            health -= damageValue + 1;
            propertyChangeSupport.firePropertyChange("health", null, health);
        }
        if (health <= 0) {
            isDead = true;
            Goblin goblin = new Goblin(level, cellSize);
            goblin.setSpawn(getCurrentCell());
            goblin.setTarget(target);
            goblin.position = position;
            propertyChangeSupport.firePropertyChange("enemySpawned", null, goblin);
            propertyChangeSupport.firePropertyChange("enemyReward", this, reward);
        }
    }
}
