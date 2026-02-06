import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Tower Defense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel gamePanel = new GamePanel();
        
        add(gamePanel);

        pack();          // ปรับขนาดตาม Panel
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
