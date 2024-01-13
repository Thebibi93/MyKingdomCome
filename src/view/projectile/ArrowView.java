package view.projectile;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import util.MakeSprite;
import util.AnimatedSprite;
import java.awt.Graphics2D;
import java.awt.Image;

public class ArrowView extends ProjectileView {

    public ArrowView(int level, int cellSize) {
        super(create(level, cellSize), explosion(cellSize));
    }

    private static BufferedImage create(int level, int cellSize) {
        BufferedImage res = null;
        try {
            switch (level) {
                case 1:
                    res = ImageIO.read(ArrowView.class.getResourceAsStream("/towers/projectile/archerTower/level1.png"))
                            .getSubimage(0, 0, 8, 40);
                    return resizeImage(res, cellSize);
                case 2:
                    res = ImageIO.read(ArrowView.class.getResourceAsStream("/towers/projectile/archerTower/level2.png"))
                            .getSubimage(0, 0, 15, 40);
                    return resizeImage(res, cellSize);
                case 3:
                    res = ImageIO.read(ArrowView.class.getResourceAsStream("/towers/projectile/archerTower/level3.png"))
                            .getSubimage(0, 0, 22, 40);
                    return resizeImage(res, cellSize);
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static AnimatedSprite explosion(int cellSize) {
        AnimatedSprite res = MakeSprite.makeSpriteAnimation("/towers/projectile/archerTower/impact.png", 3, 64, 64,
                15).resize(cellSize * 5, cellSize * 5);
        return res;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int cellSize) {
        Image tmp = originalImage.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(cellSize, cellSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }
}
