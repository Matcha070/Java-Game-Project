package GameController;

import UI.PauseUI;
import UI.StatusUI;
import UI.TowerUI;
import java.awt.event.*;
import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Tower Defense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel game = new GamePanel();
        TowerUI ui = new TowerUI(game);
        StatusUI statusUI = new StatusUI(game);
        PauseUI pauseUI = new PauseUI(game);

        game.setUI(ui);

        JLayeredPane layer = new JLayeredPane();
        layer.setPreferredSize(game.getPreferredSize());

        int width = game.getPreferredSize().width;
        int height = game.getPreferredSize().height;

        game.setBounds(0, 0, width, height);
        ui.setBounds(0, 0, width, height);
        statusUI.setBounds(0, 0, width, height);
        pauseUI.setBounds(0, 0, width, height);   

        layer.add(game, Integer.valueOf(0));
        layer.add(ui, Integer.valueOf(1));
        layer.add(statusUI, Integer.valueOf(2));
        layer.add(pauseUI, Integer.valueOf(3));   

        InputController input = new InputController(game, ui, pauseUI);
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
                        ui.getPreferredSize().height
                );
            }
        });
    }
}
