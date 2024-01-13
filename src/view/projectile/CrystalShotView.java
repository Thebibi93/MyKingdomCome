package view.projectile;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import util.MakeSprite;
import util.AnimatedSprite;
import java.awt.Graphics2D;
import java.awt.Image;

public class CrystalShotView extends ProjectileView {

    public CrystalShotView(int level, int cellSize) {
        super(create(level, cellSize), explosion(cellSize, level));
    }

    private static BufferedImage create(int level, int cellSize) {
        BufferedImage res = null;
        try {
            switch (level) {
                case 1:
                    res = ImageIO.read(ArrowView.class.getResourceAsStream("/towers/projectile/crystalTower/level1.png"))
                            .getSubimage(0, 0, 10, 10);
                    return resizeImage(res, cellSize);
                case 2:
                    res = ImageIO.read(ArrowView.class.getResourceAsStream("/towers/projectile/crystalTower/level2.png"))
                            .getSubimage(0, 0, 10, 10);
                    return resizeImage(res, cellSize);
                case 3:
                    res = ImageIO.read(ArrowView.class.getResourceAsStream("/towers/projectile/crystalTower/level3.png"))
                            .getSubimage(0, 0, 10, 10);
                    return resizeImage(res, cellSize);
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static AnimatedSprite explosion(int cellSize, int level) {
        AnimatedSprite res = null;
        switch (level) {
            case 1:
                res = MakeSprite.makeSpriteAnimation("/towers/projectile/crystalTower/impact1.png", 5, 64, 64,
                15).resize(cellSize * 8, cellSize * 8);
                return res;
            case 2:
                res = MakeSprite.makeSpriteAnimation("/towers/projectile/crystalTower/impact2.png", 6, 64, 64,
                15).resize(cellSize * 8, cellSize * 8);
                return res;
            case 3:
                res = MakeSprite.makeSpriteAnimation("/towers/projectile/crystalTower/impact3.png", 7, 64, 64,
                15).resize(cellSize * 8, cellSize * 8);
                return res;
            default:
                break;
        }
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
