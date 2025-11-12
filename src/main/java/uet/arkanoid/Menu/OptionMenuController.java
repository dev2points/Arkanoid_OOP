package uet.arkanoid.Menu;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.arkanoid.PlaySound;

public class OptionMenuController extends BaseController{

    @FXML
    private Slider volumeSlider;

    @FXML
    private Label volumeLabel;

    @FXML
    private Slider volumeSlider1;

    @FXML
    private Label volumeLabel1;

    @FXML
    public void initialize() {
        // Lấy âm lượng hiện tại từ PlaySound (đang là 0.0 – 1.0)
        double currentVolume = PlaySound.getVolume() * 100;
        volumeSlider.setValue(currentVolume);
        updateVolumeLabel(currentVolume);

        double currentVolume1 = PlaySound.getEffectVolume() * 100;
        volumeSlider1.setValue(currentVolume1);
        updateVolumeLabel1(currentVolume1);

        // Khi kéo thanh trượt, cập nhật âm lượng nhạc nền
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateVolumeLabel(newVal.doubleValue());
            PlaySound.setVolume(newVal.doubleValue() / 100.0);
        });

        volumeSlider1.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateVolumeLabel1(newVal.doubleValue());
            PlaySound.setEffectVolume(newVal.doubleValue() / 100.0);
        });
    }

    private void updateVolumeLabel(double value) {
        volumeLabel.setText(String.format("Volume: %.0f%%", value));
    }

    private void updateVolumeLabel1(double value) {
        volumeLabel1.setText(String.format("Volume: %.0f%%", value));
    }

    public double getVolume() {
        return volumeSlider.getValue();
    }

    public void setVolume(double value) {
        volumeSlider.setValue(value);
    }

    public double getVolume1() {
        return volumeSlider1.getValue();
    }

    public void setVolume1(double value) {
        volumeSlider1.setValue(value);
    }

    @FXML
    private void returnHome(MouseEvent event) {
        menumanager.popMenu();
        PlaySound.soundEffect("/assets/sound/clickSound.mp3");
    }
}
