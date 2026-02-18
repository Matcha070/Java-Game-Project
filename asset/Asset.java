package asset;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Asset {
    // ----------Map-----------
    public static BufferedImage GRASS;
    public static BufferedImage DIRT;

    // ---------Enemy----------
    public static BufferedImage SLIME;
    public static BufferedImage Tree1;
    public static BufferedImage Tree2;
    public static BufferedImage Tree3;

    // -----------UI------------
    public static BufferedImage[] TOWER_ICON = new BufferedImage[4];
    public static BufferedImage FILLHp;
    public static BufferedImage ENEMYHPBAR;
    public static BufferedImage ARROWTOGGLE;
    public static BufferedImage COIN_ICON;
    public static BufferedImage DELETE_ICON;

    public static void load() {
        try {
            // ----------Map-----------
            GRASS = ImageIO.read(Asset.class.getResource("/asset/map/Grass.png"));
            DIRT = ImageIO.read(Asset.class.getResource("/asset/map/Dirt.png"));
            // ---------Enemy----------
            SLIME = ImageIO.read(Asset.class.getResource("/asset/enemy/Slime.png"));
            Tree1 = ImageIO.read(Asset.class.getResource("/asset/enemy/Plant1.png"));
            Tree2 = ImageIO.read(Asset.class.getResource("/asset/enemy/Plant2.png"));
            Tree3 = ImageIO.read(Asset.class.getResource("/asset/enemy/Plant3.png"));

            // -----------UI------------
            TOWER_ICON[0] = ImageIO.read(Asset.class.getResource("/asset/tower/tower1.png"));
            TOWER_ICON[3] = ImageIO.read(Asset.class.getResource("/asset/tower/tower1.png"));

            FILLHp = ImageIO.read(Asset.class.getResource("/asset/Ui/fillHp.png"));
            ENEMYHPBAR = ImageIO.read(Asset.class.getResource("/asset/Ui/hpEnemyBar.png"));
            ARROWTOGGLE = ImageIO.read(Asset.class.getResource("/asset/Ui/arrow.png"));
            COIN_ICON = ImageIO.read(Asset.class.getResource("/asset/Ui/coinIcon.png"));
            DELETE_ICON = ImageIO.read(Asset.class.getResource("/asset/Ui/deletebin.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
