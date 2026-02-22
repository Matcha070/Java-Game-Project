package GameObject;

import java.awt.Graphics;

public abstract class GameObject {
    protected double x;
    protected double y;

    public GameObject(double x2, double y2){
        this.x = x2;
        this.y = y2;
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract void draw(Graphics g);
}
