package asset;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage[][] sprites;
    private int rows, cols;
    private int spriteW, spriteH;

    public SpriteSheet(BufferedImage sheet, int spriteW, int spriteH) {

        this.spriteW = spriteW;
        this.spriteH = spriteH;

        cols = sheet.getWidth() / spriteW;
        rows = sheet.getHeight() / spriteH;

        sprites = new BufferedImage[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                sprites[y][x] = sheet.getSubimage(
                        x * spriteW,
                        y * spriteH,
                        spriteW,
                        spriteH);
            }
        }
    }

    public BufferedImage getFrame(int row, int col) {
        return sprites[row][col];
    }

    public int getFrameCount() {
        return cols; // จำนวน frame ต่อแถว
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getSpriteW() {
        return spriteW;
    }

    public int getSpriteH() {
        return spriteH;
    }
}
