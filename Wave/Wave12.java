package Wave;
import Character.Enemy.*;
import Character.Tower.PlayerStat;

public class Wave12 extends Wave {

    public Wave12() {
        spawnDelay = 30;
        PlayerStat.towerCap = 18;

        // for (int i = 0; i < 15; i++)
        //     spawnQueue.add(new ShieldEnemy());

        // for (int i = 0; i < 10; i++)
        //     spawnQueue.add(new BerserkerEnemy());

        for (int i = 0; i < 10; i++)
            spawnQueue.add(new Slime());

        // spawnQueue.add(new BossEnemy());
    }
}