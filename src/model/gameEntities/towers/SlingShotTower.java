package model.gameEntities.towers;

import model.Damage;
import model.DamageType;
import javax.swing.Timer;
import java.awt.geom.Ellipse2D;

public class SlingShotTower extends Tower {
    
    public SlingShotTower(int size) {
        super(new Damage(DamageType.PHYSICAL, 30, new Ellipse2D.Double(0, 0, 2 * size, 2 * size)),
                new Timer(2000, null), 120, 40, 1, 140, new Ellipse2D.Double(0, 0, 4 * size, 4 * size));
    }

    @Override
    public Tower copy(int size) {
        return new SlingShotTower(size);
    }

    @Override
    public void upgrade() {
        Timer newTimer;
        switch (getLevel() + 1) {
            case 2:
                setLevel(getLevel() + 1);
                setAttack(getDamage().getValue() + 40);
                getAttackSpeed().stop();
                newTimer = new Timer(getAttackSpeed().getDelay(), null);
                newTimer.setRepeats(false);
                newTimer.start();
                setAttackSpeed(newTimer);
                setSellPrice(getSellPrice() + 20);
                setUpgradePrice(getUpgradePrice() + 40);
                return;
            case 3:
                setLevel(getLevel() + 1);
                setAttack(getDamage().getValue() + 50);
                getAttackSpeed().stop();
                newTimer = new Timer(getAttackSpeed().getDelay(), null);
                newTimer.setRepeats(false);
                newTimer.start();
                setAttackSpeed(newTimer);
                double previousRangeWidth = getRange().getWidth();
                double previousRangeHeight = getRange().getHeight();
                setRange(new Ellipse2D.Double(getRange().getCenterX(), getRange().getCenterY(),
                        previousRangeWidth * 1.5, previousRangeHeight * 1.5));
                setSellPrice(getSellPrice() + 30);
                setUpgradePrice(-1); // Max level
                return;
            default:
                return;
        }
    }
}
