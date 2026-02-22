package Map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Prop {
    private int width, height;
    private BufferedImage img;
    private int x, y;
    
    
    public Prop(int x, int y, int width, int height, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    public static Prop centered(Graphics g, int x, int y, int width, int height, BufferedImage img) {
        int drawX = x - width / 2;
        int drawY = y - height / 2;
        return new Prop(drawX, drawY, width, height, img);
    }
}