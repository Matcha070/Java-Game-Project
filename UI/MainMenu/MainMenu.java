package UI.MainMenu;

import GameController.GameFrame;
import java.awt.*;
import javax.swing.*;

public class MainMenu extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public MainMenu() {

        setTitle("Tower Defense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel menuPanel = new JPanel(null);
        menuPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        menuPanel.setBackground(Color.BLACK);

        // ===== ปุ่ม =====
        MenuButton playBtn = new MenuButton("PLAY", () -> {
            dispose();
            new GameFrame();
        });

        MenuButton quitBtn = new MenuButton("QUIT", () -> {
            System.exit(0);
        });

        // ===== จัดตำแหน่งกลางจอ (เหลือ 2 ปุ่ม) =====
        int btnWidth = 250;
        int btnHeight = 70;
        int spacing = 30;

        int totalHeight = (btnHeight * 2) + spacing;
        int startY = (HEIGHT - totalHeight) / 2;
        int centerX = (WIDTH - btnWidth) / 2;

        playBtn.setBounds(centerX, startY, btnWidth, btnHeight);
        quitBtn.setBounds(centerX, startY + btnHeight + spacing, btnWidth, btnHeight);

        menuPanel.add(playBtn);
        menuPanel.add(quitBtn);

        add(menuPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
