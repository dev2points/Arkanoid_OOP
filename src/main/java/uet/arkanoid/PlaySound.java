package uet.arkanoid;

import java.nio.file.Paths;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PlaySound {
    protected static MediaPlayer bgPlayer;
    public static void soundEffect(String path) {
        String soundPath =  PlaySound.class.getResource(path).toExternalForm();

        AudioClip clip = new AudioClip(soundPath);
        clip.play();
    }

    public static void soundBackground(String path) {
        String sbgrPath = PlaySound.class.getResource(path).toExternalForm();
        Media media = new Media(sbgrPath);
        bgPlayer = new MediaPlayer(media);
            bgPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgPlayer.setVolume(0.5); 
            bgPlayer.play();
    }

    // Dừng nhạc nền
    public static void stopBackground() {
        if (bgPlayer != null) {
            bgPlayer.stop();
        }
    }

   // Tạm dừng
    public static void pauseBackground() {
        if (bgPlayer != null) {
            bgPlayer.pause();
        }
    }

    // Tiếp tục phát
    public static void resumeBackground() {
        if (bgPlayer != null) {
            bgPlayer.play();
        }
    }
}
