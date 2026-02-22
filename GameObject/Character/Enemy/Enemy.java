package GameObject.Character.Enemy;

import GameController.Money;
import GameObject.GameObject;
import GameObject.Player.PlayerStat;
import Map.MapData;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends GameObject{
    protected int size = MapData.TILE_SIZE;
    protected double speed;

    protected int antiHealTimer = 0; //att

    protected int regenAmount = 5; // ฟื้นต่อครั้ง
    protected int regenDelay = 60; // ทุกกี่ frame
    protected int regenTimer = 0;
    protected int dirX = 0; // -1 ซ้าย, 1 ขวา
    protected int dirY = 0; // -1 ขึ้น, 1 ลง

    protected double nodeOffsetX = 0;
    protected double nodeOffsetY = 0;

    protected double laneOffset = 0;
    protected double x, y;
    protected double vx, vy;
    protected int targetIndex = 0;

    protected int hp;
    protected int maxHp;
    protected boolean alive = true;
    protected boolean outOfRange = false;

    protected boolean showHpBar = false;

    protected int valueEnemy;

    protected List<Enemy> childrenToSpawn = new ArrayList<>();

    public Enemy(int hp, double speed, int valueEnemy) {

        // Start ที่ จุด start
        super(
            MapData.pathPoints.get(0).x,
            MapData.pathPoints.get(0).y
        );

        this.hp = hp;
        this.maxHp = hp;
        this.speed = speed;
        this.valueEnemy = valueEnemy;
    }

    public void update() {
        EnemyWalk();
        EnemyOutOfRange();

        if(antiHealTimer > 0){
            antiHealTimer--;
        }

        onUpdate();
    }

    private boolean EnemyOutOfRange() {
        if (targetIndex >= MapData.pathPoints.size()) {
            System.out.println("Take dmg");
            PlayerStat.takeDMG(hp);
            outOfRange = true;
            return true;
        }
        return false;
    }

    private void EnemyWalk() {
        Point target = MapData.pathPoints.get(targetIndex);

        double dx = (target.x + laneOffset) - x;
        double dy = (target.y + laneOffset) - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < speed) { // กัน Case
            x = target.x + nodeOffsetX;
            y = target.y + nodeOffsetY;
            targetIndex++;
        } else { // walk
            vx = (dx / dist) * speed;
            vy = (dy / dist) * speed;

            if (Math.abs(dx) > Math.abs(dy)) {
                dirX = dx > 0 ? 1 : -1;
                dirY = 0;
            } else {
                dirY = dy > 0 ? 1 : -1;
                dirX = 0;
            }

            x += vx;
            y += vy;
        }
    }

    public abstract void draw(Graphics g);

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            alive = false;
            onDeath();
        }
        showHpBar = true;
    }

    protected void DrawHpBar(Graphics g) {

        if(antiHealTimer > 0){
            g.setColor(Color.MAGENTA);
            g.drawOval((int)x-20,(int)y-20,40,40);
        }

        if (showHpBar) {
            g.setColor(Color.gray);
            g.fillRect(
                    (int) (x - size / 2) + 6,
                    (int) y - size / 2 - 10,
                    size - 12,
                    5);

            g.setColor(Color.RED);
            g.fillRect(
                    (int) (x - size / 2) + 6,
                    (int) y - size / 2 - 10,
                    (size - 12) * hp / maxHp,
                    5);
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

    protected void onUpdate() {
        //
    }

    protected void onDeath() {
        //
    }

    public boolean isOutOfRange(){
        return outOfRange;
    }

    public void moneyDrop(Money money) {
        money.increseAmount(valueEnemy);
    }

    public List<Enemy> getChildrenToSpawn() {
        List<Enemy> temp = new ArrayList<>(childrenToSpawn);
        childrenToSpawn.clear();
        return temp;
    }

    public void applyAntiHeal(int duration){
        antiHealTimer = duration;
}

}
