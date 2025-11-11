package uet.arkanoid;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import uet.arkanoid.Powerups.Powerup;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML
    private Pane root;
    @FXML
    private Background background;
    private Label score;
    private GameMap gameMap;
    private Boss boss;
    List<Brick> bricks = new ArrayList<>();
    List<Powerup> powerups = new ArrayList<>();

    private Paddle paddle;
    private BallManager ballManager;
    private long lastUpdate = 0;
    private Scene scene;
    private User user;
    private AnimationTimer gameloop;
    private int currentMap;
    private Label scoreLabel;
    private Label livesLabel;
    private static boolean isPlaying = false;
    private int num = 2;
    private boolean isVictory = false;
    private boolean isMultiPlayer = false;
    private boolean isLoose = false;

    MenuManager menuManager;

    @FXML
    public void initialize() {
        root.requestFocus();
        root.setPrefWidth(Gameconfig.screen_width);
        root.setPrefHeight(Gameconfig.screen_height);
        user = new User(3, 0);
        currentMap = 0;
        isPlaying = true;
        // LevelLoader(currentMap + 1);
        // SaveGame.saveGame(GameController.this);
        Platform.runLater(() -> {
            Stage stage = (Stage) root.getScene().getWindow();
            HandleInput.setOnCloseHandler(stage, this);
        });
        init_lable();
        MainLoop();

    }

    public GameController() {
    }

    public GameController(int num, Pane pane, Scene scene) {
        this.scene = scene;
        this.root = pane;
        this.num = num;
        isMultiPlayer = true;
        user = new User(3, 0);
        currentMap = 1;
        init_lable();
        isPlaying = true;
        MainLoop();
    }

    private void LevelLoader(int level) {
        gameMap = new GameMap(level, root);
        background = gameMap.getBackground();
        paddle = gameMap.getPaddle();
        ballManager = gameMap.getBallManager();
        powerups.clear();
        if (level > Gameconfig.TOTAL_MAP)
            boss = gameMap.getBoss();
        else
            bricks = gameMap.getBricks();
        init_lable();
    }

    private void init_lable() {
        scoreLabel = new Label("Score: 0");
        scoreLabel.setLayoutX(20);
        scoreLabel.setLayoutY(10);
        scoreLabel.setStyle("""
            -fx-font-size: 24px;
            -fx-font-weight: bold;
            -fx-text-fill: linear-gradient(to right, #ff6600, #ffff00);
            -fx-effect: dropshadow(gaussian, rgba(255,120,0,0.8), 10, 0.5, 0, 0);
        """);


        livesLabel = new Label("Lives: 3");
        livesLabel.setLayoutX(20);
        livesLabel.setLayoutY(50);
        livesLabel.setStyle("""
            -fx-font-size: 24px;
            -fx-font-weight: bold;
            -fx-text-fill: linear-gradient(to right, #ff6600, #ffff00);
            -fx-effect: dropshadow(gaussian, rgba(255,120,0,0.8), 10, 0.5, 0, 0);
        """);

        root.getChildren().addAll(scoreLabel, livesLabel);
    }

    private void update_lable() {
        scoreLabel.setText("Score: " + user.getScore());
        livesLabel.setText("Lives: " + user.getHp());
        if (user.getHp() <= 0) {
            gameloop.stop();
            if (isMultiPlayer) {
                isLoose = true;
                return;
            }
            menuManager.displayDefeatMenu();

        }
        if (boss != null && boss.getHealthpoint() <= 0) {
            isVictory = true;
            gameloop.stop();
            if (isMultiPlayer)
                return;
            menuManager.displayVictoryMenu();
        }
    }

    public void loadProcess(Pair<GameMap, User> process) {
        gameMap = process.getKey();
        currentMap = gameMap.getLevel();
        // background = gameMap.getBackground();
        paddle = gameMap.getPaddle();
        ballManager = gameMap.getBallManager();
        powerups = gameMap.getPowerups();
        user = process.getValue();
        if (currentMap <= Gameconfig.TOTAL_MAP) {
            bricks = gameMap.getBricks();
            background = new Background(3, root);
        } else {
            boss = gameMap.getBoss();
            background = new Background(1, root);
        }
        restoreView();
        init_lable();
    }

    private void restoreView() {
        if (currentMap <= Gameconfig.TOTAL_MAP)
            for (Brick brick : bricks)
                brick.restoreFrame();
        else
            boss.restoreView();
        paddle.loadImage();
        Powerup.setGameController(GameController.this);
        for (Powerup powerup : powerups) {
            powerup.loadPowerup(powerup.loadImage());
        }
        ballManager.restoreView();

    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private void MainLoop() {
        gameloop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double deltaTime = (now - lastUpdate) / 1e9; // convert ns → seconds
                    BaseObject.setDeltatime(deltaTime);
                    if (scene != null) {
                        HandleInput.check_input(num, scene, GameController.this);
                    }
                    // Check va chạm
                    Collision.checkPaddleCollision(GameController.this);
                    if (currentMap > Gameconfig.TOTAL_MAP) {
                        Collision.checkBossCollision(GameController.this);
                        boss.update(deltaTime);
                    }

                    else
                        Collision.checkBrickCollision(GameController.this);
                    Collision.checkPowerUpCollision(paddle, powerups, GameController.this);
                    paddle.update(deltaTime);
                    ballManager.updateAll(deltaTime, GameController.this);
                    update_lable();
                }
                lastUpdate = now;
                if (bricks.isEmpty() && currentMap < Gameconfig.TOTAL_MAP) {
                    currentMap++;
                    LevelLoader(currentMap);
                    System.out.println("Load new map number " + currentMap);

                } else if (currentMap == Gameconfig.TOTAL_MAP) {
                    currentMap++;
                    LevelLoader(currentMap);
                    System.out.println("Boss level");
                }

            }
        };
        gameloop.start();
    }

    // public Pane getRoot() {
    //     return this.root;
    // }

    // public void setRoot(Pane root) {
    //     this.root = root;
    // }

    public Background getBackground() {
        return this.background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public int getScore() {
        return user.getScore();
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

    public boolean IsLoose() {
        return isLoose;
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
        if (isMultiPlayer)
            return;
        menuManager.displayPauseMenu();
    }

    public void ContinueGame() {
        gameloop.start();
    }

    public Boss getBoss() {
        return boss;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public BallManager getBallManager() {
        return this.ballManager;
    }

    public void setBallManager(BallManager ballManager) {
        this.ballManager = ballManager;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCurrentMap() {
        return this.currentMap;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }

    public Pane getPane() {
        return root;
    }

    public Label getScoreLabel() {
        return this.scoreLabel;
    }

    public void setScoreLabel(Label scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public Label getLivesLabel() {
        return this.livesLabel;
    }

    public void setLivesLabel(Label livesLabel) {
        this.livesLabel = livesLabel;
    }

    public Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

    public int getuserHP() {
        return user.getHp();
    }

    public static void setIsplaying(boolean b) {
        isPlaying = b;
    }

    public static boolean getIsplaying() {
        return isPlaying;
    }

    public boolean IsVictory() {
        return isVictory;
    }

    public void setFocus() {
        Platform.runLater(() -> {
            root.requestFocus();
        });
    }

    public void setMenumanager(MenuManager m) {
        menuManager = m;
    }
}