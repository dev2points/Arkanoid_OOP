package uet.arkanoid.Menu.StartMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ArkanoidMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));

            Scene scene = new Scene(root, 800, 600);
            
            primaryStage.setTitle("Arkanoid Menu");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            

        } catch (IOException e) {
            System.err.println("Không thể tải file menu.fxml. Hãy chắc chắn file tồn tại và đúng đường dẫn.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}