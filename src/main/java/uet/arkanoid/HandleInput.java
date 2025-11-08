package uet.arkanoid;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HandleInput {
    private static final Set<KeyCode> pressedKeys = new HashSet<>();

    public static void check_input(Paddle paddle, BallManager ballManager, Scene scene, GameController gamecontroller) {
        if (scene.getOnKeyPressed() == null && scene.getOnKeyReleased() == null) {
            scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
            scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
        }

        // Di chuyển paddle
        if (pressedKeys.contains(KeyCode.LEFT)) {
            paddle.moveLeft();
        } else if (pressedKeys.contains(KeyCode.RIGHT)) {
            paddle.moveRight();
        } else {
            paddle.stop();
        }

        // Nhấn SPACE để bắn bóng
        if (pressedKeys.contains(KeyCode.SPACE)) {
            ballManager.launchBalls();
            pressedKeys.remove(KeyCode.SPACE);
        }

        if (pressedKeys.contains(KeyCode.ESCAPE)) {
            pressedKeys.remove(KeyCode.ESCAPE);
            gamecontroller.PauseGame();
        }
    }

    public static void testSaveGame(Scene scene, GameController gameController) {
        if (scene.getOnKeyPressed() == null && scene.getOnKeyReleased() == null) {
            scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
            scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
        }
        if (pressedKeys.contains(KeyCode.ENTER))
            SaveGame.saveGame(gameController);
        if (pressedKeys.contains(KeyCode.S))
            SaveGame.saveScore(gameController.getUser().getName(), gameController.getUser().getScore());
    }

    public static void setOnCloseHandler(Stage stage, GameController gameController) {
    stage.setOnCloseRequest(event -> {
        if (!GameController.getIsplaying()) {
            // Let it close normally if not playing (e.g., in pause menu, start menu)
            return;
        }
        event.consume(); // prevent immediate close

        // Step 1: Ask user if they want to save
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Exit Game");
        confirmAlert.setHeaderText("Do you want to save your game before exiting?");
        confirmAlert.setContentText("Choose an option:");

        // Define buttons in desired order
        
        ButtonType noSaveButton = new ButtonType("Exit without Saving", ButtonBar.ButtonData.NO);
        ButtonType saveButton = new ButtonType("Save & Exit", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirmAlert.getButtonTypes().setAll(noSaveButton, saveButton, cancelButton);

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == saveButton) {
                // Step 2: Ask for username
                TextInputDialog nameDialog = new TextInputDialog();
                nameDialog.setTitle("Save Game");
                nameDialog.setHeaderText("Enter your name to save your game:");
                nameDialog.setContentText("Name:");

                nameDialog.showAndWait().ifPresent(name -> {
                    if (name.trim().isEmpty()) {
                        System.out.println("No name entered, game not saved.");
                        stage.close();
                    } else {
                        gameController.getUser().setName(name);
                        SaveGame.saveGame(gameController);
                        SaveGame.saveScore(name, gameController.getUser().getScore());
                        System.out.println("Game saved for user: " + name);
                        stage.close();
                    }
                });

            } else if (response == noSaveButton) {
                // Exit without saving
                System.out.println("Exited without saving.");
                stage.close();

            } else {
                // Cancel
                System.out.println("Exit canceled by user.");
            }
        });
    });
}

}
