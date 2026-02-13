package UI;


import GameController.GamePanel;
import Map.MapData;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class TowerUI extends JPanel {
    GamePanel game;

    ArrayList<HitButton> selectTowers = new ArrayList<>();
    ArrayList<DeleteTower> deleteTowers = new ArrayList<>();
    int hoverId = -1;

    public TowerUI(GamePanel gamePanel) {
        this.game = gamePanel;

        setOpaque(false);
        setPreferredSize(game.getPreferredSize());
        int panelW = 470;
        int panelH = 144;

        int margin = 10;
        int gap = 10;

        int btnW = (panelW - margin * 2 - gap * 3) / 4;
        int btnH = panelH - margin * 2;

        int startY = game.getPreferredSize().height - panelH + margin;

        for (int i = 0; i < 4; i++) {

            int x = margin + i * (btnW + gap);
            int y = startY;

            selectTowers.add(new HitButton(i, x, y, btnW, btnH));

            // วงกลมอยู่ด้านขวาของสี่เหลี่ยม
        }
        int circleSize = 60;

        int x = (getPreferredSize().width / 2) + 20;  // อยู่ข้าง ๆ แถบ
        int y = getPreferredSize().height - 120;      // ตำแหน่งด้านล่าง

        deleteTowers.add(new DeleteTower(x, y, circleSize));
    }

    public boolean isOnUI(Point p) {
    for (HitButton b : selectTowers) {
        if (b.isClick(p)) return true;
    }
    for (DeleteTower b : deleteTowers) {
        if (b.isClick(p)) return true;
    }
    return false;
    }

    public void handleClickSelect(Point p) {
        for (HitButton b : selectTowers) {
            if (b.isClick(p)) {
                game.setId(b.getId());
            }
        } 
    }

    public void handleClickDelete(Point p) {

        for (DeleteTower d : deleteTowers) {
            if (d.isClick(p)) {
                game.setCanDelete(true);
            }
        }
    }

    public void handleHover(Point p) {
        hoverId = -1;
        for (HitButton b : selectTowers) {
            if (b.isClick(p)) {
                hoverId = b.getId();
                break;
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = (getWidth() / 2) - 10;
        int height = 3 * MapData.TILE_SIZE;

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, getHeight() - height, width, height);

        for (HitButton b : selectTowers) {
            b.draw(g);
        }

        for (DeleteTower d : deleteTowers) {
            d.draw(g);
        }
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

class HitButton {

    Rectangle box;
    int id;

    public HitButton(int id, int x, int y, int w, int h) {
        this.id = id;
        this.box = new Rectangle(x, y, w, h);
    }

    public boolean isClick(Point p) {
        return box.contains(p);
    }

    public int getId() {
        return id;
    }

    public void draw(Graphics g) {

        g.setColor(new Color(255, 255, 255, 40));
        g.fillRect(box.x, box.y, box.width, box.height);
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