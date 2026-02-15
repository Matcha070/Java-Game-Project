package UI;

import java.awt.*;
import java.awt.image.BufferedImage;

import asset.Asset;

public class HitButton {

    Rectangle box;
    int id;
    BufferedImage icon;

    public HitButton(int id, int x, int y, int w, int h) {
        this.id = id;
        this.box = new Rectangle(x, y, w, h);
    }

    public boolean isClick(Point p, int offsetY) {
        return new Rectangle(box.x, box.y + offsetY, box.width, box.height).contains(p);
    }

    public int getId() {
        return id;
    }

    public void draw(Graphics g) {

        g.setColor(new Color(255, 255, 255, 40));
        g.fillRect(box.x, box.y, box.width, box.height);

        BufferedImage icon = null;

        if (Asset.TOWER_ICON != null && Asset.TOWER_ICON.length > 0) {
            if (id < Asset.TOWER_ICON.length && Asset.TOWER_ICON[id] != null) {
                icon = Asset.TOWER_ICON[id];
            } else {
                icon = Asset.TOWER_ICON[0];
            }
        }

        if (icon != null) {
            g.drawImage(icon, box.x + 16, box.y + 8, box.width - 32, box.height - 16, null);
        }
    }
}