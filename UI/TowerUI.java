package UI;

import GameController.GamePanel;
import GameObject.Character.Tower.BaseTower;
import GameObject.Character.Tower.MagicTower;
import GameObject.Character.Tower.SniperTower;
import GameObject.Character.Tower.SpeedShootTower;
import GameObject.Character.Tower.Tower;
import Map.MapData;
import asset.Asset;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class TowerUI extends JPanel {

    private final Color BOX_COLOR = new Color(0, 0, 0, 150);
    private final Color TEXT_GOLD = new Color(255, 215, 0);
    private final Font FONT = new Font("Tahoma", Font.BOLD, 18);
    private final Stroke STROKE = new BasicStroke(2f);

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

        if (game.isPause() || game.isOver() || game.getCanDelete())
            return;
        GamePanel.isSelectTower = true;
        GamePanel.delete = false;
        for (HitButton b : selectTowers)
            if (b.isClick(p)) {
                game.setId(b.getId());
            }
    }

    public void handleClickDelete(Point p) {
        if (game.isPause() || game.isOver())
            return;

        for (DeleteTower d : deleteTowers) {
            if (d.isClick(p)) {
                GamePanel.isSelectTower = false; // reset select mode ก่อน
                game.setId(-1); // ยกเลิก tower ที่เลือกอยู่
                game.setCanDelete(true);
            }
        }
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

        g.setColor(BOX_COLOR);
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

            // show stat
            int padding = 20;
            int lineHeight = 25;

            Tower tower = allTowers.get(hoverId);

            String name = getHoverText(hoverId);

            int price = tower.getPrice();
            int dmg = tower.getDamage();
            int range = tower.getRange();
            int firerate = tower.getFirerate();
            String description = tower.getDescription();

            g2.setFont(FONT);
            FontMetrics fm = g2.getFontMetrics();

            String priceText = "Price: " + price;
            String dmgText = "Damage: " + dmg;
            String rangeText = "Range: " + range;
            String fireText = "Fire Rate: " + firerate;
            String descriptionText = description;

            int maxTextWidth = Math.max(
                    fm.stringWidth(name),
                    Math.max(
                            fm.stringWidth(dmgText),
                            Math.max(fm.stringWidth(rangeText), fm.stringWidth(fireText))));

            int boxWidth = maxTextWidth + padding * 2;
            List<String> wrappedDesc = wrapText(descriptionText, fm, maxTextWidth);
            int descLines = wrappedDesc.size();

            int boxHeight = lineHeight * (5 + descLines) + padding * 2 - 10;

            int boxX = getWidth() - boxWidth - padding;
            int boxY = padding + 240;

            int textX = boxX + padding;
            int textY = boxY + padding + fm.getAscent();

            g2.setColor(BOX_COLOR);
            g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

            g2.setColor(Color.WHITE);
            g2.setStroke(STROKE);
            g2.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

            g2.setColor(TEXT_GOLD);

            g2.setColor(new Color(255, 215, 0));

            g2.drawString(name, textX, textY);

            // ===== Stat =====
            g2.setColor(Color.WHITE);
            g2.drawString(priceText, textX, textY + lineHeight);
            g2.drawString(dmgText, textX, textY + lineHeight * 2);
            g2.drawString(rangeText, textX, textY + lineHeight * 3);
            g2.drawString(fireText, textX, textY + lineHeight * 4);
            int descY = textY + lineHeight * 5 + 5;

            // สี description ให้นุ่มลง
            g2.setColor(new Color(200, 200, 255));

            wrappedDesc = wrapText(descriptionText, fm, boxWidth - padding * 2);

            for (String line : wrappedDesc) {
                g2.drawString(line, textX, descY);
                descY += lineHeight - 5;
            }
        }
    }

    private List<String> wrapText(String text, FontMetrics fm, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine + word + " ";
            if (fm.stringWidth(testLine) > maxWidth) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word + " ");
            } else {
                currentLine.append(word).append(" ");
            }
        }

        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    private String getHoverText(int id) {
        switch (id) {
            case 0:
                return "Base Tower";
            case 1:
                return "Speed Shoot Tower";
            case 2:
                return "Sniper Tower";
            case 3:
                return "Magic Tower";
            default:
                return "";
        }
    }

}

class DeleteTower {

    GamePanel game;

    int x, y, size;
    boolean hover = false;

    public DeleteTower(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public boolean isClick(Point p) {
        // if (GamePanel.isSelectTower) {
        // return false;
        // }
        double dx = p.x - (x + size / 2);
        double dy = p.y - (y + size / 2);
        // System.out.println(dx * dx + dy * dy <= (size / 2) * (size / 2));
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
