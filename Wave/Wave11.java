package Wave;
import Character.Enemy.*;
import Character.Tower.PlayerStat;

public class Wave11 extends Wave {

    public Wave11() {
        spawnDelay = 35;
        PlayerStat.towerCap = 18;

        for (int i = 0; i < 15; i++)
            spawnQueue.add(new TankyEnemy());

        for (int i = 0; i < 15; i++)
            spawnQueue.add(new RegenEnemy());

        // for (int i = 0; i < 15; i++)
        //     spawnQueue.add(new SupportEnemy());
    }
}