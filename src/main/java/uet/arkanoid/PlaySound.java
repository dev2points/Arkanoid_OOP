package uet.arkanoid;

import java.nio.file.Paths;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PlaySound {
    protected MediaPlayer bgPlayer;
    public void soundEffect(String path) {
        String soundPath = Paths.get("assets", path).toUri().toString();

        AudioClip clip = new AudioClip(soundPath);
        clip.play();
    }

    public void soundBackground(String path) {
        String sbgrPath = Paths.get("assets", path).toUri().toString();
        Media media = new Media(sbgrPath);
        bgPlayer = new MediaPlayer(media);
            bgPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgPlayer.setVolume(0.5); 
            bgPlayer.play();
    }

    // Dừng nhạc nền
    public void stopBackground() {
        if (bgPlayer != null) {
            bgPlayer.stop();
        }
    }

   // Tạm dừng
    public void pauseBackground() {
        if (bgPlayer != null) {
            bgPlayer.pause();
        }
    }

    // Tiếp tục phát
    public void resumeBackground() {
        if (bgPlayer != null) {
            bgPlayer.play();
        }
    }
}
