package Wave;
import GameObject.Character.Enemy.*;

public class Wave6 extends Wave {

    public Wave6() {
        spawnDelay = 45;
        // PlayerStat.towerCap = 10;

        for (int i = 0; i < 15; i++)
            spawnQueue.add(new TankyEnemy());

        for (int i = 0; i < 10; i++)
            spawnQueue.add(new ShieldEnemy());
    }
}