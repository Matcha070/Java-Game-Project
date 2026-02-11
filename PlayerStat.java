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
        STARTERCOIN = 100;
        COIN = STARTERCOIN;
        currentWave = 0;
    }


    public static void takeDMG(int DMGtaken) {
        HP -= DMGtaken;
        if (HP <= 0) {
            GAMEOVER = true;
            System.out.println("Game Over");
        }
    }

    public void buyTOWER(int Price) {
        this.COIN -= Price;
    }

    public void gotMoney(int Price) {
        this.COIN += Price;
    }

    public void nextWAVE() {
        currentWave += 1;
    }
    
    
}
