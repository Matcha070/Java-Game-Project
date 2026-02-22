package Wave;
import GameObject.Character.Enemy.*;

public class Wave10 extends Wave {

    public Wave10() {
        spawnDelay = 35;
        // PlayerStat.towerCap = 15;

        // for (int i = 0; i < 15; i++)
        //     spawnQueue.add(new ShieldEnemy());

        for (int i = 0; i < 20; i++)
            spawnQueue.add(new SpeedyEnemy());

        for (int i = 0; i < 15; i++)
            spawnQueue.add(new Slime());
    }
}