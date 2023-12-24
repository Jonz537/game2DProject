package utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public enum SoundController {

    STEPS("running.wav");

    String filename;
    Clip clip;

    SoundController(String filename) {
        this.filename = filename;
        clip = loadSoundTrack(filename);
    }

    private Clip loadSoundTrack(String filename) {
        try {
            File audioFile = new File("./assets/sounds/" + filename);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();

            clip.open(audioInputStream);

            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //this has a problem
    public void play() {
        if (clip == null || clip.isRunning()) {
            return;
        }

        new Thread(() -> {
            clip.setFramePosition(0);
            clip.start();
        }).start();
    }
}
