package GameController;
import Character.BaseTower;
import Character.Bullet;
import Character.Enemy;
import Character.MagicTower;
import Character.SniperTower;
import Character.SpeedShootTower;
import Character.Tower;
import Map.MapData;
import Wave.WaveManager;
import asset.Asset;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;



public class GamePanel extends JPanel{

    BufferedImage grass, path;
    //Enemy enemy;
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Tower> towers = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();

    int id = -1;

    Timer timer;

    public GamePanel() {

        Asset.load();
        MapData.buildPath();

        //Test Enemy
        //enemy = new Enemy();
        //Test
        // Timer timers = new Timer(2000, e -> {
        //     enemies.add(new Slime());
        // });
        // timers.start();
        WaveManager waveManager = new WaveManager();

        
        timer = new Timer(16, e -> {

            waveManager.update(enemies);

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
                // หาเป้าหมาย
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

            for (int i = bullets.size() - 1; i >= 0; i--) {
                Bullet bullet = bullets.get(i);

                for (int j = enemies.size() - 1; j >= 0; j--) {
                    Enemy enemy = enemies.get(j);

                    if (bullet.hitEnemy(enemy)) {
                        enemy.takeDamage(bullet.getDamage());
                        bullets.remove(i);
                        break;
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
    }

    //Put Tower
    public void handleClick(Point p) {
        int col = p.x / MapData.TILE_SIZE;
        int row = p.y / MapData.TILE_SIZE;

        if (row < 0 || row >= MapData.MAP.length ||
            col < 0 || col >= MapData.MAP[0].length) return;

        if (towers.stream().anyMatch(t -> t.contains(p))) return;

        if (MapData.MAP[row][col] == 0) {
            int cx = col * MapData.TILE_SIZE + MapData.TILE_SIZE / 2;
            int cy = row * MapData.TILE_SIZE + MapData.TILE_SIZE / 2;
            if(id == 0)
            {
                towers.add(new BaseTower(cx, cy));
                id = -1;
            }
            else if(id == 1)
            {
                towers.add(new SpeedShootTower(cx, cy));
                id = -1;
            }
            else if(id == 2)
            {
                towers.add(new SniperTower(cx, cy));
                id = -1;
            }
            else if(id == 3)
            {
                towers.add(new MagicTower(cx, cy));
                id = -1;
            }
            else
            {
                System.out.println("no tower selected");
            }
        }
    }

    public void handleHover(Point p) {
        for (Tower t : towers) {
            t.setHovered(t.contains(p));
        }
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
            
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
