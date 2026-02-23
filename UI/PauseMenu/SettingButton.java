package UI.PauseMenu;

import asset.Asset;
import asset.AudioManager;
import java.awt.*;

public class SettingButton {

    private int x, y, width, height;
    private boolean hover = false;
    private boolean isEntry = false;

    public SettingButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // ================= CLICK =================
    public boolean isClick(Point p) {
        return new Rectangle(x, y, width, height).contains(p);
    }

    public void handleHover(Point p) {
        hover = isClick(p);
        if(!isEntry && hover){
            isEntry = true;
            AudioManager.playSFX(Asset.SFX_MENU_HOVER);
            // System.out.println("1");
        }else if (!hover && isEntry) {
            isEntry = false;
            // System.out.println("2");
        }
    }

    // ================= DRAW =================
    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        // พื้นหลังปุ่ม
        if (hover) {
            g2.setColor(new Color(90, 90, 255));
        } else {
            g2.setColor(new Color(60, 60, 200));
        }

        g2.fillRoundRect(x, y, width, height, 25, 25);

        // เส้นขอบ
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, width, height, 25, 25);

        // ข้อความ
        g2.setFont(new Font("Tahoma", Font.BOLD, 28));
        g2.setColor(Color.WHITE);

        String text = "SETTINGS";
        FontMetrics fm = g2.getFontMetrics();

        int tx = x + (width - fm.stringWidth(text)) / 2;
        int ty = y + (height - fm.getHeight()) / 2 + fm.getAscent();

        g2.drawString(text, tx, ty);
    }
}
