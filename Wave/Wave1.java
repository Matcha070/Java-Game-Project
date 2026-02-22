package Wave;
import GameObject.Character.Enemy.*;
public class Wave1 extends Wave {

    public Wave1() {
        
        spawnDelay = 60;
        for (int i = 0; i < 15; i++)
            spawnQueue.add(new SpeedyEnemy());
        
    }
}