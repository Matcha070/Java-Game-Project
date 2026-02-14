package UI;

import GameController.GamePanel;
import Map.MapData;
import asset.Asset;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class TowerUI extends JPanel {

    GamePanel game;

    ArrayList<HitButton> selectTowers = new ArrayList<>();
    ArrayList<DeleteTower> deleteTowers = new ArrayList<>();

    int hoverId = -1;

    private boolean isOpen = true;
    private int panelHeight;
    private int panelOffsetY = 0;
    private int slideSpeed = 5;

    private Rectangle toggleBtn;
    private double currentAngle = 0;
    private double targetAngle = 0;
    private double rotateSpeed = 5;

    public TowerUI(GamePanel gamePanel) {

        this.game = gamePanel;

        setOpaque(false);
        setPreferredSize(game.getPreferredSize());

        int panelW = 470;
        int margin = 10;
        int gap = 10;

        int btnW = (panelW - margin * 2 - gap * 3) / 4;
        int btnH = 3 * MapData.TILE_SIZE - margin * 2;

        panelHeight = 3 * MapData.TILE_SIZE;
        int startY = game.getPreferredSize().height - panelHeight + margin;

        int circleSize = 60;
        int dx = (getPreferredSize().width / 2) + 20;
        int dy = getPreferredSize().height - 120;

        for (int i = 0; i < 4; i++) {
            int x = margin + i * (btnW + gap);
            int y = startY;
            selectTowers.add(new HitButton(i, x, y, btnW, btnH));
        }

        deleteTowers.add(new DeleteTower(dx, dy, circleSize));
    }

    public void update() {

        // --------Toggle rotate ---------
        if (currentAngle < targetAngle) {
            currentAngle += rotateSpeed;
            if (currentAngle > targetAngle) {
                currentAngle = targetAngle;
            }
        } else if (currentAngle > targetAngle) {
            currentAngle -= rotateSpeed;
            if (currentAngle < targetAngle) {
                currentAngle = targetAngle;
            }
        }
        // ------- Slider Panel ---------
        if (!isOpen) {
            if (panelOffsetY < panelHeight) {
                panelOffsetY += slideSpeed;
                if (panelOffsetY > panelHeight)
                    panelOffsetY = panelHeight;
            }
        } else {
            if (panelOffsetY > 0) {
                panelOffsetY -= slideSpeed;
                if (panelOffsetY < 0)
                    panelOffsetY = 0;
            }
        }
        repaint();
    }

    public void handleClickToggle(Point p) {
        if (toggleBtn != null && toggleBtn.contains(p)) {
            isOpen = !isOpen;

            if (isOpen) {
                targetAngle = 0;
            } else {
                targetAngle = 180;
            }
        }
    }

    public boolean isOnUI(Point p) {
        if (toggleBtn != null && toggleBtn.contains(p))
            return true;
        for (HitButton b : selectTowers)
            if (b.isClick(p))
                return true;
        for (DeleteTower d : deleteTowers)
            if (d.isClick(p))
                return true;
        return false;
    }

    public void handleClickSelect(Point p) {
        for (HitButton b : selectTowers)
            if (b.isClick(p))
                game.setId(b.getId());
    }

    public void handleClickDelete(Point p) {
        for (DeleteTower d : deleteTowers)
            if (d.isClick(p))
                game.setCanDelete(true);
    }

    public void handleHover(Point p) {
        hoverId = -1;
        for (HitButton b : selectTowers) {
            if (b.isClick(p)) {
                hoverId = b.getId();
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        int width = (getWidth() / 2) - 10;
        int drawY = getHeight() - panelHeight + panelOffsetY;
        int btnX = 10;
        int btnY = drawY - 35;
        int btnW = 40;
        int btnH = 25;

        // bar panel
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, drawY, width, panelHeight);

        toggleBtn = new Rectangle(btnX, btnY, btnW, btnH);

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();

        g2.rotate(Math.toRadians(currentAngle), btnX + btnW / 2, btnY + btnH / 2);

        // Toogle
        g2.drawImage(Asset.ARROWTOGGLE, btnX, btnY, btnW, btnH, null);

        g2.setTransform(old);

        g2.translate(0, panelOffsetY);

        for (HitButton b : selectTowers)
            b.draw(g);

        for (DeleteTower d : deleteTowers)
            d.draw(g);

        g2.translate(0, -panelOffsetY);

        if (hoverId != -1) {
            String text = getHoverText(hoverId);
            g.setFont(new Font("Tahoma", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            FontMetrics fm = g.getFontMetrics();
            int tx = (getWidth() - fm.stringWidth(text)) / 2;
            int ty = getHeight() - 200;
            g.drawString(text, tx, ty);
        }
    }

    private String getHoverText(int id) {
        switch (id) {
            case 0:
                return "base tower";
            case 1:
                return "speed shoot tower";
            case 2:
                return "sniper tower";
            case 3:
                return "magic tower";
            default:
                return "";
        }
    }
}

class DeleteTower {

    int x, y, size;

    public DeleteTower(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public boolean isClick(Point p) {
        double dx = p.x - (x + size / 2);
        double dy = p.y - (y + size / 2);
        return dx * dx + dy * dy <= (size / 2) * (size / 2);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 200, 255, 120));
        g.fillOval(x, y, size, size);
        g.setColor(Color.WHITE);
        g.drawOval(x, y, size, size);
    }
}
