package Wave;
import GameObject.Character.Enemy.*;

public class Wave9 extends Wave {

    public Wave9() {
        spawnDelay = 40;
        // PlayerStat.towerCap = 15;

        for (int i = 0; i < 20; i++)
            spawnQueue.add(new TankyEnemy());

        // for (int i = 0; i < 10; i++)
        //     spawnQueue.add(new BerserkerEnemy());
    }
}