
package util;

import java.awt.geom.Rectangle2D;

public class Hurtbox extends Rectangle2D.Double {

    public Hurtbox(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public boolean isColliding(Hurtbox other) {
        return this.intersects(other);
    }

    public boolean isColliding(Rectangle2D.Double other) {
        return this.intersects(other);
    }

    public void move(double x, double y) {
        super.setRect(x, y, width, height);
    }
}
