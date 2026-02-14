package Character.Enemy;

import asset.Asset;
import java.awt.*;

public class SpeedyEnemy extends Enemy {

    public SpeedyEnemy() {
        super(15, 5.0, 10);
    }

    public void draw(Graphics g) {
        DrawEnemy(g);
        super.DrawHpBar(g);
    }

    private void DrawEnemy(Graphics g) {
        g.drawImage(
                Asset.SLIME,
                (int) x - size / 2,
                (int) y - size / 2,
                size,
                size,
                null);
    }
}
