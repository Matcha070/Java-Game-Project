import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

public class towerUI extends JPanel {
    GamePanel game;

    ArrayList<HitButton> buttons = new ArrayList<>();

    public towerUI(GamePanel gamePanel) {
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

            buttons.add(new HitButton(i, x, y, btnW, btnH));
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                for (HitButton b : buttons) {
                    if (b.isClick(e.getPoint())) {
                        System.out.println("click on: " + b.getId());
                        onButtonClick(b.getId());
                    }
                }
            }
        });
    }

    private void onButtonClick(int id) {
        System.out.println("logic btn: " + id);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = (getWidth() / 2) - 10;
        int height = 3 * MapData.TILE_SIZE;

        
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, getHeight() - height, width, height);

        
        for (HitButton b : buttons) {
            b.draw(g);
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