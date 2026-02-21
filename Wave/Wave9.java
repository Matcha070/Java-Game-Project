package Wave;
import Character.Enemy.*;
import Character.Tower.PlayerStat;

public class Wave9 extends Wave {

    public Wave9() {
        spawnDelay = 40;
        PlayerStat.towerCap = 15;

        for (int i = 0; i < 20; i++)
            spawnQueue.add(new TankyEnemy());

        // for (int i = 0; i < 10; i++)
        //     spawnQueue.add(new BerserkerEnemy());
    }
}