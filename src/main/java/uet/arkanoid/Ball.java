package uet.arkanoid;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.effect.ColorAdjust;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.util.LinkedList;

public class Ball extends BaseObject {
    private double dx = 0;
    private double dy = -Gameconfig.speed_ball;
    private double radius;
    private Pane pane;
    private LinkedList<ImageView> trailList = new LinkedList<>();
    private static final int MAX_TRAIL = 8; // số vệt tối đa

    public Ball() {
        super(400, 400, Gameconfig.size_ball, Gameconfig.size_ball, null);
        this.radius = Gameconfig.size_ball / 2;
        
    }

    public Ball(double x, double y, Pane pane) {
        super(x, y, Gameconfig.size_ball, Gameconfig.size_ball, pane);
        this.pane = pane;
        this.radius = Gameconfig.size_ball / 2;
        loadImage();
    }

    public void attachToPane(Pane pane) {
        this.pane = pane;
        loadImage();
    }


    private void loadImage() {
        Image ballImg = new Image(getClass().getResource("/assets/image/balls/ball.png").toExternalForm());
        ImageView imageView = new ImageView(ballImg);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        setView(imageView);
        if(pane!=null) {
            pane.getChildren().add(imageView);
        }
    }

    public void update(double deltaTime) {
        // cập nhật vị trí
        x += dx * deltaTime;
        y += dy * deltaTime;

        // tạo hiệu ứng vệt
        createTrail();

        // va chạm tường
        if (x <= 0) {
            x = 0; dx = -dx;
            PlaySound.soundEffect("/assets/sound/ballSound.mp3");
        }
        if (x + width >= Gameconfig.screen_width) {
            x = Gameconfig.screen_width - width; dx = -dx;
            PlaySound.soundEffect("/assets/sound/ballSound.mp3");
        }
        if (y <= 0) {
            y = 0; dy = -dy;
            PlaySound.soundEffect("/assets/sound/ballSound.mp3");
        }
        if (y > Gameconfig.screen_height) {
            resetPosition();
        }

        // cập nhật vị trí hiển thị
        if (view instanceof ImageView img) {
            img.setLayoutX(x);
            img.setLayoutY(y);
        }
    }

    /** Hiệu ứng vệt mờ theo hướng di chuyển */
    private void createTrail() {
        if (!(view instanceof ImageView ballView)) return;

        ImageView trail = new ImageView(ballView.getImage());
        trail.setFitWidth(width);
        trail.setFitHeight(height);
        trail.setLayoutX(x);
        trail.setLayoutY(y);

        trail.setOpacity(0.4);

        pane.getChildren().add(pane.getChildren().indexOf(ballView), trail);
        trailList.add(trail);

        FadeTransition fade = new FadeTransition(Duration.millis(200), trail);
        fade.setFromValue(0.4);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> pane.getChildren().remove(trail));
        fade.play();

        if (trailList.size() > MAX_TRAIL) {
            ImageView old = trailList.removeFirst();
            pane.getChildren().remove(old);
        }
    }

    private void resetPosition() {
        x = Gameconfig.screen_width / 2 - width / 2;
        y = Gameconfig.screen_height / 2;
        dx = 0;
        dy = -Gameconfig.speed_ball;
    }

    // getter/setter như cũ
    public double getDx() { return dx; }
    public void setDx(double dx) { this.dx = dx; }
    public double getDy() { return dy; }
    public void setDy(double dy) { this.dy = dy; }
    public void slow() { this.dx *= 0.8; this.dy *= 0.8; }
    public void fast() { this.dx *= 1.2; this.dy *= 1.2; }
    public double getSpeed() { return Math.sqrt(dx * dx + dy * dy); }
    public double getRadius() { return radius; }
    public double getCenterX() { return x + radius; }
    public double getCenterY() { return y + radius; }
}
