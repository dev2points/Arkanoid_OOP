package uet.arkanoid;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.concurrent.Task; // Import Task

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HandleInput {
    private static final Set<KeyCode> pressedKeys = new HashSet<>();

    public static void check_input(int num, Scene scene, GameController gamecontroller) {
        // Đảm bảo event handlers chỉ được set một lần
        if (scene.getOnKeyPressed() == null && scene.getOnKeyReleased() == null) {
            scene.setOnKeyPressed(e -> {
                synchronized (pressedKeys) { // Đồng bộ hóa truy cập vào pressedKeys
                    pressedKeys.add(e.getCode());
                }
            });
            scene.setOnKeyReleased(e -> {
                synchronized (pressedKeys) { // Đồng bộ hóa truy cập vào pressedKeys
                    pressedKeys.remove(e.getCode());
                }
            });
        }

        // Lấy trạng thái phím đã nhấn (cần đồng bộ hóa khi đọc)
        Set<KeyCode> currentPressedKeys;
        synchronized (pressedKeys) {
            currentPressedKeys = new HashSet<>(pressedKeys); // Tạo bản sao an toàn
        }

        // --- Logic điều khiển Paddle và Ball ---
        Paddle paddle = gamecontroller.getPaddle();
        BallManager ballManager = gamecontroller.getBallManager();

        if (num == 2) { // Người chơi thứ 2
            if (currentPressedKeys.contains(KeyCode.LEFT)) {
                paddle.moveLeft();
            } else if (currentPressedKeys.contains(KeyCode.RIGHT)) {
                paddle.moveRight();
            } else {
                paddle.stop();
            }
            if (currentPressedKeys.contains(KeyCode.UP)) {
                ballManager.launchBalls();
                synchronized (pressedKeys) { // Xóa phím UP sau khi xử lý
                    pressedKeys.remove(KeyCode.UP);
                }
            }
        } else if (num == 1) { // Người chơi thứ 1
            if (currentPressedKeys.contains(KeyCode.A)) {
                paddle.moveLeft();
            } else if (currentPressedKeys.contains(KeyCode.D)) {
                paddle.moveRight();
            } else {
                paddle.stop();
            }
            if (currentPressedKeys.contains(KeyCode.W)) {
                ballManager.launchBalls();
                synchronized (pressedKeys) { // Xóa phím W sau khi xử lý
                    pressedKeys.remove(KeyCode.W);
                }
            }
        }

        // Xử lý phím ESCAPE cho Pause/Menu
        if (currentPressedKeys.contains(KeyCode.ESCAPE)) {
            synchronized (pressedKeys) { // Xóa phím ESCAPE sau khi xử lý
                pressedKeys.remove(KeyCode.ESCAPE);
            }
            gamecontroller.PauseGame();
        }
    }

    public static void setOnCloseHandler(Stage stage, GameController gameController) {
        stage.setOnCloseRequest(event -> {
            if (!gameController.getIsplaying()) {
                // Để nó đóng bình thường nếu không đang chơi
                return;
            }
            event.consume(); // Ngăn đóng ngay lập tức

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Exit Game");
            confirmAlert.setHeaderText("Do you want to save your game before exiting?");
            confirmAlert.setContentText("Choose an option:");

            ButtonType noSaveButton = new ButtonType("Exit without Saving", ButtonBar.ButtonData.NO);
            ButtonType saveButton = new ButtonType("Save & Exit", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            confirmAlert.getButtonTypes().setAll(noSaveButton, saveButton, cancelButton);

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == saveButton) {
                    TextInputDialog nameDialog = new TextInputDialog();
                    nameDialog.setTitle("Save Game");
                    nameDialog.setHeaderText("Enter your name to save your game:");
                    nameDialog.setContentText("Name:");

                    nameDialog.showAndWait().ifPresent(name -> {
                        String finalName = name.trim();
                        if (finalName.isEmpty()) {
                            System.out.println("No name entered, game not saved.");
                            stage.close();
                        } else {
                            // --- Chạy tác vụ lưu trên luồng nền ---
                            Task<Void> saveTask = new Task<Void>() {
                                @Override
                                protected Void call() throws Exception {
                                    gameController.getUser().setName(finalName);
                                    SaveGame.saveGame(gameController.getGameMap(), gameController.getUser());
                                    SaveGame.saveScore(finalName, gameController.getUser().getScore());
                                    return null;
                                }
                            };

                            saveTask.setOnSucceeded(e -> {
                                System.out.println("Game saved for user: " + finalName);
                                stage.close(); // Đóng cửa sổ sau khi lưu thành công
                            });

                            saveTask.setOnFailed(e -> {
                                System.err.println("Error saving game: " + saveTask.getException().getMessage());
                                // Hiển thị thông báo lỗi nếu cần
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                errorAlert.setTitle("Save Error");
                                errorAlert.setHeaderText("Failed to save game!");
                                errorAlert.setContentText(
                                        "Please check console for details: " + saveTask.getException().getMessage());
                                errorAlert.showAndWait();
                                stage.close(); // Vẫn đóng cửa sổ
                            });

                            // Bắt đầu tác vụ trên một luồng riêng
                            new Thread(saveTask).start();
                        }
                    });

                } else if (response == noSaveButton) {
                    System.out.println("Exited without saving.");
                    stage.close();
                } else {
                    System.out.println("Exit canceled by user.");
                }
            });
        });
    }

    public static void clearKeys() {
        synchronized (pressedKeys) { // Đồng bộ hóa khi xóa
            pressedKeys.clear();
        }
    }
}