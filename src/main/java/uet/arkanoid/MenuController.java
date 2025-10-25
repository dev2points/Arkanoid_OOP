package uet.arkanoid;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox; // <-- Import VBox
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    // Thêm một biến để tham chiếu đến VBox gốc trong FXML
    @FXML
    private VBox rootPane; // Tên này phải khớp với fx:id trong menu.fxml

    @FXML
    private void handleStartButtonAction() {
        System.out.println("Nút 'Bắt đầu' đã được nhấn! Chuyển màn hình...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/maps/main.fxml"));
            Parent mainRoot = loader.load();

            // 2. Tạo một Scene mới với giao diện vừa tải
            Scene mainScene = new Scene(mainRoot);
            GameController controller = loader.getController();
            controller.setScene(mainScene); 
            // 3. Lấy Stage (cửa sổ) hiện tại.
            // Chúng ta lấy nó từ một thành phần bất kỳ trong scene cũ, ở đây là VBox gốc (rootPane).
            Stage currentStage = (Stage) rootPane.getScene().getWindow();
            
            // 4. Đặt Scene mới cho Stage
            currentStage.setScene(mainScene);

        } catch (IOException e) {
            System.err.println("Không thể tải file main.fxml!");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleHighScoresButtonAction() {
        System.out.println("Nút 'Bảng xếp hạng' đã được nhấn!");
    }

    @FXML
    private void handleExitButtonAction() {
        Platform.exit();
    }
}