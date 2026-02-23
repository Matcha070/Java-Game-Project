package UI;

import GameController.GamePanel;
import asset.Asset;
import asset.AudioManager;
import java.awt.*;

public class ResumeButton {

    private int x, y, width, height;
    private GamePanel game;
    private boolean hover = false;
    private boolean isEntry = false;

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
            AudioManager.playSFX(Asset.SFX_MENU_CLICK);     
            game.togglePause();
        }
    }

    public void handleHover(Point p) {
        hover = contains(p);
        if(!isEntry && hover){
            isEntry = true;
            AudioManager.playSFX(Asset.SFX_MENU_HOVER);
            // System.out.println("1");
        }else if (!hover && isEntry) {
            isEntry = false;
            // System.out.println("2");
        }
    }

    private boolean contains(Point p) {
        return p.x >= x && p.x <= x + width &&
                p.y >= y && p.y <= y + height;
    }
}