package uet.arkanoid;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Paddle extends BaseObject {
    private double speed = Gameconfig.speed_paddle;
    private double moveDir = 0; // -1 = trái, 1 = phải, 0 = đứng yên

    /**
     * Khởi tạo Paddle – thanh đỡ bóng
     */
    public Paddle(double x, double y, Pane pane, double screenWidth) {
        super(x, y, Gameconfig.width_paddle, Gameconfig.height_paddle, pane);
        loadImage();
    }

    private void loadImage() {
        Image paddleImg = new Image("file:assets/image/paddles/paddle_1.png");
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
        update();
    }

    public void moveRight() {
        moveDir = 1;
        update();
    }

    public void stop() {
        moveDir = 0;
        update();
    }

    public void extend() {
        view.setScaleX(1.5);
    }

    public void shrink() {
        view.setScaleX(0.7);
    }

    @Override
    public void update() {
        x += moveDir * speed;

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
