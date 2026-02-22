package GameController;

import GameObject.Bullet;
import GameObject.Character.Enemy.Enemy;
import GameObject.Character.Tower.BaseTower;
import GameObject.Character.Tower.MagicTower;
import GameObject.Character.Tower.SniperTower;
import GameObject.Character.Tower.SpeedShootTower;
import GameObject.Character.Tower.Tower;
import GameObject.GameObject;
import GameObject.Player.PlayerStat;
import GameObject.Prop;
import Map.MapData;
import UI.PauseMenu.PauseUI;
import UI.TowerUI;
import Wave.WaveManager;
import asset.Asset;
import asset.AudioManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;

public class GamePanel extends JPanel {

    BufferedImage grass, path;
    // Enemy enemy;
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Tower> towers = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();

    int id = -1;
    public static boolean isSelectTower = false;
    public static boolean delete = false;
    boolean pause = false;
    boolean isOver = false;


    Point mousePoint = null;
    Timer timer;
    Money money;
    Cursor deleteCursor;
    TowerUI towerUi;
    PauseUI pauseUI;
    WaveManager waveManager;
    private String errorMessage = "";
    private int errorTimer = 0;


    private final Font FONT_24 = new Font("Tahoma", Font.BOLD, 24);
    private final Color BACK_COLOR = new Color(0, 0, 0, 120);

    // field ของ GamePanel
    ArrayList<Prop> props = new ArrayList<>();

    public GamePanel() {

        Asset.load();
        MapData.buildPath();
        loadMapProps();

        AudioManager.playBGM(Asset.BGM_WAVE);

        PlayerStat.HP = PlayerStat.MaxHP;
        isOver = false;

        // Test Enemy
        // enemy = new Enemy();
        // Test
        // Timer timers = new Timer(2000, e -> {
        // enemies.add(new Slime());
        // });
        // timers.start();
        waveManager = new WaveManager();
        money = new Money();

        timer = new Timer(16, e -> {

            if (!pause && !isOver) {
                waveManager.update(enemies);
                if (towerUi != null)
                    towerUi.update();

                for (Bullet bullet : bullets) {
                    bullet.update();
                }

                for (int i = enemies.size() - 1; i >= 0; i--) {
                    Enemy enemy = enemies.get(i);
                    enemy.update();

                    if (!enemy.isAlive()) {
                        enemy.moneyDrop(money);

                        enemies.addAll(enemy.getChildrenToSpawn());

                        enemies.remove(i);
                    } else if (enemy.isOutOfRange()) {
                        enemies.remove(i);
                    }
                }

                for (Tower tower : towers) {

                    tower.update();

                    Enemy target = null;
                    int maxProgress = -1;

                    for (Enemy enemy : enemies) {

                        if (!enemy.isAlive()) continue;
                        if (!tower.isEnemyInRange(enemy)) continue;

                        int progress = enemy.getTargetIndex();

                        if (progress > maxProgress) {
                            maxProgress = progress;
                            target = enemy;
                        }
                    }

                    if (target != null) {
                        tower.setAngle(target);
                        if (tower.canShoot()) {
                            bullets.add(tower.Shoot(target));
                        }
                    }
                }

                for (int i = bullets.size() - 1; i >= 0; i--) {
                    Bullet bullet = bullets.get(i);

                    for (int j = enemies.size() - 1; j >= 0; j--) {
                        Enemy enemy = enemies.get(j);

                        if (bullet.hitEnemy(enemy)) {
                            enemy.takeDamage(bullet.getDamage(),bullet.isPBulllet());
                            bullets.remove(i);
                            break;
                        }
                    }
                }

                // destroy tower
                for (int i = towers.size() - 1; i >= 0; i--) {
                    Tower t = towers.get(i);
                    if (t.getCurrentHp() <= 1) {
                        AudioManager.playSFX(Asset.SFX_BROKENTOWER);
                        towers.remove(i);
                        // Asset.play(Asset.SFX_BROKENTOWER);
                        PlayerStat.towers--;

                        setCanDelete(false); // ปิดโหมดลบ
                    }
                }
                if (errorTimer > 0) {
                    errorTimer--;
                }

            }

            if (PlayerStat.HP <= 0) {
                isOver = true;
            }
            repaint();
        });

        timer.start();

        setPreferredSize(new Dimension(
                MapData.MAP[0].length * MapData.TILE_SIZE,
                MapData.MAP.length * MapData.TILE_SIZE));

        // import delete tower
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("asset/Ui/delete_cursor.png");

        deleteCursor = toolkit.createCustomCursor(
                image,
                new Point(16, 16), // จุดกด (hotspot)
                "DeleteCursor");

        setFocusable(true);
        requestFocusInWindow();

    }

