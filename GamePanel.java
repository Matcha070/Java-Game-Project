import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


public class GamePanel extends JPanel {

    BufferedImage grass, path;
    //Enemy enemy;
    ArrayList<Enemy> enemies = new ArrayList<>();
    Timer timer;
    
    

    public GamePanel() {

        MapData.buildPath();

        //Test Enemy
        //enemy = new Enemy();
        //Test
        Timer timers = new Timer(2000, e -> {
            enemies.add(new Enemy());
        });
        timers.start();


        
        timer = new Timer(16, e -> {
            for (int i = enemies.size() - 1; i >= 0; i--) {
                Enemy enemy = enemies.get(i);
                enemy.update();

                if (!enemy.isAlive()) {
                    enemies.remove(i);
                }
            }
            
            repaint();
        });

        timer.start();

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
                    (MapData.MAP[row][col] == 1 || MapData.MAP[row][col] == 2 || MapData.MAP[row][col] == 3) ? path : grass;

                g.drawImage(
                    img,
                    col * MapData.TILE_SIZE,
                    row * MapData.TILE_SIZE,
                    MapData.TILE_SIZE,
                    MapData.TILE_SIZE,
                    null
                );


                // //DeBug Path
                // g.setColor(Color.RED);
                // for (Point p : MapData.pathPoints) {
                //     g.fillOval(p.x - 4, p.y - 4, 8, 8);
                // }
            

                // if (enemy.isAlive()) {
                //     enemy.draw(g);
                // }
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

        for (Enemy enemy : enemies) {
                    enemy.draw(g);
                }
    }

}
