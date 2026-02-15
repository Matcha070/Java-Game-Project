package GameController;

import UI.TowerUI;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputController extends MouseAdapter {

    GamePanel game;
    TowerUI ui;

    public InputController(GamePanel game, TowerUI ui) {
        this.game = game;
        this.ui = ui;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        
        ui.handleClickToggle(e.getPoint());

        
        if (ui.isToggleClicked(e.getPoint())) {
            return;
        }

       
        if (ui.isOnUI(e.getPoint())) {

            if (game.getTowerCap() > 1) {
                ui.handleClickSelect(e.getPoint());
            }

            ui.handleClickDelete(e.getPoint());
            return;
        }

        
        game.handleClick(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (ui.getIsOpen()) {
            ui.handleHover(e.getPoint());
        }
        game.handleHover(e.getPoint());

        game.setMousePoint(e.getPoint());
    }
}
