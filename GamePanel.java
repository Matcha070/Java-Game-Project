import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;


public class GamePanel extends JPanel {

    BufferedImage grass, path;

    public GamePanel() {

         try {
            grass = ImageIO.read(getClass().getResource("asset\\map\\Grass.png"));
            path = ImageIO.read(getClass().getResource("asset\\map\\Dirt.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(
                MapData.MAP[0].length * MapData.TILE_SIZE,
                MapData.MAP.length * MapData.TILE_SIZE
        ));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < MapData.MAP.length; row++) {
            for (int col = 0; col < MapData.MAP[0].length; col++) {

                BufferedImage img =
                    (MapData.MAP[row][col] == 1) ? path : grass;

                g.drawImage(
                    img,
                    col * MapData.TILE_SIZE,
                    row * MapData.TILE_SIZE,
                    MapData.TILE_SIZE,
                    MapData.TILE_SIZE,
                    null
                );

                // // วาดเส้นกริด (เอาออกได้)
                // g.setColor(Color.BLACK);
                // g.drawRect(
                //     col * MapData.TILE_SIZE,
                //     row * MapData.TILE_SIZE,
                //     MapData.TILE_SIZE,
                //     MapData.TILE_SIZE
                // );
            }
        }
    }

}
