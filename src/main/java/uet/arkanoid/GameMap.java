package uet.arkanoid;

import uet.arkanoid.Powerups.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;

public class GameMap implements Serializable {
    // private static final long serialVersionUID = 1L;

    private Background background;
    private Paddle paddle;
    private BallManager ballManager;
    private List<Brick> bricks = new ArrayList<>();
    private List<Powerup> powerups = new ArrayList<>();
    private int level;
    private Boss boss;

    // Constructor khi tạo mới map
    public GameMap(int level, Pane root) {
        this.level = level;
        this.background = new Background(1, root); // Hoặc dựa vào level
        this.paddle = new Paddle(root);
        this.ballManager = new BallManager(paddle, root);
        this.ballManager.addDefaultBall();
        if (level > Gameconfig.TOTAL_MAP)
            this.boss = new Boss(250, 50, 260, 260, root);
        else
            this.bricks = ReadMapFile.readMapFXML(level, root);
    }

    // Constructor cho việc deserialized
    public GameMap(Background background, Paddle paddle, BallManager ballManager, List<Brick> bricks,
            List<Powerup> powerups, int level, Boss boss) {
        this.background = background;
        this.paddle = paddle;
        this.ballManager = ballManager;
        this.bricks = bricks;
        this.powerups = powerups;
        this.level = level;
        this.boss = boss;
    }

    // Getter & Setter
    public Background getBackground() {
        return background;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public BallManager getBallManager() {
        return ballManager;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public List<Powerup> getPowerups() {
        return powerups;
    }

    public int getLevel() {
        return level;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void setBallManager(BallManager ballManager) {
        this.ballManager = ballManager;
    }

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
    }

    public void setPowerups(List<Powerup> powerups) {
        this.powerups = powerups;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Boss getBoss() {
        return this.boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    // Phương thức để khôi phục tất cả các thành phần UI của GameMap sau khi
    // deserialized
    public void restoreGameMap(Pane parentPane, GameController gameController) {
        if (background != null) {
            background.restoreView(parentPane);
        }
        if (paddle != null) {
            paddle.restoreView(parentPane);
        }
        if (ballManager != null) {
            ballManager.restoreView(parentPane);
        }
        if (level > Gameconfig.TOTAL_MAP) { // Boss level
            if (boss != null) {
                boss.restoreView(parentPane);
            }
        } else { // Brick level
            for (Brick brick : bricks) {
                brick.restoreView(parentPane);
            }
        }
        // Powerups cần GameController để gán lại cho các powerup cụ thể
        Powerup.setGameController(gameController);
        for (Powerup powerup : powerups) {
            powerup.restoreView(parentPane);
            // Gán lại controller cho các powerup cụ thể
            if (powerup instanceof MultiBallPowerup) {
                ((MultiBallPowerup) powerup).setGameController(gameController);
            } else if (powerup instanceof ExtraHpPowerup) {
                ((ExtraHpPowerup) powerup).setGameController(gameController);
            } else if (powerup instanceof FireBallPowerup) {
                ((FireBallPowerup) powerup).setGameController(gameController);
            } else if (powerup instanceof ExtendPaddlePowerup) {
                ((ExtendPaddlePowerup) powerup).setPaddle(paddle);
            } else if (powerup instanceof ShrinkPaddlePowerup) {
                ((ShrinkPaddlePowerup) powerup).setPaddle(paddle);
            }
        }
    }
}