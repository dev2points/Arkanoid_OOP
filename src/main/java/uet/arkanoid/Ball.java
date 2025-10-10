package uet.arkanoid;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Ball extends BaseObject {
    private double dx = 4; // hướng di chuyển X
    private double dy = -4; // hướng di chuyển Y
    private Pane pane;
    private double screenWidth;
    private double screenHeight;

    public Ball(double x, double y, Pane pane, double screenWidth, double screenHeight) {
        super(x, y, Gameconfig.size_ball, Gameconfig.size_ball, pane);
        this.pane = pane;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        loadImage();
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDx() {
        return dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDy() {
            return dy;
        }

    public double getscreenWidth() {
        return screenWidth;
    }

     public void setscreenWidth(double screenWidth) {
        this.screenWidth = screenWidth;
    }

     public void setscreenHeight(double screenHeight) {
        this.screenHeight= screenHeight;
    }

    public double getscreenHeight() {
        return screenHeight;
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
        if (x <= 0 || x + width >= screenWidth) {
            dx = -dx;
        }

        if (y <= 0) {
            dy = -dy;
        }

        // Nếu rơi khỏi màn hình
        if (y > screenHeight) {
            resetPosition();
        }

        // Cập nhật vị trí hiển thị
        if (view instanceof ImageView img) {
            img.setLayoutX(x);
            img.setLayoutY(y);
        }
    }
    private void resetPosition() {
        x = screenWidth / 2 - width / 2;
        y = screenHeight / 2;
        dx = 4;
        dy = -4;
    }
}
