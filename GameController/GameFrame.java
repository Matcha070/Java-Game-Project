package GameController;

import UI.StatusUI;
import UI.TowerUI;
import UI.PauseMenu.PauseUI;

import java.awt.event.*;
import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
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

        game.setBounds(0, 0, width, height);
        towerUi.setBounds(0, 0, width, height);
        statusUI.setBounds(0, 0, width, height);
        pauseUI.setBounds(0, 0, width, height);

        layer.add(game, Integer.valueOf(0));
        layer.add(towerUi, Integer.valueOf(1));
        layer.add(statusUI, Integer.valueOf(2));
        layer.add(pauseUI, Integer.valueOf(3));

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
