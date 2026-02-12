package Character.Enemy;
import java.awt.*;

import asset.Asset;


public class Slime extends Enemy {

    public Slime() {
        super(30, 2.0); // hp, speed เดิม
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(
            Asset.SLIME,
            (int) x - size / 2,
            (int) y - size / 2,
            size,
            size,
            null
        );

        // HP bar (ของเดิม)
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
}
