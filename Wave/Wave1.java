package Wave;
import Character.Enemy.*;
import Character.Tower.PlayerStat;

public class Wave1 extends Wave {

    public Wave1() {
        spawnDelay = 60;
        PlayerStat.towerCap = 6;

        for (int i = 0; i < 15; i++)
            spawnQueue.add(new SpeedyEnemy());
    }
}