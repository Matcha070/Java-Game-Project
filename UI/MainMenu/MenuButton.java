package UI.MainMenu;

import asset.Asset;
import asset.AudioManager;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class MenuButton extends JPanel {

    private String text;
    private boolean hover = false;
    private Runnable action; // สิ่งที่จะทำตอนกด

    public MenuButton(String text, Runnable action) {
        this.text = text;
        this.action = action;

        setPreferredSize(new Dimension(200, 60));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                AudioManager.playSFX(Asset.SFX_MENU_HOVER);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (action != null) {
                    AudioManager.playSFX(Asset.SFX_MENU_CLICK);
                    action.run();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // ===== Background =====
        if (hover) {
            g2.setColor(new Color(255, 100, 100));
        } else {
            g2.setColor(new Color(200, 50, 50));
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        // ===== Text =====
        g2.setFont(new Font("Tahoma", Font.BOLD, 24));
        FontMetrics fm = g2.getFontMetrics();

        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }
}

