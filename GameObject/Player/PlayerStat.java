package GameObject.Player;

public class PlayerStat {

    public static int MaxHP = 10;
    public static int towerCap ;
    public static int towers ;
    public static int HP = MaxHP;
    public static int STARTERCOIN;
    public static int currentWave;
    public static boolean GAMEOVER = false;

    public PlayerStat() {
        MaxHP = 10;
        HP = MaxHP;
        currentWave = 0;
        towers = 0;
    }

    public static void takeDMG(int DMGtaken) {
        HP -= DMGtaken;
        if (HP <= 0) {
            PlayerStat.HP = 0;
            GAMEOVER = true;
            System.out.println("Game Over");
        }
    }

}
