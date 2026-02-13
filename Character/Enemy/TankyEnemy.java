package Character.Enemy;
import asset.Asset;
import java.awt.*;

public class TankyEnemy extends Enemy{

    public TankyEnemy() {
        super(100, 1.5, 10);
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
}

