package model.gameEntities.towers;

import model.Damage;
import model.DamageType;
import javax.swing.Timer;
import java.awt.geom.Ellipse2D;

public class CrystalTower extends Tower {
    
    public CrystalTower(int size) {
        super(new Damage(DamageType.MAGICAL, 50, new Ellipse2D.Double(0, 0, 2 * size, 2 * size)),
                new Timer(4000, null), 300, 100, 1, 350, new Ellipse2D.Double(0, 0, 9 * size, 9 * size));
    }

    @Override
    public Tower copy(int size) {
        return new CrystalTower(size);
    }
    
    @Override
    public void upgrade() {
        Timer newTimer;
        switch (getLevel() + 1) {
            case 2:
                setLevel(getLevel() + 1);
                setAttack(getDamage().getValue() + 60);
                getAttackSpeed().stop();
                newTimer = new Timer(getAttackSpeed().getDelay(), null);
                newTimer.setRepeats(false);
                newTimer.start();
                setAttackSpeed(newTimer);
                setSellPrice(getSellPrice() + 20);
                setUpgradePrice(getUpgradePrice() + 50);
                return;
            case 3:
                setLevel(getLevel() + 1);
                setAttack(getDamage().getValue() + 80);
                getAttackSpeed().stop();
                newTimer = new Timer(getAttackSpeed().getDelay(), null);
                newTimer.setRepeats(false);
                newTimer.start();
                setAttackSpeed(newTimer);
                setSellPrice(getSellPrice() + 30);
                setUpgradePrice(-1); // Max level
                return;
            default:
                return;
        }
    }
}
