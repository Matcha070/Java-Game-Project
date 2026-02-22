package Wave;
import GameObject.Character.Enemy.*;

public class Wave5 extends Wave {

    public Wave5() {
        spawnDelay = 50;
        // PlayerStat.towerCap = 10;

        for (int i = 0; i < 15; i++)
            spawnQueue.add(new RegenEnemy());

        // for (int i = 0; i < 5; i++)
        //     spawnQueue.add(new SupportEnemy());
    }
}