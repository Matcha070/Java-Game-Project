package UI.PauseMenu;

import asset.AudioManager;
import java.awt.*;
import javax.swing.*;

public class Setting extends JFrame {

    private boolean muted = false;

    private JSlider bgmSlider;
    private JSlider sfxSlider;

    private float lastBgmVolume;
    private float lastSfxVolume;

    public Setting() {

        setTitle("Settings");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);
        add(panel);

        // ===== TITLE =====
        JLabel title = new JLabel("SETTINGS", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Tahoma", Font.BOLD, 40));
        title.setBounds(0, 60, 800, 50);
        panel.add(title);

        // ===== โหลดค่าปัจจุบัน =====
        lastBgmVolume = AudioManager.getBgmVolume();
        lastSfxVolume = AudioManager.getSfxVolume();

        // ===== BGM =====
        JLabel bgmLabel = new JLabel("BGM Volume");
        bgmLabel.setForeground(Color.WHITE);
        bgmLabel.setBounds(300, 180, 200, 30);
        panel.add(bgmLabel);

        bgmSlider = new JSlider(0, 100, (int)(lastBgmVolume * 100));
        bgmSlider.setBounds(300, 210, 200, 50);
        bgmSlider.setBackground(Color.BLACK);
        panel.add(bgmSlider);

        bgmSlider.addChangeListener(e -> {
            float value = bgmSlider.getValue() / 100f;
            AudioManager.setBgmVolume(value);
        });

        // ===== SFX =====
        JLabel sfxLabel = new JLabel("SFX Volume");
        sfxLabel.setForeground(Color.WHITE);
        sfxLabel.setBounds(300, 280, 200, 30);
        panel.add(sfxLabel);

        sfxSlider = new JSlider(0, 100, (int)(lastSfxVolume * 100));
        sfxSlider.setBounds(300, 310, 200, 50);
        sfxSlider.setBackground(Color.BLACK);
        panel.add(sfxSlider);

        sfxSlider.addChangeListener(e -> {
            float value = sfxSlider.getValue() / 100f;
            AudioManager.setSfxVolume(value);
        });

        // ===== MUTE BUTTON =====
        JButton muteButton = new JButton("Mute: OFF");
        muteButton.setBounds(300, 380, 200, 40);
        panel.add(muteButton);

        muteButton.addActionListener(e -> {

            muted = !muted;

            if (muted) {

                lastBgmVolume = AudioManager.getBgmVolume();
                lastSfxVolume = AudioManager.getSfxVolume();

                AudioManager.setBgmVolume(0f);
                AudioManager.setSfxVolume(0f);

                bgmSlider.setValue(0);
                sfxSlider.setValue(0);

                muteButton.setText("Mute: ON");

            } else {

                AudioManager.setBgmVolume(lastBgmVolume);
                AudioManager.setSfxVolume(lastSfxVolume);

                bgmSlider.setValue((int)(lastBgmVolume * 100));
                sfxSlider.setValue((int)(lastSfxVolume * 100));

                muteButton.setText("Mute: OFF");
            }
        });

        // ===== BACK BUTTON =====
        JButton backButton = new JButton("Back");
        backButton.setBounds(300, 450, 200, 40);
        panel.add(backButton);

        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}
