package UI;

import GameController.GamePanel;
import java.awt.*;
import javax.swing.*;

public class PauseUI extends JPanel {

    GamePanel game;

    private PauseButton pauseButton;

    public PauseUI(GamePanel gamePanel) {

        this.game = gamePanel;
        setOpaque(false);
        setPreferredSize(game.getPreferredSize());

        int circleSize = 60;
        int margin = 20;

        // วงกลมอยู่ซ้ายบน
        int x = margin;
        int y = margin;

        pauseButton = new PauseButton(x, y, circleSize);
    }

    // ===== ใช้ใน InputController =====

    public void handleClick(Point p) {
        game.togglePause();
    }

    public void handleHover(Point p) {
        pauseButton.handleHover(p);
    }

    public boolean isOnUI(Point p) {
        return pauseButton.isClick(p);
    }

    public boolean isPaused() {
        return pauseButton.isPaused();
    }

    // ===== วาด =====

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        pauseButton.draw(g);
    }
}

class PauseButton {

    int x, y, size;
    boolean paused = false;
    boolean hover = false;

    public PauseButton(int x, int y, int size) {
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

    public boolean isPaused() {
        return paused;
    }

    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        // วงกลม
        if (hover)
            g2.setColor(new Color(255,255,255,180));
        else
            g2.setColor(new Color(0,0,0,150));

        g2.fillOval(x, y, size, size);

        g2.setColor(Color.WHITE);
        g2.drawOval(x, y, size, size);

    }
}

