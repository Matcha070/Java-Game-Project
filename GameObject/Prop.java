package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Prop extends GameObject{

    private int width, height;
    private BufferedImage img;
    
    
    public Prop(int x, int y, int width, int height, BufferedImage img) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.img = img;
    }

    public void draw(Graphics g) {
        g.drawImage(img, (int) getX(), (int) getY(), width, height, null);
    }

    // ปรับจุดศูนย์กลางของ prop 
    public static Prop centered(int centerX, int centerY, int width, int height, BufferedImage img) {
        int topLeftX = centerX - width / 2;
        int topLeftY = centerY - height / 2;
        return new Prop(topLeftX, topLeftY, width, height, img);
    }
}