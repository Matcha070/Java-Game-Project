package Wave;
import GameObject.Character.Enemy.Enemy;
import GameObject.Player.PlayerStat;
import asset.Asset;
import asset.AudioManager;
import java.util.*;

public class WaveManager {

    private List<Wave> waves = new ArrayList<>();
    private int currentWaveIndex = -1;

    private WaveState state = WaveState.SPAWNING;

    private double waitTimer = 0;
    private final double WAIT_TIME = 5.0; // วินาที

    public WaveManager() {
        createWaves();
    }

    private void createWaves() {
        waves.add(new Wave0());
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

    public void update(List<Enemy> enemies, double deltaTime) {

        if (currentWaveIndex >= waves.size()) {
            state = WaveState.FINISHED;
            return;
        }

        if (currentWaveIndex == -1) {
            PlayerStat.towerCap = 6;
            currentWaveIndex++;
        }

        switch (state) {

            case SPAWNING:

                Wave current = waves.get(currentWaveIndex);
                current.update(enemies);

                if (current.isFinished() && enemies.isEmpty()) {
                    state = WaveState.WAITING;
                    waitTimer = 0;
                }

                break;

            case WAITING:

                waitTimer += deltaTime;

                if (waitTimer >= WAIT_TIME) {
                    currentWaveIndex++;
                    increaseTowerCap(currentWaveIndex);
                    state = WaveState.SPAWNING;
                    AudioManager.playSFX(Asset.SFX_STARTING_WAVE);
                }

                break;

            case FINISHED:
                break;
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
            case 1:
                PlayerStat.towerCap = 6;
                break;
            case 3:
                PlayerStat.towerCap = 8;
                System.out.println("Wave up");
                break;
            case 5:
                PlayerStat.towerCap = 10;
                break;
            case 7:
                PlayerStat.towerCap = 12;
                break;
            case 9:
                PlayerStat.towerCap = 15;
                break;
            case 11:
                PlayerStat.towerCap = 18;
                break;
            default:
                break;
        }
    }


    public boolean isWaiting() {
        return state == WaveState.WAITING;
    }

    public double getRemainingTimeExact() {
        if (state == WaveState.WAITING) {
            return Math.max(0, WAIT_TIME - waitTimer);
        }
        return 0;
    }

    public int getRemainingTime() {
        return (int) Math.ceil(getRemainingTimeExact());
    }
}
