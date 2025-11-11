package uet.arkanoid;

import uet.arkanoid.Powerups.Powerup;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameMap implements Serializable {
    // private static final long serialVersionUID = 1L;

    private Background background;
    private Paddle paddle;
    private BallManager ballManager;
    private List<Brick> bricks = new ArrayList<>();
    private List<Powerup> powerups = new ArrayList<>();
    private int level;
    private Boss boss;

    public GameMap(int level, Pane root) {
        this.level = level;
        this.background = new Background(1, root);
        this.paddle = new Paddle(root);
        this.ballManager = new BallManager(paddle, root);
        this.ballManager.addDefaultBall();
        if (level > Gameconfig.TOTAL_MAP)
            this.boss = new Boss(250, 50, 260, 260, root);
        else
            this.bricks = ReadMapFile.readMapFXML(level, root);
    }

    // public GameMap(Pane root) {
    // this.level = 6;
    // this.background = new Background(3);

    // this.paddle = new Paddle();
    // this.ballManager = new BallManager(paddle);
    // this.ballManager.addDefaultBall();
    // }

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

}
