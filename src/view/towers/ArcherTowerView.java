package view.towers;

import util.MakeSprite;
import util.AnimatedSprite;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.Image;

public class ArcherTowerView extends TowerView {

    public ArcherTowerView(int level, int cellSize) {
        super(towerSprite(level, cellSize), weaponSprite(level, cellSize));
        this.level = level;
        this.cellSize = cellSize;
    }

    public void upgrade() {
        System.out.println("Upgrade Archer Tower");
        this.level++;
        this.weapon = weaponSprite(level, cellSize);
        this.sprite = towerSprite(level, cellSize);
        weapon.setOnFinished(() -> {
            state = State.IDLE;
        });
    }

    private static BufferedImage towerSprite(int level, int cellSize) {
        BufferedImage base = null;
        try {
            switch (level) {
                case 1:
                    base = ImageIO
                            .read(ArcherTowerView.class.getResourceAsStream("/towers/base/archerTower.png")).getSubimage(0, 0, 64, 128);
                    return resizeImage(base, cellSize);
                case 2:
                    base = ImageIO
                            .read(ArcherTowerView.class.getResourceAsStream("/towers/base/archerTower.png")).getSubimage(64, 0, 64, 128);
                    return resizeImage(base, cellSize);
                case 3:
                    base = ImageIO
                            .read(ArcherTowerView.class.getResourceAsStream("/towers/base/archerTower.png")).getSubimage(128, 0, 64, 128);
                    return resizeImage(base, cellSize);
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base;
    }

    private static AnimatedSprite weaponSprite(int level, int cellSize) {
        switch (level) {
            case 1:
                return MakeSprite.makeSpriteAnimation("/towers/weapon/archerTower/level1.png", 5, 96, 96, 10)
                        .resize(cellSize, cellSize);
            case 2:
                return MakeSprite.makeSpriteAnimation("/towers/weapon/archerTower/level2.png", 5, 96, 96, 10)
                        .resize(cellSize, cellSize);
            case 3:
                return MakeSprite.makeSpriteAnimation("/towers/weapon/archerTower/level3.png", 5, 96, 96, 10)
                        .resize(cellSize, cellSize);
            default:
                break;
        }
        return null;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int cellSize) {
        Image tmp = originalImage.getScaledInstance(cellSize, cellSize, Image.SCALE_AREA_AVERAGING);
        BufferedImage resizedImage = new BufferedImage(cellSize, cellSize, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }
}
