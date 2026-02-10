public class PlayerStat {

    public static int MaxHP;
    public static int HP;
    public static int STARTERCOIN;
    public static int COIN;
    public static int WAVE;
    public static boolean GAMEOVER = false;

    public PlayerStat() {
        MaxHP = 100;
        HP = MaxHP;
        STARTERCOIN = 100;
        COIN = STARTERCOIN;
        WAVE = 0;
    }

    public int getHP() {
        return HP;
    }

    public int getCOIN() {
        return COIN;
    }

    public int getWAVE() {
        return WAVE;
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
        WAVE += 1;
    }
    
    
}
