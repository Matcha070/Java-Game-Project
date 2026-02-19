package Character.Enemy;

import GameController.Animation;
import asset.Asset;
import asset.EnemySheet;
import java.awt.*;

public class SpeedyEnemy extends Enemy {

    private EnemySheet enemySheet;

    private Animation enemyUp;
    private Animation enemyDown;
    private Animation enemyWalk_left;
    private Animation enemyWalk_right;

    private Animation currentAnim;
    private int drawSize = 96;

    public SpeedyEnemy() {
        super(15, 5.0, 10);

        enemySheet = new EnemySheet(Asset.Tree2, 64, 64);

        enemyDown = enemySheet.createAnim(0, 12, true);
        enemyUp = enemySheet.createAnim(1, 12, true);
        enemyWalk_left = enemySheet.createAnim(2, 12, true);
        enemyWalk_right = enemySheet.createAnim(3, 12, true);
        currentAnim = enemyWalk_right;
    }

    public void draw(Graphics g) {
        DrawEnemy((Graphics2D) g);
        super.DrawHpBar(g);
    }

    private void DrawEnemy(Graphics2D g) {
        if (!currentAnim.isRight) {
            g.drawImage(currentAnim.getCurrentFrame(), (int) x - size - 10, (int) y - size - 10, drawSize, drawSize,
                    null);
        } else {
            g.drawImage(currentAnim.getCurrentFrame(), (int) x - size - 12, (int) y - size - 10, -drawSize, drawSize,
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
