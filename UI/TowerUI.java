package UI;

import Character.Tower.BaseTower;
import Character.Tower.MagicTower;
import Character.Tower.SniperTower;
import Character.Tower.SpeedShootTower;
import Character.Tower.Tower;
import GameController.GamePanel;
import Map.MapData;
import asset.Asset;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.*;

public class TowerUI extends JPanel {

    GamePanel game;
    ArrayList<Tower> allTowers = new ArrayList<>(java.util.List.of(
            new BaseTower(0, 0),
            new SpeedShootTower(0, 0),
            new SniperTower(0, 0),
            new MagicTower(0, 0)));
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
        int dx = (getPreferredSize().width / 2);
        int dy = getPreferredSize().height - 100;

        for (int i = 0; i < 4; i++) {
            int x = margin + i * (btnW + gap);
            int y = startY;
            selectTowers.add(new HitButton(i, x, y, btnW, btnH));
        }

        deleteTowers.add(new DeleteTower(dx, dy, circleSize));
    }

    public void update() {

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

    public boolean isToggleClicked(Point p) {
        return toggleBtn != null && toggleBtn.contains(p);
    }

    public boolean isOnUI(Point p) {

        if (toggleBtn != null && toggleBtn.contains(p))
            return true;

        if (!isOpen)
            return false;

        for (HitButton b : selectTowers)
            if (b.isClick(p))
                return true;

        for (DeleteTower d : deleteTowers)
            if (d.isClick(p))
                return true;

        return false;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public void handleClickSelect(Point p) {

        if (game.isPause() || game.isOver())
            return;

        for (HitButton b : selectTowers)
            if (b.isClick(p))
                game.setId(b.getId());
    }

    public void handleClickDelete(Point p) {

        if (game.isPause() || game.isOver())
            return;

        for (DeleteTower d : deleteTowers)
            if (d.isClick(p))
                game.setCanDelete(true);
    }

    public void handleHover(Point p) {

        if (game.isPause() || game.isOver())
            return;
        hoverId = -1;
        for (HitButton b : selectTowers) {
            if (b.isClick(p)) {
                hoverId = b.getId();
                break;
            }
        }

        for (DeleteTower b : deleteTowers) {
            b.handleHover(p);
            game.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = (getWidth() / 2) + 80;
        int drawY = getHeight() - panelHeight + panelOffsetY;

        int btnX = 10;
        int btnY = drawY - 35;
        int btnW = 40;
        int btnH = 25;

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, drawY, width, panelHeight);

        toggleBtn = new Rectangle(btnX, btnY, btnW, btnH);

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();

        g2.rotate(Math.toRadians(currentAngle),
                btnX + btnW / 2,
                btnY + btnH / 2);

        g2.drawImage(Asset.ARROWTOGGLE, btnX, btnY, btnW, btnH, null);

        g2.setTransform(old);

        g2.translate(0, panelOffsetY);

        for (HitButton b : selectTowers)
            b.draw(g);

        for (DeleteTower d : deleteTowers)
            d.draw(g);

        g2.translate(0, -panelOffsetY);

        if (hoverId != -1) {

            int padding = 20;
            int lineHeight = 25;

            Tower tower = allTowers.get(hoverId);

            int dmg = tower.getDamage();
            int range = tower.getRange();
            int firerate = tower.getFirerate();

            g2.setFont(new Font("Tahoma", Font.BOLD, 18));
            g2.setColor(Color.WHITE);

            FontMetrics fm = g2.getFontMetrics();

            // ===== ข้อความ =====
            String dmgText = "Damage: " + dmg;
            String rangeText = "Range: " + range;
            String fireText = "Fire Rate: " + firerate;

            // ===== หา width ที่ยาวที่สุด =====
            int maxTextWidth = Math.max(
                    fm.stringWidth(dmgText),
                    Math.max(
                            fm.stringWidth(rangeText),
                            fm.stringWidth(fireText)
                    )
            );

            // ===== คำนวณขนาดกล่อง =====
            int boxWidth = maxTextWidth + padding * 2;
            int boxHeight = lineHeight * 3 + padding * 2;

            // ===== ตำแหน่งกล่อง =====
            int boxX = getWidth() - boxWidth - padding;
            int boxY = padding + 110;

            // ===== วาดกล่อง =====
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

            // ===== ตำแหน่งข้อความ (อิงจากกล่อง) =====
            int textX = boxX + padding;
            int textY = boxY + padding + fm.getAscent();

            // ===== วาดข้อความ =====
            g2.drawString(dmgText, textX, textY);
            g2.drawString(rangeText, textX, textY + lineHeight);
            g2.drawString(fireText, textX, textY + lineHeight * 2);

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
    boolean hover = false;

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

    public void handleHover(Point p) {
        hover = isClick(p);
    }

    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        // พื้นหลังปุ่ม
        if (hover) {
            g2.setColor(new Color(255, 80, 80, 180));
        } else {
            g2.setColor(new Color(0, 0, 0, 0));
        }

        g2.fillOval(x, y, size, size);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x, y, size, size);

        // =========================
        // วาดไอคอนตรงกลาง
        // =========================

        if (Asset.DELETE_ICON != null) {

            int iconSize = size / 2; // ขนาดไอคอนเล็กกว่าวงกลม
            int iconX = x + (size - iconSize) / 2;
            int iconY = y + (size - iconSize) / 2;

            g2.drawImage(
                    Asset.DELETE_ICON,
                    iconX,
                    iconY,
                    iconSize,
                    iconSize,
                    null);
        }
    }
}
