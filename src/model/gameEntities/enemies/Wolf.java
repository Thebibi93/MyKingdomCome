package model.gameEntities.enemies;

public class Wolf extends Enemy{
    
    public Wolf(int level, int cellSize){
        super(40, cellSize * 4, 20, 0.2, 0.3, level, 1.1);
    }
}
