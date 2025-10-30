package uet.arkanoid;

import uet.arkanoid.Powerups.Powerup;
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

    public GameMap(int level, Pane root) {
        BaseObject.setRootPane(root);
        this.level = level;
        this.background = new Background(3);
        this.paddle = new Paddle();
        this.ballManager = new BallManager(paddle);
        this.ballManager.addDefaultBall();
        this.bricks = ReadMapFile.readMapFXML(level, root);
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
}
