package uet.arkanoid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;

public class BallManager implements Serializable {
    private List<Ball> balls = new ArrayList<>();
    private Paddle paddle; // paddle không cần transient vì nó là một đối tượng BaseObject đã serialize
    private boolean waitingForLaunch = false;
    private transient List<Ball> toRemove = new ArrayList<>(); // vẫn là transient
    private transient Pane pane; // vẫn là transient

    public BallManager(Paddle paddle, Pane pane) {
        this.pane = pane;
        this.paddle = paddle;
    }

    // Constructor cho khi deserialized, pane sẽ được set sau
    public BallManager() {
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
        // System.out.println(balls.size()); // Comment dòng này để tránh spam console
        // Danh sách tạm để lưu bóng cần xoá
        if (toRemove == null)
            toRemove = new ArrayList<>(); // Khởi tạo lại nếu là null (sau deserialize)
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
            // Cần xóa view của bóng khỏi pane trước khi xóa đối tượng
            for (Ball ball : toRemove) {
                ball.destroy();
            }
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

    public void restoreView(Pane parentPane) { // Thêm parentPane
        this.pane = parentPane; // Gán lại pane sau khi deserialized
        if (paddle != null)
            paddle.restoreView(parentPane); // Đảm bảo paddle cũng được restore
        for (Ball ball : balls) {
            ball.restoreView(parentPane);
        }
    }

    public void reset() {
        for (Ball ball : balls) {
            ball.destroy(); // Xóa view khỏi pane
        }
        balls.clear();
        addDefaultBall();
    }
}