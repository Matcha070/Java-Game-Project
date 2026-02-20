package asset;

import java.net.URL;
import javax.sound.sampled.*;

public class AudioManager {

    private static Clip bgmClip;
    private static long bgmPausePosition = 0;
    private static boolean bgmPaused = false;

    private static float bgmVolume = 0.6f;
    private static float sfxVolume = 0.8f;

    // ---------------- BGM ----------------

    public static void playBGM(String path) {
        try {

            if (bgmClip != null) {
                bgmClip.stop();
                bgmClip.close();
            }

            URL url = AudioManager.class.getResource(path);
            if (url == null) {
                System.out.println("BGM not found: " + path);
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(ais);

            setVolume(bgmClip, bgmVolume);

            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
            bgmPaused = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pauseBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmPausePosition = bgmClip.getMicrosecondPosition();
            bgmClip.stop();
            bgmPaused = true;
        }
    }

    public static void resumeBGM() {
        if (bgmClip != null && bgmPaused) {
            bgmClip.setMicrosecondPosition(bgmPausePosition);
            bgmClip.start();
            bgmPaused = false;
        }
    }

    public static void stopBGM() {
        if (bgmClip != null) {
            bgmClip.stop();
            bgmClip.setMicrosecondPosition(0);
            bgmPaused = false;
        }
    }

    // ---------------- SFX ----------------

    public static void playSFX(String path) {
        try {
            URL url = AudioManager.class.getResource(path);
            if (url == null) return;

            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            setVolume(clip, sfxVolume);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- Volume ----------------

    public static void setBgmVolume(float v) {
        bgmVolume = clamp(v);
        if (bgmClip != null) setVolume(bgmClip, bgmVolume);
    }

    public static float getBgmVolume() {
        return bgmVolume;
    }

    public static void setSfxVolume(float v) {
        sfxVolume = clamp(v);
    }

    public static float getSfxVolume() {
        return sfxVolume;
    }

    private static float clamp(float v) {
        return Math.max(0f, Math.min(1f, v));
    }

    private static void setVolume(Clip clip, float volume) {
        try {

            FloatControl gain =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            if (volume == 0f) {
                gain.setValue(gain.getMinimum()); // mute จริง 100%
            } else {
                float dB = (float) (20.0 * Math.log10(volume));
                gain.setValue(dB);
            }

        } catch (Exception e) {
            e.printStackTrace(); // อย่า ignore
        }
    }
}