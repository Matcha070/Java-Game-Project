package UI;

import Character.Tower.Tower;

import GameController.GamePanel;
import GameController.Money;
import Map.MapData;
import asset.Asset;

import java.awt.*;
import javax.swing.*;

public class StatusUI extends JPanel {
    GamePanel game;
    

    public StatusUI(GamePanel gamePanel) {
        this.game = gamePanel;  

        setOpaque(false);
        setPreferredSize(game.getPreferredSize());
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        int currentMoney = game.getMoney().getAmount();
        g.setFont(new Font("Tahoma", Font.BOLD, 22));
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(String.valueOf(currentMoney), getWidth() - 140, getHeight() / 9 +2);

        g.drawImage(Asset.COIN_ICON, getWidth() - 200, getHeight() / 10 - 20, 48, 48, null);

    }
}
