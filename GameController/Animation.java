package GameController;

import java.awt.image.BufferedImage;

import asset.SpriteSheet;

public class Animation {
    private SpriteSheet sheet;
    private int currentRow;

    private int currentFrame = 0;
    private int frameDelay;
    private int counter = 0;

    private boolean loop = true;
    private boolean finished = false;
    public boolean isRight = false;

    public Animation(SpriteSheet sheet, int row, int frameDelay, boolean loop) {
        this.sheet = sheet;
        this.currentRow = row;
        this.frameDelay = frameDelay;
        this.loop = loop;
    }

    public void update() {
        if (finished)
            return;

        counter++;

        if (counter >= frameDelay) {
            counter = 0;
            currentFrame++;
            if (currentFrame >= sheet.getFrameCount()) {
                if (loop) {
                    currentFrame = 0;
                } else {
                    currentFrame = sheet.getFrameCount() - 1;
                    finished = true;
                }
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return sheet.getFrame(currentRow, currentFrame);
    }

    public void reset() {
        finished = false;
        currentFrame = 0;
        counter = 0;
    }

    public boolean isFinished() {
        return finished;
    }

    public void flipX() {

    }

}
