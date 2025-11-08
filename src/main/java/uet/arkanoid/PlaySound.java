package uet.arkanoid;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;

public class PlaySound {
    private static MediaPlayer bgPlayer;
    private static final HashMap<String, AudioClip> soundCache = new HashMap<>();

    // Phát hiệu ứng âm thanh (đã cache)
    public static void soundEffect(String path) {
        try {
            AudioClip clip = soundCache.get(path);
            if (clip == null) {
                String soundPath = PlaySound.class.getResource(path).toExternalForm();
                clip = new AudioClip(soundPath);
                soundCache.put(path, clip);
            }
            clip.play();
        } catch (Exception e) {
            System.err.println("⚠️ Lỗi phát âm thanh: " + path);
        }
    }

    // Phát nhạc nền lặp vô hạn
    public static void soundBackground(String path) {
        try {
            stopBackground(); // tránh chồng nhạc
            String sbgrPath = PlaySound.class.getResource(path).toExternalForm();
            Media media = new Media(sbgrPath);
            bgPlayer = new MediaPlayer(media);
            bgPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgPlayer.setVolume(0.5);
            bgPlayer.play();
        } catch (Exception e) {
            System.err.println("⚠️ Lỗi phát nhạc nền: " + path);
        }
    }

    public static void stopBackground() {
        if (bgPlayer != null) {
            bgPlayer.stop();
            bgPlayer.dispose(); // giải phóng tài nguyên
            bgPlayer = null;
        }
    }

    public static void pauseBackground() {
        if (bgPlayer != null) {
            bgPlayer.pause();
        }
    }

    public static void resumeBackground() {
        if (bgPlayer != null) {
            bgPlayer.play();
        }
    }
}
