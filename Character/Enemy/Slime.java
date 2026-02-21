package Character.Enemy;

import GameController.Animation;
import asset.Asset;
import asset.EnemySheet;
import java.awt.*;

public class Slime extends Enemy {

    private EnemySheet enemySheet;

    private Animation enemyUp;
    private Animation enemyDown;
    private Animation enemyWalk_left;
    private Animation enemyWalk_right;
    private boolean isChild = false;

    private Animation currentAnim;

    private int drawSize = 96;

    public Slime() {
        this(false);
    }

    public Slime(boolean isChild) {
        super(isChild ? 15 : 30, 2.5, isChild ? 2 : 5);

        this.isChild = isChild;
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

    private void spawnChildren() {

        Slime child1 = new Slime(true);
        Slime child2 = new Slime(true);

        child1.laneOffset = -12 - Math.random() * 5;
        child2.laneOffset = 12 + Math.random() * 5;

        child1.x = this.x ;
        child1.y = this.y ;

        child2.x = this.x ;
        child2.y = this.y ;

        child1.targetIndex = this.targetIndex;
        child2.targetIndex = this.targetIndex;

        childrenToSpawn.add(child1);
        childrenToSpawn.add(child2);
    }

    @Override
    protected void onDeath() {
        if (!isChild) {
            spawnChildren();
        }
    }
}
