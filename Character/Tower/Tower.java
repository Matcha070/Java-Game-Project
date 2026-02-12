package Character.Tower;
import Character.Enemy.Enemy;
import GameController.Money;
import java.awt.*;

public abstract class Tower{
    protected int x, y;
    protected int size = 30;

    protected double angle = 0;
    protected int cooldown = 0;
    protected boolean hovered = false;
    protected int price;

    protected double bulletSpeed = 30.0;
    protected int range;
    protected int fireRate; 
    protected int damage;

    public Tower(int x, int y, int damage, int range, int fireRate, int price) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.price = price;
    }

    public abstract void draw(Graphics g);

    public abstract void drawGuide(Graphics g);

    public boolean contains(Point p) {
        Rectangle rect = new Rectangle(
            x - size / 2,
            y - size / 2,
            size,
            size
        );
        return rect.contains(p);
    }
    
    public void update() {
        Cooldown();
    }

    private void Cooldown() {
        if (cooldown > 0) cooldown--;
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
        cooldown = fireRate;
        
        // calculate lead
        Point enemyPos = enemy.getPosition();
        double ex = enemyPos.x;
        double ey = enemyPos.y;

        double evx = enemy.getVx();
        double evy = enemy.getVy();

        double dx = ex - x;
        double dy = ey - y;

        
        double distance = Math.sqrt(dx*dx + dy*dy);
        double time = distance / bulletSpeed;

        double futureX = ex + evx * time;
        double futureY = ey + evy * time;

        double dirX = futureX - x;
        double dirY = futureY - y;

        double len = Math.sqrt(dirX*dirX + dirY*dirY);
        dirX /= len;
        dirY /= len;

        Bullet bullet = new Bullet(x, y, (int)bulletSpeed, damage);
        bullet.vx = dirX * bullet.speed;
        bullet.vy = dirY * bullet.speed;

        return bullet;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public void place(Money money) {
        money.decreseAmount(price);
    }

}