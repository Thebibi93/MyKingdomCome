package model.gameEntities.enemies;

public class Bee extends Enemy{
    
    public Bee(int level, int cellSize){
        super(30, cellSize * 2.5, 15, 0, 0.5, level, 1.05);
    }
}
