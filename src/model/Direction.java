package model;

public enum Direction {
    NORTH, SOUTH, EAST, WEST, NONE;

    public static Direction getDirection(double x, double y){
        if(x > 0){
            return EAST;
        } else if(x < 0){
            return WEST;
        } else if(y > 0){
            return SOUTH;
        } else if(y < 0){
            return NORTH;
        } else {
            return NONE;
        }
    }
}
