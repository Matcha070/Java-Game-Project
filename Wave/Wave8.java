package Wave;
import Character.Enemy.*;
import Character.Tower.PlayerStat;

public class Wave8 extends Wave {

    public Wave8() {
        spawnDelay = 40;
        // PlayerStat.towerCap = 12;

        // for (int i = 0; i < 10; i++)
        //     spawnQueue.add(new SupportEnemy());

        for (int i = 0; i < 15; i++)
            spawnQueue.add(new RegenEnemy());

        for (int i = 0; i < 15; i++)
            spawnQueue.add(new SpeedyEnemy());
    }
}