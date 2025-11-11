package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import uet.arkanoid.Ball;
import uet.arkanoid.GameController;
import java.util.ArrayList;
import java.util.List;

public class MultiBallPowerup extends Powerup {

    private static final Image IMAGE = new Image(
            MultiBallPowerup.class.getResource("/assets/image/powerups/hp.png").toExternalForm());

    private GameController controller;

    public MultiBallPowerup(double x, double y, double width, double height, GameController controller) {
        super(x, y, width, height, controller.getPane());
        this.controller = controller;
    }

    @Override
    public Image loadImage() {
        // Dùng ảnh cache thay vì load lại mỗi lần
        return IMAGE;
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
            double offset = 5; // khoảng cách tách bóng
            Ball ball1 = new Ball(x - offset, y, dx * 0.8 + 1, dy * 0.8, pane);
            Ball ball2 = new Ball(x + offset, y, -dx * 0.8, dy * 0.8, pane);
            if (mainBall.isFireBall()) {
                ball1.setFireBall(true);
                ball2.setFireBall(true);
            }

            newBalls.add(ball1);
            newBalls.add(ball2);
        }

        // Thêm bóng mới vào controller
        for (Ball b : newBalls) {
            controller.addBall(b);
        }
    }

}
