import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Tower Defense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(new GamePanel());

        pack();          // ปรับขนาดตาม Panel
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
