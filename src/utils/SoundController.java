package utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public enum SoundController {

    STEPS("steps.wav"),
    FIRE("fire.wav");

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

    public void play() {
        if (clip == null || clip.isRunning()) {
            return;
        }

        new Thread(() -> {
            clip.setFramePosition(0);
            clip.start();
        }).start();
    }

    public boolean isRunning() {
        return clip.isRunning();
    }

    public void stopSound() {
        clip.stop();
    }

    public void setVolume(int volume) {
        setVolume(clip, volume);
    }

    private static void setVolume(Clip clip, int volumePercent) {
        if (volumePercent < 0 || volumePercent > 100) {
            throw new IllegalArgumentException("Volume percentage must be between 0 and 100");
        }

        float minVolumeDB = -10.0f;
//        float minVolumeDB = -80.0f;
        float maxVolumeDB = 1.0206f;

        float volumeDB = (volumePercent / 100.0f) * (maxVolumeDB - minVolumeDB) + minVolumeDB;

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volumeDB);
    }

}
