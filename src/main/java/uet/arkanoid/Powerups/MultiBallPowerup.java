package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import uet.arkanoid.Ball;
import uet.arkanoid.GameController;
import java.util.ArrayList;
import java.util.List;

public class MultiBallPowerup extends Powerup {

    private static final Image IMAGE = new Image(
            MultiBallPowerup.class.getResource("/assets/image/powerups/hp.png").toExternalForm()); // Dùng tạm hp.png

    private transient GameController controller; // Transient vì không serialize GameController

    public MultiBallPowerup(double x, double y, double width, double height, GameController controller) {
        super(x, y, width, height, controller.getPane());
        this.controller = controller;
    }

    // Constructor cho khi deserialized
    public MultiBallPowerup(double x, double y, double width, double height, double fallSpeed) {
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
            return; // Đảm bảo controller đã được gán lại
        // Lấy danh sách bóng hiện có
        List<Ball> currentBalls = new ArrayList<>(controller.getBalls());
        List<Ball> newBalls = new ArrayList<>();

        for (Ball mainBall : currentBalls) {
            double x = mainBall.getX();
            double y = mainBall.getY();
            double dx = mainBall.getDx();
            double dy = mainBall.getDy();
            double offset = 5; // khoảng cách tách bóng
            // Điều chỉnh dx, dy để bóng bay theo các hướng khác nhau
            Ball ball1 = new Ball(x - offset, y, dx * 0.8 + 50, dy * 0.8, pane); // Điều chỉnh dx, dy
            Ball ball2 = new Ball(x + offset, y, -dx * 0.8 - 50, dy * 0.8, pane); // Điều chỉnh dx, dy
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

    // // Setter cho controller khi khôi phục game
    // public void setGameController(GameController controller) {
    // this.controller = controller;
    // }
}