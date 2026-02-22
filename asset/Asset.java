package asset;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

public class Asset {

//\\\\\\\\\\\\\\\\\\\\\\\\\\\PNG\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\//

    // ----------Map-----------
    public static BufferedImage GRASS;
    public static BufferedImage DIRT;
    // ----------Prop-----------
    public static BufferedImage ROCK1;
    public static BufferedImage ROCK2;
    public static BufferedImage ROCK3;
    public static BufferedImage TREE1;
    public static BufferedImage TREE2;
    public static BufferedImage TREE3;
    public static BufferedImage TREE4;
    public static BufferedImage RUIN1;
    public static BufferedImage RUIN2;
    public static BufferedImage RUIN3;
    

    // ---------Enemy----------
    public static BufferedImage SLIME;
    public static BufferedImage Tree1;
    public static BufferedImage Tree2;

    public static BufferedImage VAMPIRE;
    public static BufferedImage VAMPIRE3;


    public static BufferedImage Tree3;
    

    // -----------UI------------
    public static BufferedImage[] TOWER_ICON = new BufferedImage[4];
    public static BufferedImage FILLHp;
    public static BufferedImage PLAYERHp;
    public static BufferedImage ENEMYHPBAR;
    public static BufferedImage ARROWTOGGLE;
    public static BufferedImage COIN_ICON;
    public static BufferedImage DELETE_ICON;
    


//\\\\\\\\\\\\\\\\\\\\\\\\\\\SFX\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\//


    public static final String SFX_FIRE = "/assetSFX/Tower/Fire/spell.wav";

    public static final String SFX_BROKENTOWER = "/assetSFX/Tower/BrokenTower/TowerBreak.wav";

    public static final String SFX_MENU_CLICK = "/assetSFX/UI/MenuClick.wav";

    public static final String SFX_STARTING_WAVE = "/assetSFX/Wave/StartingWave.wav";

    public static final String BGM_MAIN = "/assetSFX/BGM/main.wav";

//\\\\\\\\\\\\\\\\\\\\\\\\\\\BGM\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\//

    public static final String BGM_WAVE = "/assetSFX/BGM/BGMWave.wav";

    public static void load() {
        AudioManager.setSfxVolume(0.06f);
        AudioManager.setBgmVolume(0.5f);
        try {
            // ----------PNG-----------
            // ----------Map-----------
            GRASS = ImageIO.read(Asset.class.getResource("/asset/map/Grass.png"));
            DIRT = ImageIO.read(Asset.class.getResource("/asset/map/Dirt.png"));

            // ----------Prop-----------
            ROCK1 = ImageIO.read(Asset.class.getResource("/asset/Props/Rock/Rock1_1.png"));
            ROCK2= ImageIO.read(Asset.class.getResource("/asset/Props/Rock/Rock1_2.png"));
            ROCK3 = ImageIO.read(Asset.class.getResource("/asset/Props/Rock/Rock1_3.png"));
            TREE1 = ImageIO.read(Asset.class.getResource("/asset/Props/Tree/Tree1.png"));
            TREE2 = ImageIO.read(Asset.class.getResource("/asset/Props/Tree/Broken_tree5.png"));
            TREE3 = ImageIO.read(Asset.class.getResource("/asset/Props/Tree/Tree2.png"));
            TREE4 = ImageIO.read(Asset.class.getResource("/asset/Props/Tree/Broken_tree4.png"));
            RUIN1 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Brown_ruins1.png"));
            RUIN2 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Brown_ruins3.png"));
            RUIN3 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Brown_ruins5.png"));
            
            

            // ---------Enemy----------
            SLIME = ImageIO.read(Asset.class.getResource("/asset/enemy/Slime1.png"));
            Tree1 = ImageIO.read(Asset.class.getResource("/asset/enemy/Plant1.png"));
            Tree2 = ImageIO.read(Asset.class.getResource("/asset/enemy/Plant2.png"));
            VAMPIRE = ImageIO.read(Asset.class.getResource("/asset/enemy/Vampires1.png"));
            VAMPIRE3 = ImageIO.read(Asset.class.getResource("/asset/enemy/Vampire3.png"));

            // -----------UI------------
            TOWER_ICON[0] = ImageIO.read(Asset.class.getResource("/asset/tower/tower1.png"));
            TOWER_ICON[3] = ImageIO.read(Asset.class.getResource("/asset/tower/tower1.png"));

            FILLHp = ImageIO.read(Asset.class.getResource("/asset/Ui/fillHp.png"));
            ENEMYHPBAR = ImageIO.read(Asset.class.getResource("/asset/Ui/hpEnemyBar.png"));
            ARROWTOGGLE = ImageIO.read(Asset.class.getResource("/asset/Ui/arrow.png"));
            COIN_ICON = ImageIO.read(Asset.class.getResource("/asset/Ui/coinIcon.png"));
            DELETE_ICON = ImageIO.read(Asset.class.getResource("/asset/Ui/deletebin.png"));
            PLAYERHp = ImageIO.read(Asset.class.getResource("/asset/Ui/heart.png"));

            
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


    public static void play(String path) {
        try {
            var url = Asset.class.getResource(path);

            if (url == null) {
                System.out.println("Sound not found: " + path);
                return;
            }

            AudioInputStream ais =
                AudioSystem.getAudioInputStream(url);

            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}

