package Character.Tower;

import Character.Enemy.Enemy;
import GameController.Money;
import asset.Asset;
import asset.AudioManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public abstract class Tower {
    protected int x, y;
    protected int size = 30;

    protected double angle = 0;
    protected int cooldown = 0;
    protected boolean hovered = false;
    protected int price;

    protected String name;
    protected double bulletSpeed = 30.0;
    protected int range;
    protected int fireRate;
    protected int damage;
    protected int hp;
    protected int maxHp;
    protected int currentHp;

    public Tower(String name, int x, int y, int damage, int range, int fireRate, int price, int hp) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.price = price;
        this.hp = hp;
        this.maxHp = hp;
        this.currentHp = hp;
    }

    public abstract void draw(Graphics g);

    public abstract void drawGuide(Graphics g);

    public abstract void ShowRange(Graphics2D g2);

    public boolean contains(Point p) {
        Rectangle rect = new Rectangle(
                x - size / 2,
                y - size / 2,
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

        Bullet bullet = new Bullet(x, y, (int) bulletSpeed, damage);
        bullet.vx = dirX * bullet.speed;
        bullet.vy = dirY * bullet.speed;

        takeDamage();

        return bullet;
    }

    protected void DrawTower(Graphics2D g2) {

        if (Asset.TOWER_ICON[3] != null) {

            BufferedImage img = Asset.TOWER_ICON[3];

            int imgW = img.getWidth();
            int imgH = img.getHeight();

            int drawSize = 128;
            double scale = (double) drawSize / Math.max(imgW, imgH);

            int newW = (int) (imgW * scale);
            int newH = (int) (imgH * scale);

            int offsetY = 45;
            int drawX = x - newW / 2;
            int drawY = y - newH / 2 - offsetY;

            float hpPercent = (float) currentHp / maxHp;

            if (hpPercent < 0.5f) {

                float darkFactor = hpPercent / 0.5f; // 1 → 0

                float[] scales = { darkFactor, darkFactor, darkFactor, 1f };
                float[] offsets = { 0f, 0f, 0f, 0f };

                RescaleOp op = new RescaleOp(scales, offsets, null);
                BufferedImage darkImg = op.filter(img, null);

                g2.drawImage(darkImg, drawX, drawY, newW, newH, null);

            } else {
                g2.drawImage(img, drawX, drawY, newW, newH, null);
            }
        }
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

    private void takeDamage() {
        if (currentHp > 0) {
            currentHp--;
        }
    }

    public int getY() {
        return this.y;
    }

}