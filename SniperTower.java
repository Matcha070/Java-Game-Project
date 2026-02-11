
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class SniperTower extends Tower {
    
    public SniperTower(int x, int y) {
        super(x, y, 50, 250, 60);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();

        g2.rotate(angle, x, y);
        g2.setColor(Color.MAGENTA);
        g2.fillRect(
            x - size / 2,
            y - size / 2,
            size,
            size
        );

        g2.setTransform(old);

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
}
