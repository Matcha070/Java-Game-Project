package asset;

import GameController.Animation;
import java.awt.image.BufferedImage;

public class EnemySheet {

    private SpriteSheet sheet;

    public EnemySheet(BufferedImage img, int spriteW, int spriteH) {
        sheet = new SpriteSheet(img, spriteW, spriteH);
    }

    public Animation createAnim(int row, int delay, boolean loop) {
        return new Animation(sheet, row, delay, loop);
    }
}
