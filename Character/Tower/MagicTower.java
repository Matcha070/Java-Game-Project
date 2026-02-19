package Character.Tower;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class MagicTower extends Tower {

    public MagicTower(int x, int y) {
        super("Magic Tower",x, y, 100, 150, 30, 20, 10);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();

        super.DrawTower(g2);

        g2.setTransform(old);

        ShowRange(g2);
    }

    @Override
    public void drawGuide(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(
                AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, 0.7f));

        super.DrawTower(g2);
        
        g2.setComposite(
                AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, 1f));
    }

    private void ShowRange(Graphics2D g2) {
        if (hovered) {
            g2.setComposite(
                    AlphaComposite.getInstance(
                            AlphaComposite.SRC_OVER, 0.3f));
            g2.setColor(Color.YELLOW);
            g2.fillOval(
                    x - range,
                    y - range,
                    range * 2,
                    range * 2);
            g2.setComposite(
                    AlphaComposite.getInstance(
                            AlphaComposite.SRC_OVER, 1f));
        }
    }

}
