package uet.arkanoid;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Ball extends BaseObject {
    private double dx = 0; // vận tốc trục X (pixel/giây)
    private double dy = -Gameconfig.speed_ball; // vận tốc trục Y (pixel/giây)
    private Pane pane;

    public Ball(double x, double y, Pane pane) {
        super(x, y, Gameconfig.size_ball, Gameconfig.size_ball, pane);
        this.pane = pane;
        loadImage();
    }

    public Ball(Pane pane) {
        super(140, 300, Gameconfig.size_ball, Gameconfig.size_ball, pane);
        this.pane = pane;
        loadImage();
    }

    private void loadImage() {
        Image ballImg = new Image(getClass().getResource("/assets/image/balls/ball_1.png").toExternalForm());

        ImageView imageView = new ImageView(ballImg);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        setView(imageView);
        pane.getChildren().add(imageView);
    }

    /** Cập nhật vị trí bóng theo deltaTime (giây) */
    public void update(double deltaTime) {
        x += dx * deltaTime;
        y += dy * deltaTime;

        // Va chạm tường
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

        // Nếu rơi khỏi màn hình
        if (y > Gameconfig.screen_height) {
            resetPosition();
        }

        // Cập nhật vị trí hiển thị
        if (view instanceof ImageView img) {
            img.setLayoutX(x);
            img.setLayoutY(y);
        }
    }

    private void resetPosition() {
        x = Gameconfig.screen_width / 2 - width / 2;
        y = Gameconfig.screen_height / 2;
        dx = 0;
        dy = -Gameconfig.speed_ball;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void slow() {
        this.dx *= 0.8;
        this.dy *= 0.8;
    }

    public void fast() {
        this.dx *= 1.2;
        this.dy *= 1.2;
    }

    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    } // thêm dòng này
}
