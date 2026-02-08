import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy implements DrawObj {
    private BufferedImage slime;
    private int size = MapData.TILE_SIZE;

    double x, y;
    private double vx, vy;
    private Point[] pathPoints;
    private int targetIndex = 0;
    private double speed = 2.0; // speed Enemy

    private int hp;
    private boolean alive = true;

    public Enemy(int hp) {

        // Start ที่ จุด start
        Point start = MapData.pathPoints.get(0);
        x = start.x;
        y = start.y;
        this.hp = hp;
    }

    public void update() {
        if (targetIndex >= MapData.pathPoints.size()) {
            alive = false;
            return;
        }

        Point target = MapData.pathPoints.get(targetIndex);

        double dx = target.x - x;
        double dy = target.y - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < speed) {// กัน case ที่วิ่งไวเกินแล้วย้อนกลับ
            x = target.x;
            y = target.y;
            targetIndex++;
        } else {
            // เดินเข้าไปใกล้เป้าหมาย
            vx = (dx / dist) * speed;
            vy = (dy / dist) * speed;
            x += vx;
            y += vy;
        }
    }

    @Override
    public void draw(Graphics g) {
        // g.setColor(Color.RED);
        // g.fillOval((int)x - 8, (int)y - 8, 16, 16);
        g.drawImage(
                Asset.SLIME,
                (int) x - size / 2,
                (int) y - size / 2,
                size,
                size,
                null);
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            alive = false;
        }
    }

    public int getSize() {
        return size;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public Point getPosition() {
        return new Point((int) x, (int) y);
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

}
