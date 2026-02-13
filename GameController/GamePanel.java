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
    int towerCap = 10;

    Point mousePoint = null;
    Timer timer;
    Money money;
    Cursor deleteCursor;

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

            // destroy tower
            for(int i = towers.size() - 1; i >= 0; i--){
                Tower t = towers.get(i);
                if(t.getHp() <= 1){
                    System.out.println("kuy");
                    towers.remove(i);
                    towerCap++;

                    setCanDelete(false); // ปิดโหมดลบ
                }
            }

            repaint();
        });

        timer.start();

        setPreferredSize(new Dimension(
                MapData.MAP[0].length * MapData.TILE_SIZE,
                MapData.MAP.length * MapData.TILE_SIZE
        ));

        // import delete tower
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("asset/Ui/delete_cursor.png");

        deleteCursor = toolkit.createCustomCursor(
                image,
                new Point(16, 16),  // จุดกด (hotspot)
                "DeleteCursor"
        );

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
        if (MapData.MAP[row][col] == 0) {
            int cx = col * MapData.TILE_SIZE + MapData.TILE_SIZE / 2;
            int cy = row * MapData.TILE_SIZE + MapData.TILE_SIZE / 2;
            ArrayList<Tower> allTowers = new ArrayList<>(java.util.List.of(
                new BaseTower(cx, cy), new SpeedShootTower(cx, cy), 
                new SniperTower(cx, cy), new MagicTower(cx, cy)
            ));
            if(id == -1)
            {
                System.out.println("no tower selected");
                System.out.println(towerCap);
                return;
            }
            Tower t = allTowers.get(id);
            if(money.getAmount() >= t.getPrice()){
                towerCap--;
                t.place(money);
                towers.add(t);
            }
            else{
                System.out.println("Not enough money");
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
                    towerCap++;

                    setCanDelete(false); // ปิดโหมดลบ
                    
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

    public int getTowerCap(){
        return this.towerCap;
    }

    public boolean getCanDelete(){
        return this.delete;
    }

    public void setCanDelete(boolean candelete){

        this.delete = candelete;

        Cursor cursor;

        if (delete) {
            cursor = deleteCursor;
        } else {
            cursor = Cursor.getDefaultCursor();
        }

        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.setCursor(cursor);
        }
    }

    public void setMousePoint(Point mPoint){
        this.mousePoint = mPoint;
    }
}
