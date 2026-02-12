package UI;

import GameController.GamePanel;
import Map.MapData;
import java.awt.*;
import javax.swing.*;

public class RightUI extends JPanel {
    GamePanel game;

    public RightUI(GamePanel gamePanel) {
        this.game = gamePanel;

        setOpaque(false);
        setPreferredSize(game.getPreferredSize());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = (getWidth() / 4) + 100;

        int height = 2 * MapData.TILE_SIZE + 50;

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(getWidth() - width, 0, width, height);

    }
}
