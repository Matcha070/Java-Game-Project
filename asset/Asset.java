package asset;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

public class Asset {

//\\\\\\\\\\\\\\\\\\\\\\\\\\\PNG\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\//

    // ----------Map-----------
    public static BufferedImage GRASS;
    public static BufferedImage DIRT;

    // ---------Enemy----------
    public static BufferedImage SLIME;
    public static BufferedImage Tree1;
    public static BufferedImage Tree2;
    public static BufferedImage Tree3;

    // -----------UI------------
    public static BufferedImage[] TOWER_ICON = new BufferedImage[4];
    public static BufferedImage FILLHp;
    public static BufferedImage ENEMYHPBAR;
    public static BufferedImage ARROWTOGGLE;
    public static BufferedImage COIN_ICON;
    


//\\\\\\\\\\\\\\\\\\\\\\\\\\\SFX\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\//

    // ----------Tower-----------
    public static Clip SFX_BROKENTOWER;
    public static Clip SFX_FIRE;

    // ------------UI------------
    public static Clip SFX_MENU_HOVER;
    public static Clip SFX_MENU_CLICK;
    public static Clip SFX_CLICK;

    // ------------Wave------------
    public static Clip SFX_STARTING_WAVE;

    public static void load() {
        try {
            // ----------PNG-----------
            // ----------Map-----------
            GRASS = ImageIO.read(Asset.class.getResource("/asset/map/Grass.png"));
            DIRT = ImageIO.read(Asset.class.getResource("/asset/map/Dirt.png"));

            // ---------Enemy----------
            SLIME = ImageIO.read(Asset.class.getResource("/asset/enemy/Slime.png"));
            Tree1 = ImageIO.read(Asset.class.getResource("/asset/enemy/Plant1.png"));
            Tree2 = ImageIO.read(Asset.class.getResource("/asset/enemy/Plant2.png"));
            Tree3 = ImageIO.read(Asset.class.getResource("/asset/enemy/Plant3.png"));

            // -----------UI------------
            TOWER_ICON[0] = ImageIO.read(Asset.class.getResource("/asset/tower/tower1.png"));
            TOWER_ICON[3] = ImageIO.read(Asset.class.getResource("/asset/tower/tower1.png"));

            FILLHp = ImageIO.read(Asset.class.getResource("/asset/Ui/fillHp.png"));
            ENEMYHPBAR = ImageIO.read(Asset.class.getResource("/asset/Ui/hpEnemyBar.png"));
            ARROWTOGGLE = ImageIO.read(Asset.class.getResource("/asset/Ui/arrow.png"));
            COIN_ICON = ImageIO.read(Asset.class.getResource("/asset/Ui/coinIcon.png"));




            // ----------SFX------------
            // ----------Tower-----------
            SFX_BROKENTOWER = loadClip("/assetSFX/Tower/BrokenTower/TowerBreak.wav");
            SFX_FIRE = loadClip("/assetSFX/Tower/Fire/spell.wav");

            // ------------UI------------
            SFX_MENU_HOVER = loadClip("/assetSFX/UI/MenuHover.wav");
            SFX_MENU_CLICK = loadClip("/assetSFX/UI/MenuClick.wav");
            
            // ------------Wave------------
            SFX_STARTING_WAVE = loadClip("/assetSFX/Wave/StartingWave.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Clip loadClip(String path) throws Exception {
        var url = Asset.class.getResource(path);

        if (url == null) {
            throw new RuntimeException("Sound not found: " + path);
        }

        AudioInputStream ais = AudioSystem.getAudioInputStream(url);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        return clip;
    }


    public static void play(Clip clip) {//เล่นเสียง
        if (clip == null) return;
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }


}
