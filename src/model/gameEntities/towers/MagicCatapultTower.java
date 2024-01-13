package model.gameEntities.towers;

import javax.swing.Timer;
import java.awt.geom.Ellipse2D;
import model.Damage;
import model.DamageType;

public class MagicCatapultTower extends Tower {

    public MagicCatapultTower(int cellSize) {
        super(new Damage(DamageType.MAGICAL, 15),
                new Timer(750, null), 120, 60, 1, 140, new Ellipse2D.Double(0, 0, 7 * cellSize, 6 * cellSize));
    }

    @Override
    public Tower copy(int cellSize) {
        return new MagicCatapultTower(cellSize);
    }
    
    @Override
    public void upgrade() {
        Timer newTimer;
        switch (getLevel() + 1) {
            case 2:
                setLevel(getLevel() + 1);
                setAttack(getDamage().getValue() + 15);
                getAttackSpeed().stop();
                newTimer = new Timer(getAttackSpeed().getDelay(), null);
                newTimer.setRepeats(false);
                newTimer.start();
                setAttackSpeed(newTimer);
                setSellPrice(getSellPrice() + 30);
                setUpgradePrice(getUpgradePrice() + 50);
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
                setSellPrice(getSellPrice() + 40);
                setUpgradePrice(-1); // Max level
                return;
            default:
                return;
        }
    }
}