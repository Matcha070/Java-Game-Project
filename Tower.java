import java.awt.*;

public class Tower {
    int x, y;
    int size = 30;

    public Tower(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(
            x - size / 2,
            y - size / 2,
            size,
            size
        );
    }
}