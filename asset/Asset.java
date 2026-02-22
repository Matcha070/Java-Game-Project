package asset;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.util.Map;
import java.util.HashMap;
import Map.MapData;

public class Asset {

//\\\\\\\\\\\\\\\\\\\\\\\\\\\PNG\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\//

    // ----------Map-----------
    public static BufferedImage GRASS;
    public static BufferedImage DIRT;

    // ----------Prop-----------
    public static BufferedImage ROCK1;
    public static BufferedImage ROCK2;
    public static BufferedImage ROCK3;
    public static BufferedImage ROCK4;
    public static BufferedImage ROCK5;
    public static BufferedImage ROCK6;

    public static BufferedImage TREE1;
    public static BufferedImage TREE2;
    public static BufferedImage TREE3;
    public static BufferedImage TREE4;
    public static BufferedImage TREE5;
    public static BufferedImage TREE6;

    public static BufferedImage RUIN1;
    public static BufferedImage RUIN2;
    public static BufferedImage RUIN3;
    public static BufferedImage RUIN4;
    public static BufferedImage RUIN5;
    public static BufferedImage RUIN6;
    public static BufferedImage RUIN7;
    public static BufferedImage RUIN8;
    public static BufferedImage RUIN9;
    public static BufferedImage RUIN10;
    public static BufferedImage RUIN11;
    public static BufferedImage RUIN12;
    public static BufferedImage RUIN13;
    

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
    

    // Prop info mapping: tile -> Prop image + size + baseOffset
    public static class PropInfo {
        public final BufferedImage img;
        public final int width;
        public final int height;
        public final int baseOffset;

        public PropInfo(BufferedImage img, int width, int height, int baseOffset) {
            this.img = img;
            this.width = width;
            this.height = height;
            this.baseOffset = baseOffset;
        }
    }

    public static final Map<Integer, PropInfo> PROP_INFO_BY_TILE = new HashMap<>();


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
            ROCK1 = ImageIO.read(Asset.class.getResource("/asset/Props/Rock/Rock2_1.png"));
            ROCK2= ImageIO.read(Asset.class.getResource("/asset/Props/Rock/Rock2_3.png"));
            ROCK3 = ImageIO.read(Asset.class.getResource("/asset/Props/Rock/Rock1_3.png"));
            ROCK4 = ImageIO.read(Asset.class.getResource("/asset/Props/Rock/Rock2_grass_shadow_dark3.png"));
            ROCK5 = ImageIO.read(Asset.class.getResource("/asset/Props/Rock/Rock6_grass_shadow_dark3.png"));
            ROCK6 = ImageIO.read(Asset.class.getResource("/asset/Props/Rock/Rock6_4.png"));

            TREE1 = ImageIO.read(Asset.class.getResource("/asset/Props/Tree/Moss_tree2.png"));
            TREE2 = ImageIO.read(Asset.class.getResource("/asset/Props/Tree/Broken_tree2.png"));
            TREE3 = ImageIO.read(Asset.class.getResource("/asset/Props/Tree/Flower_tree1.png"));
            TREE4 = ImageIO.read(Asset.class.getResource("/asset/Props/Tree/Broken_tree4.png"));
            TREE5 = ImageIO.read(Asset.class.getResource("/asset/Props/Tree/Autumn_tree2.png"));
            TREE6 = ImageIO.read(Asset.class.getResource("/asset/Props/Tree/Burned_tree1.png"));

            RUIN1 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Water_ruins2.png"));
            RUIN2 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Brown_ruins3.png"));
            RUIN3 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Brown_ruins5.png"));
            // populate prop info mapping (use MapData.TILE_SIZE so sizes stay consistent)
            PROP_INFO_BY_TILE.put(4, new PropInfo(TREE1, MapData.TILE_SIZE * 5, MapData.TILE_SIZE * 5, 90));
            PROP_INFO_BY_TILE.put(5, new PropInfo(ROCK1, MapData.TILE_SIZE, MapData.TILE_SIZE, 0));
            PROP_INFO_BY_TILE.put(6, new PropInfo(RUIN1, (int)(MapData.TILE_SIZE * 2.5), (int)(MapData.TILE_SIZE * 2.5), 10));
            PROP_INFO_BY_TILE.put(7, new PropInfo(RUIN2, (int)(MapData.TILE_SIZE * 1.5), (int)(MapData.TILE_SIZE * 1.5), 10));
            PROP_INFO_BY_TILE.put(8, new PropInfo(RUIN3, MapData.TILE_SIZE, MapData.TILE_SIZE, 0));
            PROP_INFO_BY_TILE.put(9, new PropInfo(TREE2, MapData.TILE_SIZE, MapData.TILE_SIZE, 0));
            PROP_INFO_BY_TILE.put(10, new PropInfo(TREE3, (int)(MapData.TILE_SIZE * 2.15), (int)(MapData.TILE_SIZE * 2.15), 0));
            PROP_INFO_BY_TILE.put(11, new PropInfo(TREE4, (int)(MapData.TILE_SIZE * 2.5), (int)(MapData.TILE_SIZE * 2.5), 40));
            PROP_INFO_BY_TILE.put(12, new PropInfo(ROCK3, MapData.TILE_SIZE, MapData.TILE_SIZE, 0));
            PROP_INFO_BY_TILE.put(13, new PropInfo(ROCK2, MapData.TILE_SIZE, MapData.TILE_SIZE, 0));
            RUIN4 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Brown_ruins1.png"));
            RUIN5 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Water_ruins3.png"));
            RUIN6 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Brown_ruins4.png"));
            RUIN7 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Blue-gray_ruins2.png"));
            RUIN8 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Blue-gray_ruins3.png"));
            RUIN9 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Blue-gray_ruins1.png"));
            RUIN10 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Water_ruins1.png"));
            RUIN11 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Water_ruins4.png"));
            RUIN12 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Water_ruins5.png"));
            RUIN13 = ImageIO.read(Asset.class.getResource("/asset/Props/ruins/Blue-gray_ruins4.png"));
            
            

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

    public static PropInfo getPropInfoByTile(int tile) {
        return PROP_INFO_BY_TILE.get(tile);
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

