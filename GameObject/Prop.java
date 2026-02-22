package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Prop extends GameObject {

    private int width, height;
    private int baseOffset; // ระยะชดเชยพื้น
    private BufferedImage img;

    public Prop(double footX, double footY,
                int width, int height,
                int baseOffset,
                BufferedImage img) {

        super(footX, footY);
        this.width = width;
        this.height = height;
        this.baseOffset = baseOffset;
        this.img = img;
    }

    @Override
    public void draw(Graphics g) {

        int drawX = (int)(x - width / 2);
        int drawY = (int)(y - height + baseOffset);

        g.drawImage(img, drawX, drawY, width, height, null);
    }
}