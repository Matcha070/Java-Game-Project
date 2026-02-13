package Character.Enemy;
import asset.Asset;
import java.awt.*;

public class RegenEnemy extends Enemy{

    private int regenAmount = 5;     // ฟื้นต่อครั้ง
    private int regenDelay = 15;     // ทุกกี่ frame
    private int regenTimer = 0;

    public RegenEnemy() {
        super(100, 1, 10);
    }
    public void draw(Graphics g) {
        DrawEnemy(g);
        DrawHpBar(g);
    }

    private void DrawHpBar(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(
            (int)(x - size / 2) + 6,
            (int)y - size / 2 - 10,
            size - 12,
            5
        );

        g.setColor(Color.RED);
        g.fillRect(
            (int)(x - size / 2) + 6,
            (int)y - size / 2 - 10,
            (size - 12) * hp / maxHp,
            5
        );
    }

    private void DrawEnemy(Graphics g) {
        g.drawImage(
            Asset.SLIME,
            (int) x - size / 2,
            (int) y - size / 2,
            size,
            size,
            null
        );
    }

    @Override
    protected void onUpdate() {
        regenTimer++;

        if (regenTimer >= regenDelay) {
            hp += regenAmount;
            if (hp > maxHp) hp = maxHp;
            regenTimer = 0;
        }
    }
}

