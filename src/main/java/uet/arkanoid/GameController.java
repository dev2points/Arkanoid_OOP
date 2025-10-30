package uet.arkanoid;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import uet.arkanoid.Powerups.Powerup;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML
    private Pane root;
    @FXML
    private Background background;
    private Label score;
    List<Brick> bricks = new ArrayList<>();
    List<Powerup> powerups = new ArrayList<>();
    private Paddle paddle;
    private BallManager ballManager ;
    private long lastUpdate = 0;
    private Scene scene;
    private User user ;

    @FXML
    public void initialize() {
        root.requestFocus();
        root.setPrefWidth(Gameconfig.screen_width);
        root.setPrefHeight(Gameconfig.screen_height);
        PlaySound.soundBackground("/assets/sound/backgroundSound.mp3");
        user = new User(3, 0); 
        LevelLoader(Gameconfig.currentMap);
        MainLoop();

    }
    // public void changeMap() {
    //     Gameconfig.currentMap++;
    //     if (Gameconfig.currentMap > Gameconfig.TOTAL_MAP) {
    //         Gameconfig.currentMap = 1;
    //     }
    //     System.out.println("Switching to map: " + Gameconfig.currentMap);
    //     root.getChildren().clear();
    //     LevelLoader(Gameconfig.currentMap);
    //     lastUpdate = 0;
    //     MainLoop();
    // }

    private void LevelLoader(int level) {
        BaseObject.setRootPane(root);
        background = new Background(3);
        paddle = new Paddle();
        ballManager = new BallManager(paddle);
        ballManager.addDefaultBall();
        bricks = ReadMapFile.readMapFXML(level, root);

    }
 
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private void MainLoop() {
        // loop, update, timer gi day viet vao day
        // Timer time = new Timer();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double deltaTime = (now - lastUpdate) / 1e9; // convert ns → seconds
                    BaseObject.setDeltatime(deltaTime);
                    if (scene != null) {
                        //HandleInput.check_input(paddle, ball, scene, GameController.this);
                        HandleInput.check_input(paddle, ballManager, scene);
                    }
                    // Check va chạm
                    Collision.checkPaddleCollision(GameController.this);
                    Collision.checkBrickCollision(GameController.this);
                    Collision.checkPowerUpCollision(paddle, powerups, GameController.this); 
                    paddle.update(deltaTime);
                    ballManager.updateAll(deltaTime, GameController.this);
                }
                lastUpdate = now;

            }
        }.start();
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
    public User getUser(){
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
    
    private void PauseGame() {

    }

    private void ContinueGame() {

    }

    private void BacktoMain() {

    }

}
