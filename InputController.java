
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputController extends MouseAdapter {

    GamePanel game;
    towerUI ui;

    public InputController(GamePanel game, towerUI ui) {
        this.game = game;
        this.ui = ui;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        // 1. ถ้าคลิกโดน UI → ให้ UI จัดการ
        if (ui.isOnUI(e.getPoint())) {
            ui.handleClick(e.getPoint());
            return;
        }

        // 2. ถ้าไม่โดน UI → ส่งให้ Game
        game.handleClick(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        ui.handleHover(e.getPoint());
        game.handleHover(e.getPoint());
    }
}
