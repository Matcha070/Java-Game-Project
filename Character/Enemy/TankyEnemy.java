package Character.Enemy;

import asset.Asset;
import java.awt.*;

public class TankyEnemy extends Enemy {

    public TankyEnemy() {
        super(100, 1.5, 10);
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
