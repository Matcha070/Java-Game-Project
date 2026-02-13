package Character.Tower;
public class PlayerStat {

    public static int MaxHP;
    public static int HP;
    public static int STARTERCOIN;
    public static int COIN;
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
}
