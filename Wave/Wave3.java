package Wave;
import Character.Enemy.*;
import Character.Tower.PlayerStat;

public class Wave3 extends Wave {

    public Wave3() {
        spawnDelay = 55;
        PlayerStat.towerCap = 8;

        for (int i = 0; i < 8; i++)
            spawnQueue.add(new TankyEnemy());

        // for (int i = 0; i < 5; i++)
        //     spawnQueue.add(new ShieldEnemy());
    }
        
}