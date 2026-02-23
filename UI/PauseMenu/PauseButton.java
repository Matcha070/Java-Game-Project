package UI.PauseMenu;

import GameController.GamePanel;
import asset.Asset;
import asset.AudioManager;
import java.awt.*;

public class PauseButton {

    private GamePanel game;
    private int x, y, size;
    private boolean hover = false;
    private boolean  isEntry = false;

    public PauseButton(GamePanel game, int x, int y, int size) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public boolean isClick(Point p) {
        double dx = p.x - (x + size / 2);
        double dy = p.y - (y + size / 2);
        return dx * dx + dy * dy <= (size / 2) * (size / 2);
    }

    public void handleHover(Point p) {
        hover = isClick(p);
        if(hover && !isEntry){
            AudioManager.playSFX(Asset.SFX_MENU_HOVER);
            // System.out.println("1");
           isEntry = true;
        }else if (!hover && isEntry) {
            isEntry = false;
            // System.out.println("2");
        }
    }

    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        // วงกลมพื้นหลัง
        if (hover)
            g2.setColor(new Color(255, 255, 255, 180));
        else
            g2.setColor(new Color(0, 0, 0, 150));

        g2.fillOval(x, y, size, size);

        g2.setColor(Color.WHITE);
        g2.drawOval(x, y, size, size);

        int centerX = x + size / 2;
        int centerY = y + size / 2;

        // ใช้ state จาก GamePanel เท่านั้น
        if (!game.isPause()) {
            int barW = 6;
            int barH = 20;

            g2.fillRect(centerX - 10, centerY - barH / 2, barW, barH);
            g2.fillRect(centerX + 4, centerY - barH / 2, barW, barH);
        } else {
            int[] px = { centerX - 6, centerX - 6, centerX + 12 };
            int[] py = { centerY - 12, centerY + 12, centerY };
            g2.fillPolygon(px, py, 3);
        }
    }
}
