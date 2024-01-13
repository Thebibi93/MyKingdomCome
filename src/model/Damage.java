package model;

import java.awt.geom.Ellipse2D;

public class Damage {

    private DamageType type;
    private int value;
    public enum DamageRange {
        SINGLE,
        SPLASH,
    }
    private DamageRange range;
    private Ellipse2D splashRange;

    public Damage(DamageType type, int value) {
        this.type = type;
        this.value = value;
        this.range = DamageRange.SINGLE;
    }

    public Damage(DamageType type, int value, Ellipse2D range) {
        this.type = type;
        this.value = value;
        this.range = DamageRange.SPLASH;
        this.splashRange = range;
    }

    public boolean isSplash(){
        return range == DamageRange.SPLASH;
    }

    public Ellipse2D getSplashRange() {
        return splashRange;
    }

    public Damage clone(){
        return new Damage(type, value);
    }

    public DamageType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }
}
