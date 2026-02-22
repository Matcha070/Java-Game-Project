package GameObject.Character.Tower;

import GameController.Money;
import GameObject.Bullet;
import GameObject.Character.Enemy.Enemy;
import GameObject.GameObject;
import asset.Asset;
import asset.AudioManager;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Tower extends GameObject{

    protected int size = 30;

    protected double angle = 0;
    protected int cooldown = 0;
    protected boolean hovered = false;
    protected int price;

    protected String name;
    protected String description;
    protected double bulletSpeed = 30.0;
    protected int range;
    protected int fireRate;
    protected int damage;
    protected int hp;
    protected int maxHp;
    protected int currentHp;
    protected int antiHeal = 0;

    public Tower(String name, int x, int y, int damage, int range, int fireRate, int price, int hp, String description) {
        super(x, y);
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.price = price;
        this.hp = hp;
        this.maxHp = hp;
        this.currentHp = hp;
        this.description = description;
    }

    public abstract void draw(Graphics g);

    public abstract void drawGuide(Graphics g);

    public abstract void ShowRange(Graphics2D g2);

    public boolean contains(Point p) {
        Rectangle rect = new Rectangle(
                (int) getX() - size / 2,
                (int) getY() - size / 2,
                size,
                size);
        return rect.contains(p);
    }

    public void update() {
        Cooldown();
    }

    private void Cooldown() {
        if (cooldown > 0)
            cooldown--;
    }

    public boolean canShoot() {
        return cooldown == 0;
    }

    public void setAngle(Enemy enemy) {
        Point enemyPos = enemy.getPosition();
        angle = Math.atan2(enemyPos.y - y, enemyPos.x - x);
    }

    public boolean isEnemyInRange(Enemy enemy) {
        // เช็คว่า enemy อยู่ในระยะยิงไหม
        Point enemyPos = enemy.getPosition();

        double dx = enemyPos.x - x;
        double dy = enemyPos.y - y;

        return (dx * dx + dy * dy) <= range * range;
    }

    public Bullet Shoot(Enemy enemy) {

        AudioManager.playSFX(Asset.SFX_FIRE);


        cooldown = fireRate;

        // calculate lead
        Point enemyPos = enemy.getPosition();
        double ex = enemyPos.x;
        double ey = enemyPos.y;

        double evx = enemy.getVx();
        double evy = enemy.getVy();

        double dx = ex - x;
        double dy = ey - y;

        double distance = Math.sqrt(dx * dx + dy * dy);
        double time = distance / bulletSpeed;

        double futureX = ex + evx * time;
        double futureY = ey + evy * time;

        double dirX = futureX - x;
        double dirY = futureY - y;

        double len = Math.sqrt(dirX * dirX + dirY * dirY);
        dirX /= len;
        dirY /= len;

        Bullet bullet = new Bullet(x , y , (int) bulletSpeed, damage);
        bullet.setVx(dirX * bullet.getSpeed());
        bullet.setVy(dirY * bullet.getSpeed());

        takeDamage();

        enemy.applyAntiHeal(antiHeal);

        return bullet;
    }

    protected void DrawTower(Graphics2D g2) {

        BufferedImage img = Asset.TOWER_ICON[3];
        if (img == null) return;

        int imgW = img.getWidth();
        int imgH = img.getHeight();

        int drawSize = 128;
        double scale = (double) drawSize / Math.max(imgW, imgH);

        int newW = (int)(imgW * scale);
        int newH = (int)(imgH * scale);

        int drawX = (int)(getX() - newW / 2);
        int drawY = (int)(getY() - newH); // วาดจากพื้นขึ้น

        g2.drawImage(img, drawX, drawY, newW, newH, null);
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public void place(Money money) {
        money.decreseAmount(price);
    }

    public int getPrice() {
        return this.price;
    }

    public String getName(){
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage(){
        return damage;
    }

    public int getRange(){
        return range;
    }

    public int getFirerate(){
        return fireRate;
    }

    public float getCurrentHp() {
        return this.currentHp;
    }

    public String getDescription(){
        return description;
    }

    private void takeDamage() {
        if (currentHp > 0) {
            currentHp--;
        }
    }

    public Enemy getFrontEnemy(java.util.List<Enemy> enemies) {

        Enemy bestTarget = null;
        int maxProgress = -1;

        for (Enemy enemy : enemies) {

            if (!enemy.isAlive()) continue;

            if (!isEnemyInRange(enemy)) continue;

            int progress = enemy.getTargetIndex();

            if (progress > maxProgress) {
                maxProgress = progress;
                bestTarget = enemy;
            }
        }

        return bestTarget;
    }

}