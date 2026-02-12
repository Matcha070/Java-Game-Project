package asset;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Asset {
    //----------Map-----------
    public static BufferedImage GRASS;
    public static BufferedImage DIRT;

    //---------Enemy----------
    public static BufferedImage SLIME;

    public static void load(){
        try {
            //----------Map-----------
            GRASS = ImageIO.read(Asset.class.getResource("/asset/map/Grass.png"));
            DIRT = ImageIO.read(Asset.class.getResource("/asset/map/Dirt.png"));
            //---------Enemy----------
            SLIME = ImageIO.read(Asset.class.getResource("/asset/enemy/Slime.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
