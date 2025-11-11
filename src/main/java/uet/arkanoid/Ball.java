package uet.arkanoid;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.util.LinkedList;

public class Ball extends BaseObject {

    private double dx;
    private double dy;
    private double radius;
    private transient LinkedList<ImageView> trailList = new LinkedList<>();
    private static final int MAX_TRAIL = 8; // số vệt tối đa
    private boolean isFireBall = false;

    // Load ảnh 1 lần cho tất cả Ball
    private static final Image FIREBALL_IMAGE = new Image(
            Ball.class.getResource("/assets/image/balls/hp.png").toExternalForm()); // Dùng tạm hp.png
    private static final Image BALL_IMAGE = new Image(
            Ball.class.getResource("/assets/image/balls/ball.png").toExternalForm());

    public void setFireBall(boolean fire) {
        this.isFireBall = fire;
        if (super.getView() instanceof ImageView img) { // dùng view của BaseObject
            img.setImage(fire ? FIREBALL_IMAGE : BALL_IMAGE);
        }
    }

    public boolean isFireBall() {
        return isFireBall;
    }

    // Constructor dùng khi tạo mới trong game
    public Ball(Pane pane) {
        super(400, 400, Gameconfig.size_ball, Gameconfig.size_ball, pane);
        this.radius = Gameconfig.size_ball / 2;
        setInitialDirection();
        loadImage();
    }

    public Ball(double x, double y, double dx, double dy, Pane pane) {
        super(x, y, Gameconfig.size_ball, Gameconfig.size_ball, pane);
        this.dx = dx; // Gán trực tiếp dx, dy
        this.dy = dy;
        this.radius = Gameconfig.size_ball / 2;
        normalizeSpeed(Gameconfig.speed_ball); // Chuẩn hóa lại tốc độ
        loadImage();
    }

    public Ball(Paddle paddle, Pane pane) {
        super(
                paddle.getX() + paddle.getWidth() / 2 - Gameconfig.size_ball / 2,
                paddle.getY() - Gameconfig.size_ball,
                Gameconfig.size_ball,
                Gameconfig.size_ball,
                pane);
        this.radius = Gameconfig.size_ball / 2;
        setInitialDirection();
        loadImage();
    }

    // Constructor cho khi deserialized (không có pane)
    public Ball(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.radius = Gameconfig.size_ball / 2;
    }

    /** Thiết lập hướng ban đầu: luôn chéo, ngẫu nhiên trái/phải */
    private void setInitialDirection() {
        double angleDegrees = Math.random() < 0.5 ? 135 : 45; // 45° hoặc 135° (chéo lên)
        double angle = Math.toRadians(angleDegrees);
        dx = Math.cos(angle) * Gameconfig.speed_ball;
        dy = Math.sin(angle) * Gameconfig.speed_ball;
    }

    /** Load ảnh */
    public void loadImage() { // Bỏ pane khỏi tham số
        ImageView imageView = new ImageView(isFireBall ? FIREBALL_IMAGE : BALL_IMAGE);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        setView(imageView);
        if (pane != null && !pane.getChildren().contains(imageView)) { // Chỉ thêm vào pane nếu chưa có
            pane.getChildren().add(imageView);
        }
    }

    /** Cập nhật vị trí Ball */
    public void update(double deltaTime) {
        x += dx * deltaTime;
        y += dy * deltaTime;
        // va chạm tường
        if (x <= 0) {
            x = 0;
            dx = -dx;
            PlaySound.soundEffect("/assets/sound/ballSound.mp3");
        }
        if (x + width >= Gameconfig.screen_width) {
            x = Gameconfig.screen_width - width;
            dx = -dx;
            PlaySound.soundEffect("/assets/sound/ballSound.mp3");
        }
        if (y <= 0) {
            y = 0;
            dy = -dy;
            PlaySound.soundEffect("/assets/sound/ballSound.mp3");
        }

        // Cập nhật vị trí hiển thị (UI)
        // Đây là nơi cần `Platform.runLater` nếu update logic và UI trên 2 luồng khác
        // nhau.
        // Tuy nhiên, trong cấu trúc hiện tại, logic game sẽ tính toán `x`, `y` mới.
        // Sau đó, một khối `Platform.runLater` lớn sẽ cập nhật tất cả UI.
        // Tạm thời giữ nguyên `setLayoutX/Y` ở đây, nhưng nhớ rằng nó sẽ được gọi trong
        // `Platform.runLater`.

        // tạo hiệu ứng vệt
        createTrail();
    }

    /** Hiệu ứng vệt mờ theo hướng di chuyển */
    private void createTrail() {
        if (pane == null || !(view instanceof ImageView ballView))
            return;

        // Nếu danh sách trail chưa đủ, tạo sẵn vài ImageView để tái sử dụng
        while (trailList.size() < MAX_TRAIL) {
            ImageView trail = new ImageView(ballView.getImage());
            trail.setFitWidth(width);
            trail.setFitHeight(height);
            trail.setOpacity(0.0); // ban đầu ẩn
            // Thêm vào pane ở vị trí trước ballView để vệt nằm dưới bóng
            int ballViewIndex = pane.getChildren().indexOf(ballView);
            if (ballViewIndex != -1) {
                pane.getChildren().add(Math.max(0, ballViewIndex - 1), trail);
            } else {
                pane.getChildren().add(trail);
            }
            trailList.add(trail);
        }

        // Dịch chuyển index vòng tròn: vệt cũ nhất sẽ được tái sử dụng
        ImageView trail = trailList.removeFirst();
        trailList.addLast(trail);

        // Đặt lại vị trí và hiển thị vệt mới
        trail.setLayoutX(x);
        trail.setLayoutY(y);
        trail.setOpacity(0.4);

        // Làm mờ dần dần (tự tính opacity giảm)
        FadeTransition fade = new FadeTransition(Duration.millis(200), trail);
        fade.setFromValue(0.4);
        fade.setToValue(0.0);
        fade.play();
    }

    /** Slow: giảm tốc nhưng vẫn đúng tổng vector */
    public void slow() {
        normalizeSpeed(Gameconfig.speed_ball * 0.8);
    }

    /** Fast: tăng tốc nhưng vẫn đúng tổng vector */
    public void fast() {
        normalizeSpeed(Gameconfig.speed_ball * 1.2);
    }

    /** Chuẩn hóa tổng vector giữ đúng speed, giữ hướng */
    private void normalizeSpeed(double targetSpeed) {
        double currentSpeed = Math.sqrt(dx * dx + dy * dy);
        if (currentSpeed == 0)
            return; // Tránh chia cho 0
        double ratio = targetSpeed / currentSpeed;
        dx *= ratio;
        dy *= ratio;
    }

    // getter/setter
    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
        normalizeSpeed(Gameconfig.speed_ball); // Giữ tốc độ tổng không đổi
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
        normalizeSpeed(Gameconfig.speed_ball); // Giữ tốc độ tổng không đổi
    }

    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getRadius() {
        return radius;
    }

    public double getCenterX() {
        return x + radius;
    }

    public double getCenterY() {
        return y + radius;
    }

    @Override
    public void restoreView(Pane parentPane) {
        super.restoreView(parentPane);
        loadImage();
        // Clear old trails and recreate if pane is new
        for (ImageView trail : trailList) {
            parentPane.getChildren().remove(trail);
        }
        trailList.clear();
    }
}