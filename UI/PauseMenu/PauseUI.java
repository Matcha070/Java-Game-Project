package UI.PauseMenu;

import GameController.GamePanel;
import UI.QuitButton;
import UI.ResumeButton;
import asset.AudioManager;
import java.awt.*;
import javax.swing.*;

public class PauseUI extends JPanel {

    private GamePanel game;

    private PauseButton pauseButton;
    private ResumeButton resumeButton;
    private RestartButton restartButton;
    private SettingButton settingButton;
    private QuitButton quitButton;

    public PauseUI(GamePanel game) {

        this.game = game;

        setOpaque(false);
        setPreferredSize(game.getPreferredSize());

        int circleSize = 60;
        int margin = 20;

        pauseButton = new PauseButton(game, margin, margin, circleSize);

        // ===== Center Layout =====
        int btnWidth = 240;
        int btnHeight = 70;
        int gap = 90;

        int centerX = game.getPreferredSize().width / 2 - btnWidth / 2;
        int startY = game.getPreferredSize().height / 2 - 160;

        resumeButton  = new ResumeButton(game, centerX, startY, btnWidth, btnHeight);
        restartButton = new RestartButton(game, centerX, startY + gap, btnWidth, btnHeight);
        settingButton = new SettingButton(centerX, startY + gap * 2, btnWidth, btnHeight);
        quitButton    = new QuitButton(centerX, startY + gap * 3, btnWidth, btnHeight);
    }

    // ================= CLICK =================

    public void handleClick(Point p) {

        // ===== ปุ่ม pause มุมซ้าย =====
        if (pauseButton.isClick(p)) {

            if (game.isPause()) {
                AudioManager.resumeBGM();
            } else {
                AudioManager.pauseBGM();
            }

            game.togglePause();
            game.setPause(true);
            repaint();
            return;
        }

        // ===== ตอนเกม Pause อยู่ =====
        if (game.isPause()) {

            resumeButton.handleClick(p);
            restartButton.handleClick(p);

            // เปิด Setting (ไม่ resume เกม)
            if (settingButton.isClick(p)) {
                new Setting();
            }

            quitButton.handleClick(p);
        }

        repaint();
    }

    // ================= HOVER =================

    public void handleHover(Point p) {

        pauseButton.handleHover(p);

        if (game.isPause()) {
            resumeButton.handleHover(p);
            restartButton.handleHover(p);
            settingButton.handleHover(p);
            quitButton.handleHover(p);
        }

        repaint();
    }

    public boolean isOnUI(Point p) {
        return pauseButton.isClick(p);
    }

    // ================= DRAW =================

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        pauseButton.draw(g);

        if (game.isPause()) {

            Graphics2D g2 = (Graphics2D) g;

            // Overlay
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, getWidth(), getHeight());

            // Title
            g2.setFont(new Font("Tahoma", Font.BOLD, 60));
            g2.setColor(Color.WHITE);

            String text = "PAUSED";
            FontMetrics fm = g2.getFontMetrics();

            int tx = (getWidth() - fm.stringWidth(text)) / 2;
            int ty = getHeight() / 2 - 200;

            g2.drawString(text, tx, ty);

            // Buttons
            resumeButton.draw(g);
            restartButton.draw(g);
            settingButton.draw(g);
            quitButton.draw(g);
        }
    }
}
