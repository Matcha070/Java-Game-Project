public class Wave2 extends Wave {

    public Wave2() {
        spawnDelay = 60;

        for (int i = 0; i < 10; i++)
            spawnQueue.add(new Slime());
    }
}