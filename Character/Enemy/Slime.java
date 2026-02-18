package Character.Enemy;

import asset.Asset;
import asset.EnemySheet;

import java.awt.*;

import GameController.Animation;

public class Slime extends Enemy {

    private EnemySheet enemySheet;

    private Animation enemyUp;
    private Animation enemyDown;
    private Animation enemyWalk_left;
    private Animation enemyWalk_right;

    private Animation currentAnim;

    private int drawSize = 96;

    public Slime() {
        super(30, 2.0, 5);

        enemySheet = new EnemySheet(Asset.SLIME, 64, 64);

        enemyDown = enemySheet.createAnim(0, 4, true);
        enemyUp = enemySheet.createAnim(1, 4, true);
        enemyWalk_left = enemySheet.createAnim(2, 4, true);
        enemyWalk_right = enemySheet.createAnim(3, 4, true);

        currentAnim = enemyWalk_right;

    }

    @Override
    public void draw(Graphics g) {
        DrawEnemy(g);
        super.DrawHpBar(g);
    }

    private void DrawEnemy(Graphics g) {
        if (!currentAnim.isRight) {
            g.drawImage(currentAnim.getCurrentFrame(), (int) x - size - 10, (int) y - size - 10, drawSize, drawSize,
                    null);
        } else {
            g.drawImage(currentAnim.getCurrentFrame(), (int) x - size - 15, (int) y - size - 10, -drawSize, drawSize,
                    null);
        }
    }

    @Override
    protected void onUpdate() {
        regenTimer++;

        if (regenTimer >= regenDelay) {
            hp += regenAmount;
            if (hp > maxHp)
                hp = maxHp;
            regenTimer = 0;
        }

        if (dirX == 1) {
            currentAnim = enemyWalk_right;
        } else if (dirX == -1) {
            currentAnim = enemyWalk_left;
        } else if (dirY == 1) {
            currentAnim = enemyDown;
        } else if (dirY == -1) {
            currentAnim = enemyUp;
        }
        currentAnim.update();
    }
}
