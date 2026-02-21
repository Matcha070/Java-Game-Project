package Character.Tower;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import Map.MapData;


public class SpeedShootTower extends Tower {

    public SpeedShootTower(int x, int y) {
        super("Speed Shoot Tower", x, y, 6, MapData.TILE_SIZE * 3, 15, 180, 80);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();

        super.DrawTower(g2);

        g2.setTransform(old);

        if(hovered){
            ShowRange(g2);
        }
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

    public void ShowRange(Graphics2D g2) {
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
