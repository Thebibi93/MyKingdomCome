package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MakeSprite {
 
    /** Slice the spritesheet into individual sprites */
    public static BufferedImage getSprite(BufferedImage spriteSheet, int xGrid, int yGrid, int spriteWidth, int spriteHeight) {
        return spriteSheet.getSubimage(xGrid * spriteWidth, yGrid * spriteHeight, spriteWidth, spriteHeight);
    }

    public static AnimatedSprite makeSpriteAnimation(String path, int frames, int spriteWidth, int spriteHeight, int frameDelay) {
        BufferedImage spriteSheet = null;
        try {
            spriteSheet = ImageIO.read(MakeSprite.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnimatedSprite spriteAnimation = new AnimatedSprite();
        for (int i = 0; i < frames; i++) {
            spriteAnimation.addFrame(getSprite(spriteSheet, i, 0, spriteWidth, spriteHeight), frameDelay);
        }
        return spriteAnimation;
    }
}

