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
import uet.arkanoid.Powerups.ExtendPaddlePowerup;
import uet.arkanoid.Powerups.ExtraHpPowerup;
import uet.arkanoid.Powerups.FireBallPowerup;
import uet.arkanoid.Powerups.MultiBallPowerup;
import uet.arkanoid.Powerups.ShrinkPaddlePowerup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameController {

    @FXML
    private Pane root;
    private Background background; // Không cần @FXML nếu không inject từ FXML
    private Label score; // Không cần @FXML nếu không inject từ FXML
    private GameMap gameMap;
    private Boss boss;
    List<Brick> bricks = new ArrayList<>();
    List<Powerup> powerups = new ArrayList<>();

    private Paddle paddle;
    private BallManager ballManager;
    private long lastUpdate = 0;
    private Scene scene;
    private User user;
    private static AnimationTimer gameUIRefreshTimer; // Timer chỉ để refresh UI
    private ScheduledExecutorService gameLogicExecutor; // Executor cho logic game
    private Parent pauseMenuLayer;
    private int currentMap;
    private Label scoreLabel;
    private Label livesLabel;
    private static boolean isPlaying = false;
    private int num = 2; // Player number
    private boolean isVictory = false;
    private boolean isMultiPlayer = false;
    private boolean isLoose = false;

    // Các biến trạng thái game có thể được truy cập từ luồng logic và UI
    // Cần đồng bộ hoặc chỉ thay đổi từ luồng logic và UI chỉ đọc

    @FXML
    public void initialize() {
        root.requestFocus();
        root.setPrefWidth(Gameconfig.screen_width);
        root.setPrefHeight(Gameconfig.screen_height);
        PlaySound.soundBackground("/assets/sound/backgroundSound.mp3");
        user = new User(3, 0);
        currentMap = 0; // Sẽ được tăng lên 1 trong LevelLoader đầu tiên
        isPlaying = true;

        Platform.runLater(() -> {
            Stage stage = (Stage) root.getScene().getWindow();
            HandleInput.setOnCloseHandler(stage, this);
            LevelLoader(currentMap + 1); // Load map đầu tiên
            init_lable(); // Khởi tạo lable sau khi LevelLoader
            startGameLoops(); // Bắt đầu các luồng game
        });
    }

    public GameController() {
    }

    public GameController(int num, Pane pane, Scene scene) {
        this.scene = scene;
        this.root = pane;
        this.num = num;
        isMultiPlayer = true;
        user = new User(3, 0);
        currentMap = 4; // Bắt đầu từ map 4 cho Multiplayer theo logic cũ
        isPlaying = true;

        // Multiplayer cần init_lable và startGameLoops ngay lập tức
        Platform.runLater(() -> {
            LevelLoader(currentMap + 1); // Load map đầu tiên
            init_lable();
            startGameLoops();
        });
    }

    // Phương thức để khởi tạo và bắt đầu các luồng game
    private void startGameLoops() {
        // --- Game Logic Thread ---
        gameLogicExecutor = Executors.newSingleThreadScheduledExecutor();
        gameLogicExecutor.scheduleAtFixedRate(() -> {
            if (!isPlaying) {
                // Không chạy logic game nếu game không đang chơi
                return;
            }

            if (lastUpdate == 0) {
                lastUpdate = System.nanoTime();
                return;
            }

            long now = System.nanoTime();
            double deltaTime = (now - lastUpdate) / 1e9; // convert ns → seconds
            BaseObject.setDeltatime(deltaTime);

            // --- Cập nhật trạng thái game trên luồng nền ---
            // Input sẽ được xử lý trên UI thread và cập nhật trạng thái paddle
            HandleInput.check_input(num, scene, GameController.this); // Vẫn gọi đây để cập nhật paddle.moveDir

            // Va chạm
            Collision.checkPaddleCollision(GameController.this);
            if (currentMap > Gameconfig.TOTAL_MAP) {
                Collision.checkBossCollision(GameController.this);
                if (boss != null)
                    boss.update(deltaTime);
            } else {
                Collision.checkBrickCollision(GameController.this);
            }
            Collision.checkPowerUpCollision(paddle, powerups, GameController.this);

            // Cập nhật vị trí và trạng thái
            paddle.update(deltaTime);
            ballManager.updateAll(deltaTime, GameController.this);
            for (Powerup p : powerups)
                p.update(); // Cập nhật vị trí powerup

            // Logic chuyển màn hình
            if (currentMap <= Gameconfig.TOTAL_MAP) { // Chỉ check bricks cho level thường
                if (bricks.isEmpty()) {
                    if (currentMap < Gameconfig.TOTAL_MAP) {
                        currentMap++;
                        Platform.runLater(() -> LevelLoader(currentMap)); // Tải level mới trên UI thread
                        System.out.println("Load new map number " + currentMap);
                    } else if (currentMap == Gameconfig.TOTAL_MAP) {
                        currentMap++; // Chuyển sang Boss level
                        Platform.runLater(() -> LevelLoader(currentMap)); // Tải level Boss trên UI thread
                        System.out.println("Boss level");
                    }
                }
            }
            // Check win/lose
            checkGameResult();

            lastUpdate = now;

        }, 0, (long) (1000 / Gameconfig.FPS), TimeUnit.MILLISECONDS);

        // --- UI Refresh Timer (chạy trên JavaFX Application Thread) ---
        gameUIRefreshTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Cập nhật UI dựa trên trạng thái game đã được tính toán bởi gameLogicExecutor
                update_lable(); // Cập nhật score và lives

                // Cập nhật vị trí các Node của game (paddle, balls, powerups, boss, energies)
                // Các đối tượng game đã tự cập nhật x, y trong luồng logic
                // Bây giờ chỉ cần đẩy các thay đổi đó lên UI
                // Kỹ thuật này giả định rằng các phương thức update() của đối tượng đã thay đổi
                // x, y
                // và các phương thức updateView() của chúng sẽ cập nhật Node trên UI Thread.
                if (paddle != null)
                    paddle.updateView();
                if (ballManager != null) {
                    for (Ball ball : ballManager.getBalls()) {
                        if (ball.getView() != null) {
                            ball.getView().setLayoutX(ball.getX());
                            ball.getView().setLayoutY(ball.getY());
                        }
                        // Cập nhật trails của ball (tự động trong ball.update())
                    }
                }
                if (boss != null && boss.getView() != null) {
                    boss.getView().setLayoutX(boss.getX());
                    boss.getView().setLayoutY(boss.getY());
                    for (Energy energy : boss.getEnergies()) {
                        if (energy.getView() != null) {
                            energy.getView().setLayoutX(energy.getX());
                            energy.getView().setLayoutY(energy.getY());
                        }
                    }
                    // Cập nhật thanh máu của Boss (chỉ làm khi có thay đổi)
                    // boss.updateHealthBar(); // Gọi trực tiếp từ UI thread
                }

                // Powerups
                for (Powerup p : powerups) {
                    if (p.getView() != null) {
                        p.getView().setLayoutX(p.getX());
                        p.getView().setLayoutY(p.getY());
                    }
                }

                // Bricks (chỉ update hình ảnh nếu bị phá)
                // bricks đã được xử lý xóa hoặc chuyển frame trong luồng logic
                // Ở đây, các brick đã bị destroy sẽ tự động biến mất khi view = null
            }
        };
        gameUIRefreshTimer.start(); // Bắt đầu UI refresh
    }

    // Cần phương thức để dừng các luồng khi game kết thúc hoặc thoát
    public void stopGameLoops() {
        if (gameLogicExecutor != null && !gameLogicExecutor.isShutdown()) {
            gameLogicExecutor.shutdownNow(); // Dừng ngay lập tức
            System.out.println("Game Logic Executor shutdown.");
        }
        if (gameUIRefreshTimer != null) {
            gameUIRefreshTimer.stop();
            System.out.println("Game UI Refresh Timer stopped.");
        }
    }

    private void LevelLoader(int level) {
        // Clear UI components cũ trước khi load map mới
        if (gameMap != null) {
            if (gameMap.getBackground() != null)
                gameMap.getBackground().destroy();
            if (gameMap.getPaddle() != null)
                gameMap.getPaddle().destroy();
            if (gameMap.getBallManager() != null)
                gameMap.getBallManager().reset(); // Clear balls
            if (gameMap.getBoss() != null)
                gameMap.getBoss().destroy();
            for (Brick brick : bricks)
                brick.destroy();
            for (Powerup p : powerups)
                p.destroy();
            powerups.clear(); // Xóa powerups từ list
            bricks.clear(); // Xóa bricks từ list
        }

        gameMap = new GameMap(level, root);
        background = gameMap.getBackground();
        paddle = gameMap.getPaddle();
        ballManager = gameMap.getBallManager();
        this.powerups = gameMap.getPowerups(); // Cập nhật list powerups của controller
        if (level > Gameconfig.TOTAL_MAP) {
            boss = gameMap.getBoss();
        } else {
            this.bricks = gameMap.getBricks(); // Cập nhật list bricks của controller
        }

        // Đặt lại GameController cho tất cả powerups mới
        Powerup.setGameController(this);
    }

    private void init_lable() {
        // Xóa các label cũ trước khi thêm mới
        root.getChildren().remove(scoreLabel);
        root.getChildren().remove(livesLabel);

        scoreLabel = new Label("Score: " + user.getScore());
        scoreLabel.setLayoutX(20);
        scoreLabel.setLayoutY(10);
        scoreLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18px;");

        livesLabel = new Label("Lives: " + user.getHp());
        livesLabel.setLayoutX(20);
        livesLabel.setLayoutY(50);
        livesLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18px;");

        root.getChildren().addAll(scoreLabel, livesLabel);
    }

    private void update_lable() {
        // Cập nhật UI trên JavaFX Application Thread
        scoreLabel.setText("Score: " + user.getScore());
        livesLabel.setText("Lives: " + user.getHp());
    }

    private void checkGameResult() {
        // Kiểm tra kết quả game trên luồng logic, sau đó xử lý UI trên
        // Platform.runLater
        boolean player1Win = isMultiPlayer && (ballManager.getBalls().isEmpty() && user.isDead()); // P1 thua nếu hết
                                                                                                   // bóng và HP
        boolean player2Win = isMultiPlayer && (ballManager.getBalls().isEmpty() && user.isDead()); // P2 thua nếu hết
                                                                                                   // bóng và HP

        if (user.getHp() <= 0) {
            isPlaying = false;
            stopGameLoops(); // Dừng các luồng game

            if (isMultiPlayer) {
                isLoose = true; // Báo hiệu thua cho Multiplayer
                return; // MultiplayerController sẽ xử lý
            }

            Platform.runLater(() -> {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/uet/arkanoid/Menu/VictoryMenu/victory_menu.fxml"));
                    Parent root1 = loader.load();
                    VictoryController victory = loader.getController();
                    victory.setScore(user.getScore());
                    Scene scene = new Scene(root1);
                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Game Over");
                    stage.show();
                    stage.centerOnScreen();
                } catch (IOException e) {
                    System.err.println("Error loading game over menu: " + e.getMessage());
                }
            });
        }

        if (boss != null && boss.getHealthpoint() <= 0) {
            isPlaying = false;
            stopGameLoops(); // Dừng các luồng game

            if (isMultiPlayer) {
                isVictory = true; // Báo hiệu thắng cho Multiplayer
                return; // MultiplayerController sẽ xử lý
            }

            Platform.runLater(() -> {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/uet/arkanoid/Menu/VictoryMenu/victory_menu.fxml"));
                    Parent root1 = loader.load();
                    VictoryController victory = loader.getController();
                    victory.setScore(user.getScore());
                    Scene scene = new Scene(root1);
                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Victory!");
                    stage.show();
                    stage.centerOnScreen();
                } catch (IOException e) {
                    System.err.println("Error loading victory menu: " + e.getMessage());
                }
            });
        }
    }

    public void loadProcess(Pair<GameMap, User> process) {
        // Dừng game hiện tại nếu có
        stopGameLoops();
        isPlaying = false;

        // Xóa các view cũ khỏi pane
        if (gameMap != null) {
            gameMap.getBackground().destroy();
            gameMap.getPaddle().destroy();
            gameMap.getBallManager().reset();
            if (gameMap.getBoss() != null)
                gameMap.getBoss().destroy();
            for (Brick brick : bricks)
                brick.destroy();
            for (Powerup p : powerups)
                p.destroy();
        }
        root.getChildren().clear(); // Clear tất cả các node cũ khỏi root pane

        gameMap = process.getKey();
        user = process.getValue();
        currentMap = gameMap.getLevel();

        // Khôi phục tất cả các thành phần UI từ gameMap đã load
        gameMap.restoreGameMap(root, this);
        this.background = gameMap.getBackground();
        this.paddle = gameMap.getPaddle();
        this.ballManager = gameMap.getBallManager();
        this.bricks = gameMap.getBricks();
        this.powerups = gameMap.getPowerups();
        this.boss = gameMap.getBoss();

        init_lable(); // Khởi tạo lại labels và thêm vào root

        isPlaying = true; // Đánh dấu game đang chơi
        startGameLoops(); // Bắt đầu lại các luồng game
        root.requestFocus(); // Đảm bảo rootPane có focus để nhận input
    }

    public void setScene(Scene scene) {
        this.scene = scene;
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
        isPlaying = false; // Tạm dừng game logic
        if (gameUIRefreshTimer != null)
            gameUIRefreshTimer.stop(); // Dừng UI refresh
        if (gameLogicExecutor != null && !gameLogicExecutor.isShutdown()) {
            gameLogicExecutor.shutdownNow(); // Dừng game logic thread
            // Cần khởi tạo lại executor khi resume
            gameLogicExecutor = null;
        }

        if (isMultiPlayer)
            return;
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
            isPlaying = true; // Tiếp tục game logic
            startGameLoops(); // Khởi động lại các luồng game
            root.requestFocus();
        }
    }

    // private void BacktoMain() { // Giữ nguyên hoặc xóa nếu không dùng
    // }

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

    public Parent getPauseMenuLayer() {
        return this.pauseMenuLayer;
    }

    public void setPauseMenuLayer(Parent pauseMenuLayer) {
        this.pauseMenuLayer = pauseMenuLayer;
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
}