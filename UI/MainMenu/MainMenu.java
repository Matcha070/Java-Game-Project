package UI.MainMenu;

import GameController.GameFrame;
import asset.Asset;
import java.awt.*;
import javax.swing.*;

public class MainMenu extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public MainMenu() {
        Asset.load();

        setTitle("Tower Defense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                if (Asset.MainMenuPic == null) return;

                int imgW = Asset.MainMenuPic.getWidth();
                int imgH = Asset.MainMenuPic.getHeight();
                int panelW = getWidth();
                int panelH = getHeight();

                // คำนวณ scale โดยรักษา aspect ratio (cover = เต็มจอไม่มีขอบดำ)
                double scale = Math.max((double) panelW / imgW, (double) panelH / imgH);

                int drawW = (int) (imgW * scale);
                int drawH = (int) (imgH * scale);

                // จัดให้อยู่กลาง
                int x = (panelW - drawW) / 2;
                int y = (panelH - drawH) / 2;

                g2.drawImage(Asset.MainMenuPic, x, y, drawW, drawH, null);
            }
        };
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        
        menuPanel.setLayout(null);
        menuPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

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