    private void loadMapProps() {

        for (int row = 0; row < MapData.MAP.length; row++) {
            for (int col = 0; col < MapData.MAP[0].length; col++) {

                int cx = col * MapData.TILE_SIZE + MapData.TILE_SIZE / 2;
                int footY = row * MapData.TILE_SIZE + MapData.TILE_SIZE;

                if (MapData.MAP[row][col] == 4) {

                    props.add(new Prop(
                        cx,
                        footY,
                        MapData.TILE_SIZE * 5,
                        MapData.TILE_SIZE * 5,
                        90,              // 🔥 ปรับค่านี้ให้พอดีพื้น
                        Asset.TREE1
                    ));
                }

                if (MapData.MAP[row][col] == 5) {

                    props.add(new Prop(
                        cx,
                        footY,
                        MapData.TILE_SIZE,
                        MapData.TILE_SIZE,
                        0,              
                        Asset.ROCK1
                    ));
                }
            }
        }
    }

    public void handleClick(Point p) {

        // ถ้า pause อยู่ ให้ UI จัดการก่อน
        if (pause) {
            pauseUI.handleClick(p);
            return;
        }
        int col = p.x / MapData.TILE_SIZE;
        int row = p.y / MapData.TILE_SIZE;
        System.out.println("Clicked at: (" + col + ", " + row + ")");

        if (row < 0 || row >= MapData.MAP.length ||
                col < 0 || col >= MapData.MAP[0].length)
            return;

        boolean hasTower = towers.stream().anyMatch(t ->
        (int)(t.getX() / MapData.TILE_SIZE) == col &&
        (int)((t.getY() - 1) / MapData.TILE_SIZE) == row
        );

        if (hasTower && !delete) return;
        
        PutTower(col, row);

        DeleteTower(p);
    }

    private void PutTower(int col, int row) {
        
        if (MapData.MAP[row][col] == 0) {

            int cx = col * MapData.TILE_SIZE + MapData.TILE_SIZE / 2;
            int footY = row * MapData.TILE_SIZE + MapData.TILE_SIZE;
            ArrayList<Tower> allTowers = new ArrayList<>(java.util.List.of(
                    new BaseTower(cx, footY), new SpeedShootTower(cx, footY),
                    new SniperTower(cx, footY), new MagicTower(cx, footY)));
            if (id == -1 || PlayerStat.towers >= PlayerStat.towerCap) {
                System.out.println("no tower selected");
                System.out.println(PlayerStat.towers + " / " + PlayerStat.towerCap);
                return;
            }
            Tower t = allTowers.get(id);
            if (money.getAmount() >= t.getPrice()) {
                PlayerStat.towers++;
                t.place(money);
                towers.add(t);
            } else {
                errorMessage = "Not enough money!";
                errorTimer = 60;
                System.out.println("Not enough money");
                System.out.println(PlayerStat.towers + " / " + PlayerStat.towerCap);
            }

            id = -1;
            System.out.println("Current money: " + money.getAmount());
            System.out.println(PlayerStat.towers + " / " + PlayerStat.towerCap);
        }
    }

