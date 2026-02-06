import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;



public class GamePanel extends JPanel {

    BufferedImage grass, path;
    //Enemy enemy;
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Tower> towers = new ArrayList<>();
    Timer timer;
    
    

    public GamePanel() {

        Asset.load();
        MapData.buildPath();

        //Test Enemy
        //enemy = new Enemy();
        //Test
        Timer timers = new Timer(2000, e -> {
            enemies.add(new Enemy(100));
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

        grass = Asset.GRASS;
        path = Asset.DIRT;
        

        setPreferredSize(new Dimension(
                MapData.MAP[0].length * MapData.TILE_SIZE,
                MapData.MAP.length * MapData.TILE_SIZE
        ));


        //Put Tower
        addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {

            int mouseX = e.getX();
            int mouseY = e.getY();

            int col = mouseX / MapData.TILE_SIZE;
            int row = mouseY / MapData.TILE_SIZE;

            // กันคลิกนอก map
            if (row < 0 || row >= MapData.MAP.length ||
                col < 0 || col >= MapData.MAP[0].length) {
                return;
            }

            // วาง tower 0 = grass)
            if (MapData.MAP[row][col] == 0) {

                int centerX = col * MapData.TILE_SIZE + MapData.TILE_SIZE / 2;
                int centerY = row * MapData.TILE_SIZE + MapData.TILE_SIZE / 2;

                towers.add(new Tower(centerX, centerY));//new tower
            }
            }
        });
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
        for (Tower tower : towers) {
                    tower.draw(g);
                }

    }



}
