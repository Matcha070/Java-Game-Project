package Wave;
import GameObject.Character.Enemy.*;

public class Wave4 extends Wave {

    public Wave4() {
        spawnDelay = 50;
        // PlayerStat.towerCap = 8;

        for (int i = 0; i < 15; i++)
            spawnQueue.add(new SpeedyEnemy());

        for (int i = 0; i < 10; i++)
            spawnQueue.add(new Slime());
    }
}