    private void DeleteTower(Point p) {

        if (delete) {
            for (int i = towers.size() - 1; i >= 0; i--) {
                Tower t = towers.get(i);

                if (t.contains(p)) {

                    towers.remove(i);
                    PlayerStat.towers--;

                    setCanDelete(false); // ปิดโหมดลบ

                    return;
                }
            }
            setCanDelete(false);
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

        drawMap(g);

        List<GameObject> renderList = new ArrayList<>();
        renderList.addAll(towers);
        renderList.addAll(props);

        renderList.sort(Comparator.comparingDouble(GameObject::getY));

        for (GameObject obj : renderList) {
            obj.draw(g);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        GhostPreview(g);
        drawErrorMessage(g);

        if (pause) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 0, 120));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    

    private void drawErrorMessage(Graphics g) {
        if (errorTimer > 0) {

            g.setFont(FONT_24);
            g.setColor(Color.RED);
            g.setFont(new Font("Tahoma", Font.BOLD, 24));

            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(errorMessage);
            int textHeight = fm.getHeight();

            int paddingX = 20;
            int paddingY = 10;

            int boxWidth = textWidth + paddingX * 2;
            int boxHeight = textHeight + paddingY * 2;

            int tx = (getWidth() - textWidth) / 2;
            int ty = getHeight() - 180;

            int boxX = (getWidth() - boxWidth) / 2;
            int boxY = ty - fm.getAscent() - paddingY;

            // ===== วาดพื้นหลัง =====
            g.setColor(new Color(0, 0, 0, 180)); // ดำโปร่ง
            g.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

            // ===== วาดกรอบ =====
            g.setColor(Color.RED);
            ((Graphics2D) g).setStroke(new BasicStroke(3));
            g.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

            // ===== วาดข้อความ =====
            g.setColor(Color.RED);
            g.drawString(errorMessage, tx, ty);
        }
    }

    private void drawMap(Graphics g) {
        for (int row = 0; row < MapData.MAP.length; row++) {
            for (int col = 0; col < MapData.MAP[0].length; col++) {

                BufferedImage img =
                        (MapData.MAP[row][col] == 1 ||
                        MapData.MAP[row][col] == 2 ||
                        MapData.MAP[row][col] == 3)
                        ? Asset.DIRT
                        : Asset.GRASS;

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
                        AlphaComposite.SRC_OVER, 0.5f)); // โปร่งใส

                Tower preview = null;

                if (id == 0)
                    preview = new BaseTower(cx, cy);
                else if (id == 1)
                    preview = new SpeedShootTower(cx, cy);
                else if (id == 2)
                    preview = new SniperTower(cx, cy);
                else if (id == 3)
                    preview = new MagicTower(cx, cy);

                if (preview != null) {
                    // preview.draw(g);
                    preview.ShowRange(g2);
                    preview.drawGuide(g);
                }

                g2.dispose();
            }
        }
    }

    public void RestartGame() {

        new GameFrame();
        Window window = SwingUtilities.getWindowAncestor(this);

        if (window instanceof JFrame) {
            ((JFrame) window).dispose();
        }

    }

    public void togglePause() {
        pause = !pause;

        if (pause) {
            AudioManager.pauseBGM();
        } else {
            AudioManager.resumeBGM();
        }

        repaint();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTowerCap() {
        return PlayerStat.towerCap;
    }

    public boolean getCanDelete() {
        return this.delete;
    }

    public void setTowerUI(TowerUI towerUi) {
        this.towerUi = towerUi;
    }

    public void setPauseUI(PauseUI pauseUI) {
        this.pauseUI = pauseUI;
    }

    public Money getMoney() {
        return money;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
        setCanDelete(!pause);
    }

    public void setCanDelete(boolean candelete) {

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

    public void setMousePoint(Point mPoint) {
        this.mousePoint = mPoint;
    }

    public boolean getGameOver() {
        return this.isOver;
    }

    public void setGameOver(boolean state) {
        isOver = state;
        setCanDelete(!state);
    }

    public boolean isOver() {
        return isOver;
    }

    public boolean getIsSelectTower() {
        return isSelectTower;
    }

    public void setIsSelectTower(boolean B) {
        isSelectTower = B;
    }
}

