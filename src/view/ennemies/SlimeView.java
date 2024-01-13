package view.ennemies;

import util.AnimatedSprite;
import util.MakeSprite;

// This class is used to display the ennemy Bee
public class SlimeView extends EnemyView{
    private static final AnimatedSprite D_WALK = MakeSprite.makeSpriteAnimation("/ennemies/slime/D_Walk.png", 6, 48, 48, 10);
    private static final AnimatedSprite L_WALK = MakeSprite.makeSpriteAnimation("/ennemies/slime/S_Walk.png", 6, 48, 48,10);
    private static final AnimatedSprite U_WALK = MakeSprite.makeSpriteAnimation("/ennemies/slime/U_Walk.png", 6, 48, 48, 10);
    private static final AnimatedSprite R_WALK;

    static {
        AnimatedSprite temp = MakeSprite.makeSpriteAnimation("/ennemies/slime/S_Walk.png", 6, 48, 48, 10);
        temp.rotate(90);
        R_WALK = temp;
    }

    public SlimeView(int cellSize){
        super(D_WALK, R_WALK, U_WALK, L_WALK, cellSize);
    }
}
