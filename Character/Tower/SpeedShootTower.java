package Character.Tower;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class SpeedShootTower extends Tower {
    
    public SpeedShootTower(int x, int y) {
        super(x, y, 10, 150, 10);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();

        DrawTower(g2);

        g2.setTransform(old);

        ShowRange(g2);
    }

    private void ShowRange(Graphics2D g2) {
        if (hovered) {
            g2.setComposite(
                AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.3f
                )
            );
            g2.setColor(Color.YELLOW);
            g2.fillOval(
                x - range,
                y - range,
                range * 2,
                range * 2
            );
            g2.setComposite(
                AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 1f
                )
            );
        }
    }

    private void DrawTower(Graphics2D g2) {
        g2.rotate(angle, x, y);
        g2.setColor(Color.cyan);
        g2.fillRect(
            x - size / 2,
            y - size / 2,
            size,
            size
        );
    }
}
