package uet.arkanoid;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane gamePane = new Pane();
        Scene scene = new Scene(gamePane, 400, 300);

        // BaseObject obj = new BaseObject(50, 50, 100, 100, gamePane);
        Brick brick = new Brick(10, 10, Gameconfig.width_brick, Gameconfig.height_brick, gamePane, 1);
        brick.update(gamePane);

        primaryStage.setTitle("GameLOL");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
