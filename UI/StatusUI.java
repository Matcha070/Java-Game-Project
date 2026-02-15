package UI;

import GameController.GamePanel;
import asset.Asset;
import java.awt.*;
import javax.swing.*;

public class StatusUI extends JPanel {
    GamePanel game;

    public StatusUI(GamePanel gamePanel) {
        this.game = gamePanel;

        setOpaque(false);
        setPreferredSize(game.getPreferredSize());
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        int currentMoney = game.getMoney().getAmount();
        String text = String.valueOf(currentMoney);

        Font font = new Font("Tahoma", Font.BOLD, 22);
        g2.setFont(font);

        FontMetrics fm = g2.getFontMetrics();

        int padding = 15;
        int iconSize = 48;

        // ตำแหน่ง icon
        int iconX = getWidth() - 170;
        int iconY = getHeight() / 10 - 20;

        // ตำแหน่ง text
        int textX = iconX + iconSize + 10;
        int textY = iconY + iconSize - 15;

        // คำนวณขนาดกรอบ
        int boxX = iconX - padding;
        int boxY = iconY - padding;
        int boxWidth = iconSize + 10 + fm.stringWidth(text) + padding * 2;
        int boxHeight = iconSize + padding * 2;

        // วาดพื้นหลัง
        g2.setColor(new Color(0, 0, 0, 150)); // ดำโปร่ง
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        // วาดขอบ
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        // วาดไอคอน
        g2.drawImage(Asset.COIN_ICON, iconX, iconY, iconSize, iconSize, null);

        // วาดข้อความ
        g2.setColor(Color.WHITE);
        g2.drawString(text, textX, textY);

    }
}
