package uet.arkanoid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;

public class BallManager implements Serializable {
    private List<Ball> balls = new ArrayList<>();
    private Paddle paddle;
    private boolean waitingForLaunch = false;
    private List<Ball> toRemove = new ArrayList<>();
    private transient Pane pane;

    public BallManager(Paddle paddle, Pane pane) {
        this.pane = pane;
        this.paddle = paddle;
    }

    public void addBall(double x, double y, double dx, double dy) {
        Ball ball = new Ball(x, y, dx, dy, pane);
        balls.add(ball);
    }

    public void addBall(Ball ball) {
        balls.add(ball);
    }

    public void addDefaultBall() {
        Ball ball = new Ball(paddle, pane); // bóng nằm trên paddle
        balls.add(ball);
        waitingForLaunch = true; // ← bật trạng thái chờ Space
    }

    public void removeBall(Ball ball) {
        balls.remove(ball);
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public int getSize() {
        return balls.size();
    }

    public boolean isWaitingForLaunch() {
        return waitingForLaunch;
    }

    public void setWaitingForLaunch(boolean value) {
        waitingForLaunch = value;
    }

    public void updateAll(double deltaTime, GameController gameController) {
        // System.out.println(balls.size());
        // Danh sách tạm để lưu bóng cần xoá
        toRemove.clear();
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
            User user = gameController.getUser();
            user.loseHp(1);
            int hp = user.getHp();
            if (hp > 0)
                addDefaultBall();
            if (hp == 0)
                System.out.println(user.getScore());

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

    public void restoreView(Pane pane) {
        for (Ball ball : balls) {

            ball.setRootPane(pane);
            ball.resetTrails();
            ball.loadImage();
        }
    }

    public void reset() {
        balls.clear();
        addDefaultBall();
    }

}
