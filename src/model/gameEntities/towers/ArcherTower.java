package model.gameEntities.towers;

import javax.swing.Timer;
import java.awt.geom.Ellipse2D;
import model.Damage;
import model.DamageType;

public class ArcherTower extends Tower {

    public ArcherTower(int cellSize) {
        super(new Damage(DamageType.PHYSICAL, 10),
                new Timer(1000, null), 70, 40, 1, 100, new Ellipse2D.Double(0, 0, 6 * cellSize, 6 * cellSize));
    }

    @Override
    public Tower copy(int cellSize) {
        return new ArcherTower(cellSize);
    }
    
    @Override
    public void upgrade() {
        Timer newTimer;
        switch (getLevel() + 1) {
            case 2:
                setLevel(getLevel() + 1);
                setAttack(getDamage().getValue() + 10);
                getAttackSpeed().stop();
                newTimer = new Timer(getAttackSpeed().getDelay(), null);
                newTimer.setRepeats(false);
                newTimer.start();
                setAttackSpeed(newTimer);
                setSellPrice(getSellPrice() + 10);
                setUpgradePrice(getUpgradePrice() + 30);
                return;
            case 3:
                setLevel(getLevel() + 1);
                setAttack(getDamage().getValue() + 10);
                getAttackSpeed().stop();
                newTimer = new Timer(getAttackSpeed().getDelay() - 100, null);
                newTimer.setRepeats(false);
                newTimer.start();
                setAttackSpeed(newTimer);
                double previousRangeWidth = getRange().getWidth();
                double previousRangeHeight = getRange().getHeight();
                setRange(new Ellipse2D.Double(getRange().getCenterX(), getRange().getCenterY(),
                        previousRangeWidth * 1.2, previousRangeHeight * 1.2));
                setSellPrice(getSellPrice() + 20);
                setUpgradePrice(-1); // Max level
                return;
            default:
                return;
        }
    }
}
