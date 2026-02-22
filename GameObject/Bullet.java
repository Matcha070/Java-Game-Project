package GameObject;

import GameObject.Character.Enemy.Enemy;
import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends GameObject{
    
    double vx, vy;
    int size = 12;
    double speed;
    int damage;

    public Bullet(double x, double y, double speed, int damage) {
        super(x, y);
        this.speed = speed;
        this.damage = damage;
    }

    public void update() {
        Move();
    }

    private void Move() {
        x += vx;
        y += vy;
    }

    @Override
    public void draw(Graphics g) {
        DrawBullet(g);
    }

    private void DrawBullet(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval((int)x - size/2, (int)y - size/2, size, size);
    }

    public boolean hitEnemy(Enemy e) {
        double dx = e.getPosition().x - x;
        double dy = e.getPosition().y - y;

        double r = (e.getSize() / 2.0) + (size / 2.0);
        return dx * dx + dy * dy <= r * r;
    }

    public int getDamage() {
        return damage;
    }

    public double getVx(){
        return vx;
    }

    public void setVx(double vx){
        this.vx = vx;
    }

    public double getVy(){
        return vy;
    }

    public void setVy(double vy){
        this.vy = vy;
    }

    public double getSpeed(){
        return this.speed;
    }
}
