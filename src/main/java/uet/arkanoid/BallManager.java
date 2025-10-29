package uet.arkanoid;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;

public class BallManager {
    private final List<Ball> balls = new ArrayList<>();
    private final Paddle paddle;
    private boolean waitingForLaunch = false; // ← cờ chờ bắt đầu

    public BallManager(Paddle paddle) {
        this.paddle = paddle;
    }

    public void addBall(double x, double y, double dx, double dy) {
        Ball ball = new Ball(x, y, dx, dy);
        balls.add(ball);
    }

    public void addBall(Ball ball) {
        balls.add(ball);
    }

    public void addDefaultBall() {
        Ball ball = new Ball(paddle); // bóng nằm trên paddle
        balls.add(ball);
        waitingForLaunch = true; // ← bật trạng thái chờ Space
    }

    public void removeBall(Ball ball) {
        balls.remove(ball);
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public boolean isWaitingForLaunch() {
        return waitingForLaunch;
    }

    public void setWaitingForLaunch(boolean value) {
        waitingForLaunch = value;
    }

    public void updateAll(double deltaTime) {
        // Nếu không còn bóng nào → tạo lại 1 quả trên paddle
        if (balls.isEmpty()) {
            addDefaultBall();
            return;
        }

        // Danh sách tạm để lưu bóng cần xoá
        List<Ball> toRemove = new ArrayList<>();

        if (waitingForLaunch) {
            // Giữ bóng đứng yên trên paddle
            for (Ball ball : balls) {
                ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
                ball.setY(paddle.getY() - ball.getHeight());
            }
        } else {
            // Cập nhật chuyển động bình thường
            for (Ball ball : balls) {
                ball.update(deltaTime);

                // Nếu bóng rơi khỏi màn hình thì đánh dấu xoá
                if (ball.getY() > Gameconfig.screen_height) {
                    toRemove.add(ball);
                }
            }
        }

        // Xoá các bóng rơi ra ngoài
        if (!toRemove.isEmpty()) {
            balls.removeAll(toRemove);
        }

        // Nếu không còn bóng nào → tạo lại bóng mặc định
        if (balls.isEmpty()) {
            addDefaultBall();
        }
    }


    // Gọi khi nhấn SPACE
    public void launchBalls() {
        if (waitingForLaunch && !balls.isEmpty()) {
            for (Ball ball : balls) {
                ball.setDx(0);
                ball.setDy(-Gameconfig.speed_ball);
            }
            waitingForLaunch = false;
        }
    }
}
