package view.projectile;
import java.awt.image.BufferedImage;
import util.AnimatedSprite;

// This class is used to draw the projectiles on the screen which are fired by the towers
public abstract class ProjectileView {
    private BufferedImage sprite;
    private AnimatedSprite explosion;

    public ProjectileView(BufferedImage sprite, AnimatedSprite explosion) {
        this.sprite = sprite;
        this.explosion = explosion;
    }

    public BufferedImage getImage() {
        return sprite;
    }

    public AnimatedSprite getExplosion() {
        return explosion;
    }
}