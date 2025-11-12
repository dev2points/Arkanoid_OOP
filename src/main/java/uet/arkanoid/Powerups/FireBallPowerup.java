package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import uet.arkanoid.Ball;
import uet.arkanoid.GameController;

import java.util.List;

public class FireBallPowerup extends Powerup {
    private static Thread fireballTimer;
    private static final Image IMAGE = new Image(
            FireBallPowerup.class.getResource("/assets/image/powerups/fireball.png").toExternalForm()
    );

    private final GameController controller;

    public FireBallPowerup(double x, double y, double width, double height, GameController controller) {
        super(x, y, width, height, controller.getPane());
        this.controller = controller;
    }

    @Override
    public Image loadImage() {
        return IMAGE;
    }

    @Override

    public void active() {
        List<Ball> balls = controller.getBalls();
        for (Ball ball : balls) {
            ball.setFireBall(true);
        }

        // Nếu đã có timer đang chạy thì gia hạn hoặc reset nó
        if (fireballTimer != null && fireballTimer.isAlive()) {
            fireballTimer.interrupt(); // hủy thread cũ
        }

        fireballTimer = new Thread(() -> {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException ignored) { return; }
            for (Ball ball : balls) {
                ball.setFireBall(false);
            }
        });
        fireballTimer.start();
    }

}
