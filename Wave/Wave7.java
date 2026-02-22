package Wave;
import GameObject.Character.Enemy.*;

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