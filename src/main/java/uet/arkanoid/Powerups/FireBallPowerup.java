package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import uet.arkanoid.Ball;
import uet.arkanoid.GameController;

import java.util.List;

public class FireBallPowerup extends Powerup {

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
        // Lấy tất cả bóng hiện có và chuyển chúng sang chế độ "fireball"
        List<Ball> balls = controller.getBalls();

        for (Ball ball : balls) {
            ball.setFireBall(true);

            // Tuỳ chọn: thêm hiệu ứng màu cho bóng khi đang ở chế độ Fireball
           // ball.setGlowEffect();
        }

        // Có thể đặt thời gian tồn tại của hiệu ứng, ví dụ 8 giây
        new Thread(() -> {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException ignored) {}
            for (Ball ball : balls) {
                ball.setFireBall(false);
                //ball.removeGlowEffect();
            }
        }).start();
    }
}
