package UI;

import GameController.GamePanel;
import java.awt.*;
import javax.swing.*;

public class PauseUI extends JPanel {

    private GamePanel game;

    private PauseButton pauseButton;
    private ResumeButton resumeButton;
    private RestartButton restartButton;
    private QuitButton quitButton;

    public PauseUI(GamePanel game) {

        this.game = game;

        setOpaque(false);
        setPreferredSize(game.getPreferredSize());

        int circleSize = 60;
        int margin = 20;

        pauseButton = new PauseButton(game, margin, margin, circleSize);

        // ====== Center Buttons ======
        int btnWidth = 240;
        int btnHeight = 70;
        int gap = 90;

        int centerX = game.getPreferredSize().width / 2 - btnWidth / 2;
        int startY = game.getPreferredSize().height / 2 - 80;

        resumeButton  = new ResumeButton(game, centerX, startY, btnWidth, btnHeight);
        restartButton = new RestartButton(game, centerX, startY + gap, btnWidth, btnHeight);
        quitButton    = new QuitButton(centerX, startY + gap * 2, btnWidth, btnHeight);
    }

    // ================= INPUT =================

    public void handleClick(Point p) {

        // กดปุ่ม pause มุมซ้าย
        if (pauseButton.isClick(p)) {
            game.togglePause();
            repaint();
            return;
        }

        if (game.isPause()) {
            resumeButton.handleClick(p);
            restartButton.handleClick(p);
            quitButton.handleClick(p);
        }

        repaint();
    }

    public void handleHover(Point p) {

        pauseButton.handleHover(p);

        if (game.isPause()) {
            resumeButton.handleHover(p);
            restartButton.handleHover(p);
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
            g2.setColor(new Color(0, 0, 0, 140));
            g2.fillRect(0, 0, getWidth(), getHeight());

            // Title
            g2.setFont(new Font("Tahoma", Font.BOLD, 60));
            g2.setColor(Color.WHITE);

            String text = "PAUSED";
            FontMetrics fm = g2.getFontMetrics();

            int tx = (getWidth() - fm.stringWidth(text)) / 2;
            int ty = getHeight() / 2 - 150;

            g2.drawString(text, tx, ty);

            // Buttons
            resumeButton.draw(g);
            restartButton.draw(g);
            quitButton.draw(g);
        }
    }
}

//
// =========================
// Pause Button
// =========================
//

class PauseButton {

    private GamePanel game;
    private int x, y, size;
    private boolean hover = false;

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

//
// =========================
// Resume Button
// =========================
//

class ResumeButton {

    private int x, y, width, height;
    private GamePanel game;
    private boolean hover = false;

    public ResumeButton(GamePanel game, int x, int y, int width, int height) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        if (hover)
            g2.setColor(new Color(80, 160, 255));
        else
            g2.setColor(new Color(50, 120, 220));

        g2.fillRoundRect(x, y, width, height, 25, 25);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, width, height, 25, 25);

        g2.setFont(new Font("Tahoma", Font.BOLD, 30));
        FontMetrics fm = g2.getFontMetrics();

        String text = "RESUME";
        int tx = x + (width - fm.stringWidth(text)) / 2;
        int ty = y + (height + fm.getAscent()) / 2 - 6;

        g2.drawString(text, tx, ty);
    }

    public void handleClick(Point p) {
        if (contains(p)) {
            game.togglePause();
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

class RestartButton {

    private int x, y, width, height;
    private boolean hover = false;
    private GamePanel game;

    public RestartButton(GamePanel game, int x, int y, int width, int height) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        if (hover)
            g2.setColor(new Color(255, 140, 100));
        else
            g2.setColor(new Color(240, 100, 70));

        g2.fillRoundRect(x, y, width, height, 25, 25);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, width, height, 25, 25);

        g2.setFont(new Font("Tahoma", Font.BOLD, 28));
        FontMetrics fm = g2.getFontMetrics();

        String text = "RESTART";
        int tx = x + (width - fm.stringWidth(text)) / 2;
        int ty = y + (height + fm.getAscent()) / 2 - 6;

        g2.drawString(text, tx, ty);
    }

    public void handleClick(Point p) {
        if (contains(p)) {
            game.RestartGame();
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

class QuitButton {

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
