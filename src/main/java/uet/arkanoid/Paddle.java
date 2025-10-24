package uet.arkanoid;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle extends BaseObject {
    private static int screen_width = Gameconfig.screen_width;
    private static int screen_height = Gameconfig.screen_height;

    private double speed = Gameconfig.speed_paddle;
    private double moveDir = 0;

    public Paddle() {
        super(screen_width / 2 - Gameconfig.width_paddle / 2, screen_height - 50, Gameconfig.width_paddle,
                Gameconfig.height_paddle);
        loadImage();
    }

    public void extend() {
        // Chiều dài mới của paddle
        double newWidth = width * Gameconfig.extend_ratio;
        // Vị trí X mới
        double newX = x - (newWidth - width) / 2;
        // Set lại X và width
        width = newWidth;
        x = newX;
        // Giới hạn không ra khỏi màn hình
        if (x < 0)
            x = 0;
        if (x + width > screen_width)
            x = screen_width - width;
        // Set lại hình ảnh của paddle
        if (view instanceof ImageView img) {
            img.setFitWidth(width);
            img.setLayoutX(x);
        }

    }

    public void shrink() {
        // Chiều dài mới của paddle
        double newWidth = width * Gameconfig.shrink_ratio;
        // Vị trí X mới
        double newX = x - (newWidth - width) / 2;
        // Set lại X và width
        width = newWidth;
        x = newX;
        // Set lại hình ảnh của paddle
        if (view instanceof ImageView img) {
            img.setFitWidth(width);
            img.setLayoutX(x);
        }

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
