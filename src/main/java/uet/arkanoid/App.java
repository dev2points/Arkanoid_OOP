package uet.arkanoid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/maps/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GameController controller = loader.getController();
        controller.setScene(scene); 
        stage.setScene(scene);
        stage.setTitle("Test Paddle");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}