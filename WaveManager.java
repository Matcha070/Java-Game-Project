import java.util.*;

public class WaveManager {

    private List<Wave> waves = new ArrayList<>();
    private int currentWaveIndex = 0;

    public WaveManager() {
        createWaves();
    }

    private void createWaves() {
        waves.add(new Wave1());
        waves.add(new Wave2());
        // waves.add(new Wave3());
    }

    public void update(List<Enemy> enemies) {
        if (currentWaveIndex >= waves.size()) return;

        Wave current = waves.get(currentWaveIndex);
        current.update(enemies);

        if (current.isFinished() && enemies.isEmpty()) {
            currentWaveIndex++;
        }
    }

    public int getCurrentWaveNumber() {
        return currentWaveIndex + 1;
    }

    public boolean isGameClear() {
        return currentWaveIndex >= waves.size();
    }
}
