package hellofx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.HashSet;
import java.util.Set;

public class Controller {

    @FXML
    private Rectangle MainBar;

    @FXML
    private AnchorPane root;

    private final double MOVE_SPEED = 5; // smaller = smoother
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    public void initialize() {
        Platform.runLater(() -> root.requestFocus());

        // Track pressed keys
        root.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        root.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void update() {
        double x = MainBar.getLayoutX();
        double y = MainBar.getLayoutY();

        if (pressedKeys.contains(KeyCode.LEFT) || pressedKeys.contains(KeyCode.A)) {
            x -= MOVE_SPEED;
        }
        if (pressedKeys.contains(KeyCode.RIGHT) || pressedKeys.contains(KeyCode.D)) {
            x += MOVE_SPEED;
        }
        if (pressedKeys.contains(KeyCode.UP) || pressedKeys.contains(KeyCode.W)) {
            y -= MOVE_SPEED;
        }
        if (pressedKeys.contains(KeyCode.DOWN) || pressedKeys.contains(KeyCode.S)) {
            y += MOVE_SPEED;
        }
        double maxX = root.getWidth() - MainBar.getWidth();
        double maxY = root.getHeight() - MainBar.getHeight();

        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > maxX) x = maxX;
        if (y > maxY) y = maxY;

        MainBar.setLayoutX(x);
        // MainBar.setLayoutY(y);
    }
}
