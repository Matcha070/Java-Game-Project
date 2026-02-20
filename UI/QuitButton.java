package UI;

import java.awt.*;

public class QuitButton {

    private int x, y, width, height;
    private boolean hover = false;

    public QuitButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        if (hover)
            g2.setColor(new Color(200, 60, 60));
        else
            g2.setColor(new Color(170, 40, 40));

        g2.fillRoundRect(x, y, width, height, 25, 25);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, width, height, 25, 25);

        g2.setFont(new Font("Tahoma", Font.BOLD, 28));
        FontMetrics fm = g2.getFontMetrics();

        String text = "QUIT";
        int tx = x + (width - fm.stringWidth(text)) / 2;
        int ty = y + (height + fm.getAscent()) / 2 - 6;

        g2.drawString(text, tx, ty);
    }

    public void handleClick(Point p) {
        if (contains(p)) {
            System.exit(0);
        }
    }

    public void handleHover(Point p) {
        hover = contains(p);
    }

    private boolean contains(Point p) {
        return p.x >= x && p.x <= x + width &&
                p.y >= y && p.y <= y + height;
    }
}
