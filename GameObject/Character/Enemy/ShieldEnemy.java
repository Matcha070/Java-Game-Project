package GameObject.Character.Enemy;

import GameController.Animation;
import asset.Asset;
import asset.EnemySheet;
import java.awt.*;

public class ShieldEnemy extends Enemy {

    private EnemySheet enemySheet;

    private Animation enemyUp;
    private Animation enemyDown;
    private Animation enemyWalk_left;
    private Animation enemyWalk_right;

    private Animation currentAnim;
    private int drawSize = 96;

    public ShieldEnemy() {
        super(100, 1.2, 30);   

        this.maxArmorHP = 60;
        this.armorHP = 60;

        enemySheet = new EnemySheet(Asset.Tree2, 64, 64);

        enemyDown = enemySheet.createAnim(0, 12, true);
        enemyUp = enemySheet.createAnim(1, 12, true);
        enemyWalk_left = enemySheet.createAnim(2, 12, true);
        enemyWalk_right = enemySheet.createAnim(3, 12, true);

        currentAnim = enemyWalk_right;
    }

    @Override
    public void draw(Graphics g) {
        DrawEnemy((Graphics2D) g);
        DrawArmorHpBar(g);
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

    private void DrawArmorHpBar(Graphics g) {

        int barWidth = 50;
        int barHeight = 6;

        int barX = (int) x - barWidth / 2;
        int barY = (int) y - size / 2 - 15;

        int totalMax = maxHp + maxArmorHP;

        // พื้นหลัง
        g.setColor(Color.DARK_GRAY);
        g.fillRect(barX, barY, barWidth, barHeight);

        int currentX = barX;

        //วาดเลือดก่อน
        if (hp > 0) {
            int hpWidth = barWidth * hp / totalMax;

            g.setColor(Color.RED);
            g.fillRect(currentX, barY, hpWidth, barHeight);

            currentX += hpWidth;
        }

        // วาดเกราะต่อด้านหลัง
        if (armorHP > 0) {
            int armorWidth = barWidth * armorHP / totalMax;

            g.setColor(Color.CYAN);
            g.fillRect(currentX, barY, armorWidth, barHeight);
        }
    }

    private void DrawArmorBar(Graphics g) {
        if (armorHP > 0) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect((int) (x - 20), (int) (y - 35), 40, 5);

            g.setColor(Color.CYAN);
            g.fillRect((int) (x - 20), (int) (y - 35),
                    40 * armorHP / maxArmorHP, 5);
        }
    }

    @Override
    protected void onUpdate() {

        // 🎬 animation
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