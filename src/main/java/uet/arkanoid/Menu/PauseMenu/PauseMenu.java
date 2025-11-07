// package uet.arkanoid.Menu.PauseMenu;

// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
// import javafx.scene.layout.Pane;
// import java.io.IOException;

// public class PauseMenu {
//     private static Pane pane;
//     public static void Load(Pane pane1) {
//         pane = pane1;
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/uet/arkanoid/Menu/PauseMenu/pause_menu.fxml"));
//             pane = loader.load();
//             PauseController controller = loader.getController();
//             controller.setGameController(this);
//             this.pauseMenuLayer = pane;
//             root.getChildren().add(pauseMenuLayer);
//         } catch (IOException e) {
//             System.err.println("Không thể tải file menu.fxml. Hãy chắc chắn file tồn tại và đúng đường dẫn.");
//             e.printStackTrace();
//         }
//     }
// }