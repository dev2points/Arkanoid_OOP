package uet.arkanoid;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Ball extends BaseObject {
    private double dx = 0; // hướng di chuyển X
    private double dy = Gameconfig.speed_ball; // hướng di chuyển Y
    private Pane pane;

    public Ball(double x, double y, Pane pane, double screenWidth, double screenHeight) {
        super(x, y, Gameconfig.size_ball, Gameconfig.size_ball, pane);
        this.pane = pane;
        loadImage();
    }

    private void loadImage() {
        Image ballImg = new Image("file:assets/image/balls/ball_1.png");
        ImageView imageView = new ImageView(ballImg);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        setView(imageView);
        pane.getChildren().add(imageView);
    }

    @Override
    public void update() {
        x += dx;
        y += dy;

        // Va chạm tường
        if (x <= 0 || x + width >= Gameconfig.screen_width) {
            dx = -dx;
        }

        if (y <= 0) {
            dy = -dy;
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
        dx = Gameconfig.speed_ball;
        dy = Gameconfig.speed_ball;
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
        this.dx -= 2;
        this.dy -= 2;
    }

    public void fast() {
        this.dx += 2;
        this.dy += 2;
    }
}
