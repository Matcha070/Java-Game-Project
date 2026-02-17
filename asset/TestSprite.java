package asset;

import javax.swing.*;
import java.awt.*;

public class TestSprite extends JPanel {

    private SpriteSheet sheet;

    public TestSprite() {
        Asset.load();
        sheet = new SpriteSheet(Asset.Tree1, 64, 64); // ขนาดเฟรม
        setPreferredSize(new Dimension(1000, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int startX = 50;
        int startY = 50;
        int gap = 5;

        for (int row = 0; row < sheet.getRows(); row++) {
            for (int col = 0; col < sheet.getCols(); col++) {

                int x = startX + col * (sheet.getSpriteW() + gap);
                int y = startY + row * (sheet.getSpriteH() + gap);

                g.drawImage(sheet.getFrame(row, col), x, y, null);
                g.drawRect(x, y, sheet.getSpriteW(), sheet.getSpriteH());
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SpriteSheet Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TestSprite());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
