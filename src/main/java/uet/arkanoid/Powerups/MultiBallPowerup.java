package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import uet.arkanoid.Ball;
import uet.arkanoid.GameController;
import java.util.ArrayList;
import java.util.List;

public class MultiBallPowerup extends Powerup {

    private GameController controller;

    public MultiBallPowerup(double x, double y, double width, double height, GameController controller) {
        super(x, y, width, height);
        this.controller = controller;
    }

    @Override
    public Image loadImage() {
        return new Image(getClass().getResource("/assets/image/powerups/extend_paddle.png").toExternalForm());
    }

    @Override
    public void active() {
        // Lấy danh sách bóng hiện có
        List<Ball> currentBalls = new ArrayList<>(controller.getBalls());
        List<Ball> newBalls = new ArrayList<>();

        for (Ball mainBall : currentBalls) {
            double x = mainBall.getX();
            double y = mainBall.getY();
            double dx = mainBall.getDx();
            double dy = mainBall.getDy();

            // Tạo 2 quả bóng mới lệch hướng nhau một chút
            Ball ball1 = new Ball(x, y, dx * 0.8, dy * 1.0);
            Ball ball2 = new Ball(x, y, -dx * 0.8, dy * 1.0);

            newBalls.add(ball1);
            newBalls.add(ball2);
        }

        // Thêm tất cả bóng mới vào controller
        for (Ball b : newBalls) {
            controller.addBall(b);
        }

      
    }
}
