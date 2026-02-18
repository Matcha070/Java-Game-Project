package asset;

import java.net.URL;
import javax.sound.sampled.*;

public class AudioManager {

    private static float masterVolume = 0.8f; // 0.0 - 1.0


    // เล่นเสียง (SFX)

    public static void play(String path) {
        try {
            URL url = AudioManager.class.getResource(path);

            if (url == null) {
                System.out.println("Sound not found: " + path);
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            setVolume(clip, masterVolume);

            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // เล่น BGM แบบ loop

    private static Clip bgmClip;

    public static void playBGM(String path) {
        try {
            stopBGM();

            URL url = AudioManager.class.getResource(path);
            if (url == null) return;

            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(ais);

            setVolume(bgmClip, masterVolume);

            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopBGM() {
        if (bgmClip != null) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
        }
    }


    // Volume Control

    public static void setMasterVolume(float volume) {
        masterVolume = Math.max(0f, Math.min(1f, volume));
    }

    private static void setVolume(Clip clip, float volume) {
        try {
            FloatControl gain =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            float dB = (float) (Math.log(volume) / Math.log(10) * 20);
            gain.setValue(dB);
        } catch (Exception ignored) {}
    }
}
