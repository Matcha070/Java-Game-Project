import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Enemy {
    private BufferedImage slime;
    private int size = 64;

    private double x, y;
    private Point[] pathPoints;
    private int targetIndex = 0;
    private double speed = 2.0; // speed Enemy

    private boolean alive = true;

    public Enemy() {

        //skin
        try {
            slime = ImageIO.read(getClass().getResource("asset\\enemy\\Slime.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Start ที่ จุด start
        Point start = MapData.pathPoints.get(0);
        x = start.x;
        y = start.y;
    }

    public void update() {
        if (targetIndex >= MapData.pathPoints.size()){
            alive = false;
            return;
        }

        Point target = MapData.pathPoints.get(targetIndex);

        double dx = target.x - x;
        double dy = target.y - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < speed) {//กัน case ที่วิ่งไวเกินแล้วย้อนกลับ
            x = target.x;
            y = target.y;
            targetIndex++;
        } else {
            // เดินเข้าไปใกล้เป้าหมาย
            x += (dx / dist) * speed;
            y += (dy / dist) * speed;
        }
    }

    public void draw(Graphics g) {
        // g.setColor(Color.RED);
        // g.fillOval((int)x - 8, (int)y - 8, 16, 16);
        g.drawImage(
        slime,
        (int)x - size / 2,
        (int)y - size / 2,
        size,
        size,
        null
    );
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

    
    
}
