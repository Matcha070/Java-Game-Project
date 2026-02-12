package Wave;
import Character.Slime;

public class Wave1 extends Wave {

    public Wave1() {
        spawnDelay = 60;

        for (int i = 0; i < 5; i++)
            spawnQueue.add(new Slime());
    }
}