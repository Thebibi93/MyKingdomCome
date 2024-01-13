package model.gameEntities.enemies;

public class Goblin extends Enemy{
    
    public Goblin(int level, int cellSize){
        super(40, cellSize * 2, 20, 0.2, 0, level, 1.1);
    }
}