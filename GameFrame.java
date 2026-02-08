import javax.swing.*;

import java.awt.event.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Tower Defense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel gamePanel = new GamePanel();
        rightUI hud = new rightUI(gamePanel);
        towerUI towerUi = new towerUI(gamePanel);

        gamePanel.setLayout(null);
        add(gamePanel);
        gamePanel.add(hud);
        gamePanel.add(towerUi);

        pack(); // ปรับขนาดตาม Panel

        paintUI(gamePanel, hud);
        paintUI(gamePanel, towerUi);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void paintUI(GamePanel game, JPanel ui) {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = game.getWidth();

                ui.setBounds(w - ui.getPreferredSize().width, 0, ui.getPreferredSize().width,
                        ui.getPreferredSize().height);
            }
        });
    }

}
