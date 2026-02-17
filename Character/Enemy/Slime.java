package Character.Enemy;

import asset.Asset;
import java.awt.*;

public class Slime extends Enemy {

    public Slime() {
        super(30, 2.0, 5);
    }

    @Override
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
