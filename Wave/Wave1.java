package Wave;
import Character.Enemy.*;


public class Wave1 extends Wave {

    public Wave1() {
        spawnDelay = 60;

        for (int i = 0; i < 5; i++)  spawnQueue.add(new RegenEnemy());

        for (int i = 0; i < 5; i++)  spawnQueue.add(new TankyEnemy());

        for (int i = 0; i < 5; i++)  spawnQueue.add(new Slime());

        for (int i = 0; i < 5; i++)  spawnQueue.add(new SpeedyEnemy());


    }
}