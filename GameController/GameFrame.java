package GameController;

import java.awt.event.*;
import javax.swing.*;

import UI.TowerUI;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Tower Defense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // สร้างแค่ชุดเดียว
        GamePanel game = new GamePanel();
        TowerUI ui = new TowerUI(game);

        // Layer
        JLayeredPane layer = new JLayeredPane();
        layer.setPreferredSize(game.getPreferredSize());

        game.setBounds(0, 0,
                game.getPreferredSize().width,
                game.getPreferredSize().height);

        ui.setBounds(0, 0,
                game.getPreferredSize().width,
                game.getPreferredSize().height);

        layer.add(game, Integer.valueOf(0));
        layer.add(ui, Integer.valueOf(1));

        // Mouse controller ตัวเดียว
        InputController input = new InputController(game, ui);
        layer.addMouseListener(input);
        layer.addMouseMotionListener(input);

        // ใส่ layer เข้า frame
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

                ui.setBounds(w - ui.getPreferredSize().width, 0, ui.getPreferredSize().width,
                        ui.getPreferredSize().height);
            }
        });
    }

}
