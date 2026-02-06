
import java.awt.Color;
import java.awt.Graphics;

public class Bullet implements DrawObj {
    double x, y;
    double vx, vy;
    int size = 12;
    double speed;

    public Bullet(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update() {
        x += vx;
        y += vy;
    }

    @Override
    public void draw(Graphics g) {
        System.out.println("Draw Bullet at (" + x + ", " + y + ")");
        g.setColor(Color.BLACK);
        g.fillOval((int)x - size/2, (int)y - size/2, size, size);
    }
}
