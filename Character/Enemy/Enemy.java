package Character.Enemy;
import Character.Tower.PlayerStat;
import Map.MapData;
import java.awt.*;

public abstract  class Enemy{
    protected int size = MapData.TILE_SIZE;
    protected double speed ;

    protected double x, y;
    protected double vx, vy;
    protected int targetIndex = 0;

    protected int hp;
    protected int maxHp;
    protected boolean alive = true;

    public Enemy(int hp, double speed) {

        // Start ที่ จุด start
        Point start = MapData.pathPoints.get(0);
        this.x = start.x;
        this.y = start.y;

        this.hp = hp;
        this.maxHp = hp;
        this.speed = speed;
    }

    public void update() {
        EnemyOutOfRange();
        EnemyWalk();
    }

    private boolean EnemyOutOfRange() {
        if (targetIndex >= MapData.pathPoints.size()) {
            PlayerStat.takeDMG(hp);
            alive = false;
            return true;
        }
        return false;
    }

    private void EnemyWalk() {
        Point target = MapData.pathPoints.get(targetIndex);
        
        double dx = target.x - x;
        double dy = target.y - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < speed) { //กัน Case
            x = target.x;
            y = target.y;
            targetIndex++;
        } else { //Walk
            vx = (dx / dist) * speed;
            vy = (dy / dist) * speed;
            x += vx;
            y += vy;
        }
    }

    public abstract void draw(Graphics g);

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            alive = false;
        }
    }

    public int getSize() {
        return size;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public Point getPosition() {
        return new Point((int) x, (int) y);
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

}
