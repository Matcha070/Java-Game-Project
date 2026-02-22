package Wave;
import GameObject.Character.Enemy.*;
public class Wave1 extends Wave {

    public Wave1() {
        
        spawnDelay = 60;
        // spawnQueue.add(new BossEnemy());

        // for (int i = 0; i < 5; i++)
        //     spawnQueue.add(new ShieldEnemy());

        for (int i = 0; i < 8; i++)
            spawnQueue.add(new BerserkerEnemy());
        for (int i = 0; i < 15; i++)
            spawnQueue.add(new SpeedyEnemy());
        
    }
}