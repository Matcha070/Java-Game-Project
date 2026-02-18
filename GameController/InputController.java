package GameController;

import UI.PauseMenu.PauseUI;
import UI.TowerUI;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputController extends MouseAdapter {

    GamePanel game;
    TowerUI towerUi;
    PauseUI pauseUI;

    public InputController(GamePanel game, TowerUI ui, PauseUI pauseUI) {
        this.game = game;
        this.towerUi = ui;
        this.pauseUI = pauseUI;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        
        towerUi.handleClickToggle(e.getPoint());

        
        if (towerUi.isToggleClicked(e.getPoint())) {
            return;
        }

       
        if (towerUi.isOnUI(e.getPoint())) {

            if (game.getTowerCap() > 1) {
                towerUi.handleClickSelect(e.getPoint());
            }

            towerUi.handleClickDelete(e.getPoint());
            return;
        }

        if(pauseUI.isOnUI(e.getPoint())){
            pauseUI.handleClick(e.getPoint());
            return;
        }

        
        game.handleClick(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (towerUi.getIsOpen()) {
            towerUi.handleHover(e.getPoint());
        }

        pauseUI.handleHover(e.getPoint());

        game.handleHover(e.getPoint());

        game.setMousePoint(e.getPoint());
    }
}
