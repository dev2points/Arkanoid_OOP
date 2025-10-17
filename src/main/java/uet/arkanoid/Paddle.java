package uet.arkanoid;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Paddle extends BaseObject {
    private static int screen_width = Gameconfig.screen_width;
    private static int screen_height = Gameconfig.screen_height;

    private double speed = Gameconfig.speed_paddle;
    private double moveDir = 0;
    private double dx;

    public Paddle(Pane pane) {
        super(screen_width / 2 - Gameconfig.width_paddle / 2, screen_height - 50, Gameconfig.width_paddle,
                Gameconfig.height_paddle, pane);
        loadImage();
    }

    public void loadImage() {
        Image paddleImg = new Image(getClass().getResource("/assets/image/paddles/paddle_1.png").toExternalForm());
        ImageView imageView = new ImageView(paddleImg);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        setView(imageView);
        pane.getChildren().add(imageView);
    }

    public void moveLeft() {
        moveDir = -1;
    }

    public void moveRight() {
        moveDir = 1;
    }

    public void stop() {
        moveDir = 0;
    }

    public void extend() {
        view.setScaleX(1.5);
    }

    public void shrink() {
        view.setScaleX(0.7);
    }

    public double getDx() {
        return speed * moveDir;
    }

    /** Cập nhật vị trí paddle theo thời gian deltaTime (giây) */
    public void update(double deltaTime) {
        x += moveDir * speed * deltaTime;

        // Giới hạn biên
        if (x < 0)
            x = 0;
        if (x + width > Gameconfig.screen_width)
            x = Gameconfig.screen_width - width;

        // Cập nhật vị trí hiển thị
        if (view instanceof ImageView img) {
            img.setLayoutX(x);
        }
    }
}
