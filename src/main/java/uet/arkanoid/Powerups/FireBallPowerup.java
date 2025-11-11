package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import uet.arkanoid.Ball;
import uet.arkanoid.GameController;

import java.util.List;

public class FireBallPowerup extends Powerup {

    private static final Image IMAGE = new Image(
            FireBallPowerup.class.getResource("/assets/image/powerups/hp.png").toExternalForm() // Dùng tạm hp.png
    );

    private transient GameController controller; // Transient

    public FireBallPowerup(double x, double y, double width, double height, GameController controller) {
        super(x, y, width, height, controller.getPane());
        this.controller = controller;
    }

    // Constructor cho khi deserialized
    public FireBallPowerup(double x, double y, double width, double height, double fallSpeed) {
        super(x, y, width, height, fallSpeed);
        // controller sẽ được gán lại sau
    }

    @Override
    public Image loadImage() {
        return IMAGE;
    }

    @Override
    public void active() {
        if (controller == null)
            return;
        // Lấy tất cả bóng hiện có và chuyển chúng sang chế độ "fireball"
        List<Ball> balls = controller.getBalls();

        for (Ball ball : balls) {
            ball.setFireBall(true);
        }

        // Có thể đặt thời gian tồn tại của hiệu ứng, ví dụ 8 giây
        new Thread(() -> {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException ignored) {
            }
            // Cần chạy trên JavaFX Thread để cập nhật UI
            javafx.application.Platform.runLater(() -> {
                for (Ball ball : balls) {
                    ball.setFireBall(false);
                }
            });
        }).start();
    }

    // // Setter cho controller khi khôi phục game
    // public void setGameController(GameController controller) {
    // this.controller = controller;
    // }
}