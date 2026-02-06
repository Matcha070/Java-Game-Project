import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;



public class GamePanel extends JPanel{

    BufferedImage grass, path;
    //Enemy enemy;
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Tower> towers = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
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
            for (Bullet bullet : bullets) {
                bullet.update();
            }
            for (int i = enemies.size() - 1; i >= 0; i--) {
                Enemy enemy = enemies.get(i);
                enemy.update();

                if (!enemy.isAlive()) {
                    enemies.remove(i);
                }
            }
            for (Tower tower : towers) {

                Enemy target = null;
                tower.update();

                for (Enemy enemy : enemies) {
                    if (tower.isEnemyInRange(enemy)) {
                        target = enemy;
                        break; // เจอตัวแรกพอ
                    }
                }

                    if (target != null) {
                        tower.setAngle(target);
                        if(tower.canShoot()) {
                            bullets.add(tower.Shoot(target));
                        }
                    }
            }
            
            repaint();
        });

        timer.start();

        

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
                Tower tower = new Tower(centerX, centerY);
                towers.add(tower);//new tower
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
                    (MapData.MAP[row][col] == 1 || MapData.MAP[row][col] == 2 || MapData.MAP[row][col] == 3) ? Asset.DIRT : Asset.GRASS;

                g.drawImage(
                    img,
                    col * MapData.TILE_SIZE,
                    row * MapData.TILE_SIZE,
                    MapData.TILE_SIZE,
                    MapData.TILE_SIZE,
                    null
                );
            }
        }
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

        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        for (Tower tower : towers) {
            tower.draw(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g2.setColor(Color.YELLOW);
            g2.fillOval(tower.x - 200, tower.y - 200, 400, 400);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
