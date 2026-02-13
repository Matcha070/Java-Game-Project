package GameController;
import Character.Enemy.Enemy;
import Character.Tower.BaseTower;
import Character.Tower.Bullet;
import Character.Tower.MagicTower;
import Character.Tower.SniperTower;
import Character.Tower.SpeedShootTower;
import Character.Tower.Tower;
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
    boolean delete = false;

    Point mousePoint = null;
    Timer timer;
    Money money;

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
        money = new Money();

        
        timer = new Timer(16, e -> {

            waveManager.update(enemies);

            for (Bullet bullet : bullets) {
                bullet.update();
            }

            for (int i = enemies.size() - 1; i >= 0; i--) {
                Enemy enemy = enemies.get(i);
                enemy.update();

                if (!enemy.isAlive()) {
                    enemy.moneyDrop(money);
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

    public void handleClick(Point p) {
        int col = p.x / MapData.TILE_SIZE;
        int row = p.y / MapData.TILE_SIZE;

        if (row < 0 || row >= MapData.MAP.length ||
            col < 0 || col >= MapData.MAP[0].length) return;

        if (towers.stream().anyMatch(t -> t.contains(p)) && delete == false) return;

        PutTower(col, row);

        DeleteTower(p);
    }

    private void PutTower(int col, int row) {
        if (MapData.MAP[row][col] == 0 && money.CheckMoney()) {
            int cx = col * MapData.TILE_SIZE + MapData.TILE_SIZE / 2;
            int cy = row * MapData.TILE_SIZE + MapData.TILE_SIZE / 2;
            if(id == 0)
            {
                Tower t = new BaseTower(cx, cy);
                t.place(money);
                towers.add(t);
            }
            else if(id == 1)
            {
                Tower t = new SpeedShootTower(cx, cy);
                t.place(money);
                towers.add(t);
            }
            else if(id == 2)
            {
                Tower t = new SniperTower(cx, cy);
                t.place(money);
                towers.add(t);
            }
            else if(id == 3)
            {
                Tower t = new MagicTower(cx, cy);
                t.place(money);
                towers.add(t);
                
            }
            else
            {
                System.out.println("no tower selected");
            }
            id = -1;
            System.out.println("Current money: " + money.getAmount());
        }
    }

    private void DeleteTower(Point p) {
        if (delete) {
            for (int i = towers.size() - 1; i >= 0; i--) {
                Tower t = towers.get(i);

                if (t.contains(p)) {

                    // คืนเงิน 50% (ถ้าต้องการ)
                    int refund = t.getPrice() / 2;
                    money.addAmount(refund);

                    towers.remove(i);

                    System.out.println("Tower deleted. Refund: " + refund);
                    System.out.println("Current money: " + money.getAmount());

                    delete = false; // ปิดโหมดลบ
                    return;
                }
            }
            return;
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

        GhostPreview(g);

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

    private void GhostPreview(Graphics g) {
        if (id != -1 && mousePoint != null) {

            int col = mousePoint.x / MapData.TILE_SIZE;
            int row = mousePoint.y / MapData.TILE_SIZE;

            if (row >= 0 && row < MapData.MAP.length &&
                col >= 0 && col < MapData.MAP[0].length &&
                MapData.MAP[row][col] == 0) {

                int cx = col * MapData.TILE_SIZE + MapData.TILE_SIZE / 2;
                int cy = row * MapData.TILE_SIZE + MapData.TILE_SIZE / 2;

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, 1f)); // โปร่งใส

                Tower preview = null;

                if (id == 0) preview = new BaseTower(cx, cy);
                else if (id == 1) preview = new SpeedShootTower(cx, cy);
                else if (id == 2) preview = new SniperTower(cx, cy);
                else if (id == 3) preview = new MagicTower(cx, cy);

                if (preview != null) {
                    //preview.draw(g);
                    preview.drawGuide(g); // ถ้าอยากให้เห็น range ด้วย
                }

                g2.dispose();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getCanDelete(){
        return this.delete;
    }

    public void setCanDelete(boolean candelete){
        this.delete = candelete;
    }

    public void setMousePoint(Point mPoint){
        this.mousePoint = mPoint;
    }
}
