package uet.arkanoid.Menu;
import javafx.fxml.FXML;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class StartMenuController extends BaseController{

    @FXML
    private Pane newGameButton;
    @FXML
    private Pane loadGameButton;
    @FXML
    private Pane optionsButton;
    @FXML
    private Pane highScoreButton;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleNewGame(MouseEvent event) {
        menumanager.playNewGame();

        // try {

        //     // Root cho cả 2 người chơi
        //     Pane root = new Pane();
        //     // root.getChildren().addAll(player1Pane, player2Pane);

        //     // Tạo scene
        //     Scene scene = new Scene(root, Gameconfig.screen_width * 2,
        //             Gameconfig.screen_height);

        //     // Khởi tạo MultiplayerController
        //     MultiplayerController multiplayerController = new MultiplayerController(root,
        //             scene);
        //     // Lấy stage hiện tại
        //     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //     // root.setLayoutX(0);
        //     // root.setLayoutY(0);
        //     // root.setTranslateX(0);
        //     // root.setTranslateY(0);
        //     stage.setScene(scene);
        //     stage.show();
        //     stage.centerOnScreen();

        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }

    @FXML
    private void handleLoadGame(MouseEvent event) {
        menumanager.displayLoadGame();
    }

    @FXML
    private void handleOptions(MouseEvent event) {
        menumanager.displayOptionMenu();
    }

    @FXML
    private void handleHighScore(MouseEvent event) {
        menumanager.displayHighScore();
    }

}
