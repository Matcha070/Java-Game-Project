import java.awt.*;
import java.awt.geom.AffineTransform;

public class Tower implements DrawObj {
    int x, y;
    int size = 30;
    double angle = 0;
    int range = 200;

    int cooldown = 0;
    int fireRate = 30; 
    double bulletSpeed = 30.0;

    public Tower(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        AffineTransform old = g2.getTransform();

        g2.rotate(angle, x, y);
        g2.setColor(Color.RED);
        g2.fillRect(
            x - size / 2,
            y - size / 2,
            size,
            size
        );

        g2.setTransform(old);
    }

    public void update() {
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

        Bullet bullet = new Bullet(x, y, (int)bulletSpeed);
        bullet.vx = dirX * bullet.speed;
        bullet.vy = dirY * bullet.speed;

        return bullet;
    }

}