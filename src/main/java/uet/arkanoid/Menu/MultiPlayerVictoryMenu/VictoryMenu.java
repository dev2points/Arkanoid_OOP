package uet.arkanoid.Menu.MultiPlayerVictoryMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class VictoryMenu extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("victory_menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Test Paddle");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}