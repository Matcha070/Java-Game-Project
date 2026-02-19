package Character.Tower;

public class PlayerStat {

    public static int MaxHP = 100;
    public static int HP = MaxHP;
    public static int STARTERCOIN;
    public static int currentWave;
    public static boolean GAMEOVER = false;

    public PlayerStat() {
        MaxHP = 100;
        HP = MaxHP;
        currentWave = 0;
    }

    public static void takeDMG(int DMGtaken) {
        HP -= DMGtaken;
        if (HP <= 0) {
            GAMEOVER = true;
            System.out.println("Game Over");
        }
    }

    public int getHp() {
        return this.HP;
    }
}
