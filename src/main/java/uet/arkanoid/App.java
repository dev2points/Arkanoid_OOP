package uet.arkanoid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/menu/fxml/stack_pane.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Test Paddl11e");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}