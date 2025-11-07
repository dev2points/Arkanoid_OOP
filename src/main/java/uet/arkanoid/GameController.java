package uet.arkanoid;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import uet.arkanoid.Menu.PauseMenu.PauseController;
import uet.arkanoid.Menu.VictoryMenu.VictoryController;
import uet.arkanoid.Powerups.Powerup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML
    private Pane root;
    @FXML
    private Background background;
    private Label score;
    private GameMap gameMap;
    List<Brick> bricks = new ArrayList<>();
    List<Powerup> powerups = new ArrayList<>();
    private Paddle paddle;
    private BallManager ballManager;
    private long lastUpdate = 0;
    private Scene scene;
    private User user;
    private static AnimationTimer gameloop;
    private Parent pauseMenuLayer;
    private int currentMap;
    private Label scoreLabel;
    private Label livesLabel;

    @FXML
    public void initialize() {
        root.requestFocus();
        root.setPrefWidth(Gameconfig.screen_width);
        root.setPrefHeight(Gameconfig.screen_height);
        PlaySound.soundBackground("/assets/sound/backgroundSound.mp3");
        user = new User(3, 0);
        currentMap = 2;
        LevelLoader(currentMap);
        // SaveGame.saveGame(GameController.this);
        Platform.runLater(() -> {
        Stage stage = (Stage) root.getScene().getWindow();

        // Now you can set close event handler
        HandleInput.setOnCloseHandler(stage, this);
    });
        init_lable();
        MainLoop();

    }
    // public void changeMap() {
    // Gameconfig.currentMap++;
    // if (Gameconfig.currentMap > Gameconfig.TOTAL_MAP) {
    // Gameconfig.currentMap = 1;
    // }
    // System.out.println("Switching to map: " + Gameconfig.currentMap);
    // root.getChildren().clear();
    // LevelLoader(Gameconfig.currentMap);
    // lastUpdate = 0;
    // MainLoop();
    // }

    private void LevelLoader(int level) {
        BaseObject.setRootPane(root);
        gameMap = new GameMap(level, root);
        background = gameMap.getBackground();
        paddle = gameMap.getPaddle();
        ballManager = gameMap.getBallManager();
        bricks = gameMap.getBricks();

    }

    private void init_lable() {
        scoreLabel = new Label("Score: 0");
        scoreLabel.setLayoutX(20);
        scoreLabel.setLayoutY(10);
        scoreLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18px;");

        livesLabel = new Label("Lives: 3");
        livesLabel.setLayoutX(20);
        livesLabel.setLayoutY(50);
        livesLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18px;");
        
        root.getChildren().addAll(scoreLabel, livesLabel);
    }

    private void update_lable() {
        scoreLabel.setText("Score: " + user.getScore());
        livesLabel.setText("Lives: " + user.getHp());
        if (user.getHp() <= 0) {
            gameloop.stop();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/uet/arkanoid/Menu/VictoryMenu/victory_menu.fxml"));
                Parent root1 = loader.load();
                VictoryController victory = loader.getController();
                victory.setScore(user.getScore());
                Scene scene = new Scene(root1);
                Stage stage = (Stage) root.getScene().getWindow();

                stage.setScene(scene);
                stage.setTitle("Test Paddle");
                stage.show();
            }
            catch (IOException e){
                System.out.println("loose");
            }
            
        }
    }

    public void loadProcess(Pair<GameMap, User> process) {
        gameMap = process.getKey();
        background = gameMap.getBackground();
        paddle = gameMap.getPaddle();
        ballManager = gameMap.getBallManager();
        bricks = gameMap.getBricks();
        user = process.getValue();
        currentMap = gameMap.getLevel();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private void MainLoop() {
        // loop, update, timer gi day viet vao day
        // Timer time = new Timer();
        gameloop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double deltaTime = (now - lastUpdate) / 1e9; // convert ns → seconds
                    BaseObject.setDeltatime(deltaTime);
                    if (scene != null) {
                        // HandleInput.check_input(paddle, ball, scene, GameController.this);
                        HandleInput.check_input(paddle, ballManager, scene, GameController.this);
                        HandleInput.testSaveGame(scene, GameController.this);
                    }
                    // Check va chạm
                    Collision.checkPaddleCollision(GameController.this);
                    Collision.checkBrickCollision(GameController.this);
                    Collision.checkPowerUpCollision(paddle, powerups, GameController.this);
                    paddle.update(deltaTime);
                    ballManager.updateAll(deltaTime, GameController.this);
                    update_lable();
                }
                lastUpdate = now;
                if (bricks.isEmpty()) {
                    currentMap++;
                    SaveGame.saveGame(GameController.this);
                    if (currentMap <= Gameconfig.TOTAL_MAP)
                        LevelLoader(currentMap);
                    else
                        System.out.println("Win game!");
                }
            }
        };
        gameloop.start();
    }

    public Pane getRoot() {
        return this.root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public Background getBackground() {
        return this.background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public Label getScore() {
        return this.score;
    }

    public void setScore(Label score) {
        this.score = score;
    }

    public List<Brick> getBricks() {
        return this.bricks;
    }

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
    }

    public List<Powerup> getPowerups() {
        return this.powerups;
    }

    public void setPowerups(List<Powerup> powerups) {
        this.powerups = powerups;
    }

    public User getUser() {
        return this.user;
    }

    public Paddle getPaddle() {
        return this.paddle;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public List<Ball> getBalls() {
        return ballManager.getBalls();
    }

    public void addBall(Ball ball) {
        ballManager.addBall(ball);
    }

    public long getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Scene getScene() {
        return this.scene;
    }

    public void addPowerup(Powerup powerup) {
        powerups.add(powerup);
    }

    public void deletePowerup(Powerup powerup) {
        powerups.remove(powerup);
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void PauseGame() {
        gameloop.stop();
        AnchorPane pane;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uet/arkanoid/Menu/PauseMenu/pause_menu.fxml"));
            pane = loader.load();
            PauseController controller = loader.getController();
            controller.setGameController(this);
            this.pauseMenuLayer = pane;
            root.getChildren().add(pauseMenuLayer);
        } catch (IOException e) {
            System.err.println("Không thể tải file menu.fxml. Hãy chắc chắn file tồn tại và đúng đường dẫn.");
            e.printStackTrace();
        }
    }

    public void ContinueGame() {
        if (pauseMenuLayer != null && root.getChildren().contains(pauseMenuLayer)) {
            root.getChildren().remove(pauseMenuLayer);
            pauseMenuLayer = null;
            gameloop.start();
            root.requestFocus();
        }
    }


    private void BacktoMain() {

    }

}
