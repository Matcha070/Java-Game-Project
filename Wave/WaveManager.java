package Wave;
import Character.Enemy.Enemy;
import Character.Tower.PlayerStat;
import java.util.*;

public class WaveManager {

    private List<Wave> waves = new ArrayList<>();
    private int currentWaveIndex = -1;

    public WaveManager() {
        createWaves();
    }

    private void createWaves() {
        waves.add(new Wave1());
        waves.add(new Wave2());
        waves.add(new Wave4());
        waves.add(new Wave5());
        waves.add(new Wave6());
        waves.add(new Wave7());
        waves.add(new Wave8());
        waves.add(new Wave9());
        waves.add(new Wave10());
        waves.add(new Wave11());
        waves.add(new Wave12());
    }

    public void update(List<Enemy> enemies) {
        if (currentWaveIndex >= waves.size()) return;

        if (currentWaveIndex == -1){
            PlayerStat.towerCap = 6;
            currentWaveIndex++;
        }

        Wave current = waves.get(currentWaveIndex);
        current.update(enemies);
        
        if (current.isFinished() && enemies.isEmpty()) {
            System.out.println(currentWaveIndex);
            currentWaveIndex++;
            increaseTowerCap(currentWaveIndex);
        }

    }

    public int getCurrentWaveNumber() {
        return currentWaveIndex + 1;
    }

    public boolean isGameClear() {
        return currentWaveIndex >= waves.size();
    }

    public void Clear() {

        currentWaveIndex = 0;

        waves.clear();      // ลบ wave เก่าทั้งหมด
        createWaves();      // สร้างใหม่
    }

    private void increaseTowerCap(int waveIndex) {

        switch (waveIndex) {
            case 0:
                PlayerStat.towerCap = 6;
                break;
            case 2:
                PlayerStat.towerCap = 8;
                System.out.println("Wave up");
                break;
            case 4:
                PlayerStat.towerCap = 10;
                break;
            case 6:
                PlayerStat.towerCap = 12;
                break;
            case 8:
                PlayerStat.towerCap = 15;
                break;
            case 10:
                PlayerStat.towerCap = 18;
                break;
            default:
                break;
        }
    }
}
