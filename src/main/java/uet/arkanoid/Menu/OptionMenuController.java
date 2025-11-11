package uet.arkanoid.Menu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

import uet.arkanoid.PlaySound;

public class OptionMenuController extends BaseController{
    @FXML
    private Slider volumeSlider;

    @FXML
    private Label volumeLabel;

    @FXML
    public void initialize() {
        double currentVolume = PlaySound.getVolume() * 100;
        volumeSlider.setValue(currentVolume);
        updateVolumeLabel(currentVolume);

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateVolumeLabel(newVal.doubleValue());
            PlaySound.setVolume(newVal.doubleValue() / 100.0);
        });
    }

    private void updateVolumeLabel(double value) {
        volumeLabel.setText(String.format("Volume: %.0f%%", value));
    }

    public double getVolume() {
        return volumeSlider.getValue();
    }

    public void setVolume(double value) {
        volumeSlider.setValue(value);
    }

    @FXML
    private void returnHome(MouseEvent event) {
        menumanager.popMenu();
    }
}
