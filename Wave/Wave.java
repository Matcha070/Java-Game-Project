package Wave;
import Character.Enemy.Enemy;
import java.util.*;


public abstract class Wave {

        
    protected Queue<Enemy> spawnQueue = new LinkedList<>();
    protected int spawnDelay = 60;
    protected int timer = 0;

    public void update(List<Enemy> enemies) {
        timer++;

        if (timer >= spawnDelay && !spawnQueue.isEmpty()) {
            enemies.add(spawnQueue.poll());
            timer = 0;
        }
    }

    public boolean isFinished() {
        return spawnQueue.isEmpty();
    }

}
