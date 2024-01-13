package util.geometry;

public class IntCoordinates{
    private int x;
    private int y;

    public IntCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }
    
    public RealCoordinates toRealCoordinates(double scale) {
        return new RealCoordinates(x * scale, y * scale);
    }
}
