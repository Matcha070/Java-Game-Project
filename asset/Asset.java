package asset;

import java.io.File;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Asset {
    // ----------Map-----------
    public static BufferedImage GRASS;
    public static BufferedImage DIRT;

    // ---------Enemy----------
    public static BufferedImage SLIME;

    // -----------UI------------
    public static BufferedImage[] TOWER_ICON = new BufferedImage[4];
    public static BufferedImage FILLHp;
    public static BufferedImage ENEMYHPBAR;
    public static BufferedImage ARROWTOGGLE;
    public static BufferedImage COIN_ICON;

    public static void load() {
        try {
            // ----------Map-----------
            GRASS = ImageIO.read(Asset.class.getResource("/asset/map/Grass.png"));
            DIRT = ImageIO.read(Asset.class.getResource("/asset/map/Dirt.png"));
            // ---------Enemy----------
            SLIME = ImageIO.read(Asset.class.getResource("/asset/enemy/Slime.png"));
            // -----------UI------------
            TOWER_ICON[0] = ImageIO.read(Asset.class.getResource("/asset/tower/tower1.png"));
            TOWER_ICON[3] = ImageIO.read(Asset.class.getResource("/asset/tower/tower1.png"));

            FILLHp = ImageIO.read(Asset.class.getResource("/asset/Ui/fillHp.png"));
            ENEMYHPBAR = ImageIO.read(Asset.class.getResource("/asset/Ui/hpEnemyBar.png"));
            ARROWTOGGLE = ImageIO.read(Asset.class.getResource("/asset/Ui/arrow.png"));
            COIN_ICON = ImageIO.read(Asset.class.getResource("/asset/Ui/coinIcon.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class SpriteSheet {
    private BufferedImage[][] sprites;
    private int rows, cols;

    public SpriteSheet(String path, int spritesW, int spritesH) {
        try {
            BufferedImage sheet = ImageIO.read(new File(path));

            cols = sheet.getWidth() / spritesW;
            rows = sheet.getHeight() / spritesH;

            sprites = new BufferedImage[cols][rows];

            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    sprites[y][x] = sheet.getSubimage(
                            x * spritesW,
                            y * spritesH,
                            spritesW,
                            spritesH);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getFrame(int row, int frame) {
        return sprites[row][frame];
    }

    public int getFrameCount() {
        return cols;
    }

}