package GameObject.Character.Enemy;

import GameController.Animation;
import asset.Asset;
import asset.EnemySheet;
import java.awt.Graphics;

public class TankyEnemy extends Enemy {

    private EnemySheet enemySheet;

    private Animation enemyUp;
    private Animation enemyDown;
    private Animation enemyWalk_left;
    private Animation enemyWalk_right;

    private Animation currentAnim;

    private int drawSize = 96;

    public TankyEnemy() {
        super(80, 0.6, 40);

        enemySheet = new EnemySheet(Asset.VAMPIRE, 64, 64);

        enemyDown = enemySheet.createAnim(0, 8, true);
        enemyUp = enemySheet.createAnim(1, 8, true);
        enemyWalk_left = enemySheet.createAnim(2, 8, true);
        enemyWalk_right = enemySheet.createAnim(3, 8, true);

        currentAnim = enemyWalk_right;

    }

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
