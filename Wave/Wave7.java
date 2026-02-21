package Wave;
import Character.Enemy.*;
import Character.Tower.PlayerStat;

public class Wave7 extends Wave {

    public Wave7() {
        spawnDelay = 45;
        // PlayerStat.towerCap = 12;

        // for (int i = 0; i < 8; i++)
        //     spawnQueue.add(new BerserkerEnemy());

        for (int i = 0; i < 10; i++)
            spawnQueue.add(new Slime());
    }
}