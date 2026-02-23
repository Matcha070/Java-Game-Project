package GameController;

import UI.PauseMenu.PauseUI;
import UI.StatusUI;
import UI.TowerUI;
import asset.Asset;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import javax.swing.*;


public class GameFrame extends JFrame {

    public GameFrame() {
        Asset.load();
        setTitle("Tower Defense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel game = new GamePanel();
        TowerUI towerUi = new TowerUI(game);
        StatusUI statusUI = new StatusUI(game);
        PauseUI pauseUI = new PauseUI(game);

        game.setTowerUI(towerUi);
        game.setPauseUI(pauseUI);

        JLayeredPane layer = new JLayeredPane();
        layer.setPreferredSize(game.getPreferredSize());

        int width = game.getPreferredSize().width;
        int height = game.getPreferredSize().height;

        // ต้องสร้าง gameMap ก่อน และ set bounds ด้วย
        JPanel gameMap = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                if (Asset.MainMenuPic == null) return; // เปลี่ยนเป็น Asset ที่มีรูป map จริงๆ

                int imgW = Asset.MainMenuPic.getWidth();
                int imgH = Asset.MainMenuPic.getHeight();
                int panelW = getWidth();
                int panelH = getHeight();

                double scale = Math.max((double) panelW / imgW, (double) panelH / imgH);
                int drawW = (int) (imgW * scale);
                int drawH = (int) (imgH * scale);
                int x = (panelW - drawW) / 2;
                int y = (panelH - drawH) / 2;

                g2.drawImage(Asset.MainMenuPic, x, y, drawW, drawH, null);
                System.out.println(Asset.Map);
            }
        };

        gameMap.setOpaque(true);
        gameMap.setBounds(0, 0, width, height); // ต้อง set bounds ด้วย
        game.setBounds(0, 0, width, height);
        towerUi.setBounds(0, 0, width, height);
        statusUI.setBounds(0, 0, width, height);
        pauseUI.setBounds(0, 0, width, height);

        layer.add(gameMap, Integer.valueOf(0)); // layer ต่ำสุด = background
        layer.add(game, Integer.valueOf(1));
        layer.add(towerUi, Integer.valueOf(2));
        layer.add(statusUI, Integer.valueOf(3));
        layer.add(pauseUI, Integer.valueOf(4));

        InputController input = new InputController(game, towerUi, pauseUI);
        layer.addMouseListener(input);
        layer.addMouseMotionListener(input);

        add(layer);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void paintUI(GamePanel game, JPanel ui) {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = game.getWidth();

                ui.setBounds(
                        w - ui.getPreferredSize().width,
                        0,
                        ui.getPreferredSize().width,
                        ui.getPreferredSize().height);
            }
        });
    }

    
}