package UI;

import Character.Tower.PlayerStat;
import GameController.GamePanel;
import asset.Asset;
import java.awt.*;
import javax.swing.*;

public class StatusUI extends JPanel {
    GamePanel game;
    private final Color BOX_COLOR = new Color(0, 0, 0, 150);
    private final Font FONT_22 = new Font("Tahoma", Font.BOLD, 22);

    public StatusUI(GamePanel gamePanel) {
        this.game = gamePanel;

        setOpaque(false);
        setPreferredSize(game.getPreferredSize());
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        MoneyUi(g2);
        HeartUi(g2);

        if (PlayerStat.HP <= 0) {
            drawGameOver(g2);
            game.setGameOver(true);
        }

    }

    private void MoneyUi(Graphics2D g2) {
        int currentMoney = game.getMoney().getAmount();
        String text = String.valueOf(currentMoney);

        
        g2.setFont(FONT_22);

        FontMetrics fm = g2.getFontMetrics();

        int padding = 20;
        int iconSize = 48;

        // ===== คำนวณขนาดกล่องก่อน =====
        int contentWidth = iconSize + 10 + fm.stringWidth(text);
        int boxWidth = contentWidth + padding * 2;
        int boxHeight = iconSize + padding * 2;

        // ===== ตำแหน่งกล่อง (ขวาบน) =====
        int boxX = getWidth() - boxWidth - padding;
        int boxY = padding;

        // ===== ตำแหน่ง icon =====
        int iconX = boxX + padding;
        int iconY = boxY + padding;

        // ===== ตำแหน่ง text =====
        int textX = iconX + iconSize + 10;
        int textY = iconY + iconSize - 15;

        // ===== วาดพื้นหลัง =====
        g2.setColor(BOX_COLOR);
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        // ===== วาดขอบ =====
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        // ===== วาดไอคอน =====
        g2.drawImage(Asset.COIN_ICON, iconX, iconY, iconSize, iconSize, null);

        // ===== วาดข้อความ =====
        g2.setColor(Color.WHITE);
        g2.drawString(text, textX, textY);
    }

    private void HeartUi(Graphics2D g2) {

        String text = String.valueOf(PlayerStat.HP);

        
        g2.setFont(FONT_22);
        FontMetrics fm = g2.getFontMetrics();

        int padding = 20;
        int iconSize = 48;

        // ===== คำนวณขนาดกล่อง =====
        int contentWidth = iconSize + 10 + fm.stringWidth(text);
        int boxWidth = contentWidth + padding * 2;
        int boxHeight = iconSize + padding * 2;

        // ===== ตำแหน่งกล่อง (ขวาบน ต่ำลงมาหน่อย) =====
        int boxX = getWidth() - boxWidth - padding;
        int boxY = padding + boxHeight + 20; // ต่ำกว่า money

        int iconX = boxX + padding;
        int iconY = boxY + padding;

        int textX = iconX + iconSize + 10;
        int textY = iconY + iconSize - 15;

        g2.setColor(BOX_COLOR);
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        g2.drawImage(Asset.PLAYERHp, iconX, iconY, iconSize, iconSize, null);

        g2.setColor(Color.WHITE);
        g2.drawString(text, textX, textY);
    }

    private void drawGameOver(Graphics2D g2) {

        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, getWidth(), getHeight());

        String text = "GAME OVER!";

        g2.setFont(new Font("Tahoma", Font.BOLD, 80));
        FontMetrics fm = g2.getFontMetrics();

        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = getHeight() / 2;

        g2.setColor(Color.RED);
        g2.drawString(text, x, y);
    }

}
