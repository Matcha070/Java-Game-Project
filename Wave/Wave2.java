package Wave;
import Character.Enemy.*;
import Character.Tower.PlayerStat;
public class Wave2 extends Wave {

    public Wave2() {
        spawnDelay = 55;
        PlayerStat.towerCap = 6;

        for (int i = 0; i < 10; i++)
            spawnQueue.add(new RegenEnemy());

        for (int i = 0; i < 10; i++)
            spawnQueue.add(new SpeedyEnemy());
    }
}