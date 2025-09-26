package hellofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 760;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("frame.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
       
        primaryStage.setTitle("GameLOL");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        
    }


    public static void main(String[] args) {
        launch(args);
    }
}