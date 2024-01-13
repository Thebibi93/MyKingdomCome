package view.towers;

import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import util.AnimatedSprite;
import util.MakeSprite;

public class CrystalTowerView extends TowerView {
    
    public CrystalTowerView(int level, int cellSize) {
        super(towerSprite(level, cellSize), weaponSprite(level, cellSize));
        this.level = level;
        this.cellSize = cellSize;
    }

    public void upgrade() {
        System.out.println("Upgrade Slingshot Tower");
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
                            .read(ArcherTowerView.class.getResourceAsStream("/towers/base/crystalTower.png"))
                            .getSubimage(0, 0, 64, 192);
                    return resizeImage(base, cellSize);
                case 2:
                    base = ImageIO
                            .read(ArcherTowerView.class.getResourceAsStream("/towers/base/crystalTower.png"))
                            .getSubimage(64, 0, 64, 192);
                    return resizeImage(base, cellSize);
                case 3:
                    base = ImageIO
                            .read(ArcherTowerView.class.getResourceAsStream("/towers/base/crystalTower.png"))
                            .getSubimage(128, 0, 64, 192);
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
                return MakeSprite.makeSpriteAnimation("/towers/weapon/crystalTower/level1.png", 16, 48, 48, 8)
                        .resize(cellSize, cellSize);
            case 2:
                return MakeSprite.makeSpriteAnimation("/towers/weapon/crystalTower/level2.png", 17, 48, 64, 8)
                        .resize((int) (cellSize * 0.9),(int) (cellSize * 0.9));
            case 3:
                return MakeSprite.makeSpriteAnimation("/towers/weapon/crystalTower/level3.png", 19, 48, 64, 8)
                        .resize(cellSize, cellSize);
            default:
                break;
        }
        return null;
    }

    @Override
    public void attackAnimation(Graphics g) {
        // We want to play the animation only once when the tower attacks
        if (weapon != null) {
            GraphicsConfiguration gc = getGraphicsConfiguration();
            BufferedImage image = gc.createCompatibleImage(weapon.getWidth(), weapon.getHeight(), Transparency.TRANSLUCENT);
            
            Graphics2D g2d = (Graphics2D) image.createGraphics(); // Create a copy of the graphics instance
            int centerX = sprite.getWidth() / 2;
            int centerY = sprite.getHeight() / 2;
    
            int weaponX = centerX - weapon.getWidth() / 2;
            int weaponY = centerY - weapon.getHeight() / 2;
    
            g2d.drawImage(weapon.displayNextFrame(), weaponX, weaponY, null);
    
            g2d.dispose(); // Dispose the graphics copy

            g.drawImage(image, 0, 0, null);
        }
    }

    @Override
    public void drawWeapon(Graphics g) {
        if (weapon != null) {

            GraphicsConfiguration gc = getGraphicsConfiguration();
            BufferedImage image = gc.createCompatibleImage(weapon.getWidth(), weapon.getHeight(), Transparency.TRANSLUCENT);
             
            Graphics2D g2d = (Graphics2D) image.createGraphics(); // Create a copy of the graphics instance


            int weaponX = sprite.getWidth() / 2 - weapon.getWidth() / 2;
            int weaponY = sprite.getHeight() / 2 - weapon.getHeight() / 2;

            // Draw the weapon into the accelerated image
            g2d.drawImage(weapon.displayCurrentFrame(), weaponX, weaponY, null);
            g2d.dispose(); // Dispose the graphics copy
    
            g.drawImage(image, 0, 0, null);
        }
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
