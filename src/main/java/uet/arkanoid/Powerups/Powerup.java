package uet.arkanoid.Powerups;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import uet.arkanoid.BaseObject;
import uet.arkanoid.GameController;

public abstract class Powerup extends BaseObject {
    private double fallSpeed = uet.arkanoid.Gameconfig.speed_powerup;
    public static transient GameController controller; // Vẫn transient

    // Constructor khi tạo mới
    public Powerup(double x, double y, double width, double height, Pane pane) {
        super(x, y, width, height, pane);
        loadPowerup(loadImage());
    }

    // Constructor khi deserialized
    public Powerup(double x, double y, double width, double height, double fallSpeed) {
        super(x, y, width, height);
        this.fallSpeed = fallSpeed;
    }

    public static void setGameController(GameController gameController) {
        controller = gameController;
    }

    // Load image để sử dụng loadpowerup
    public abstract Image loadImage();

    public void loadPowerup(Image image) { // Bỏ pane khỏi tham số
        if (pane == null)
            return;
        // Tạo ImageView và thêm vào pane
        ImageView iv = new ImageView(image);
        iv.setFitWidth(width);
        iv.setFitHeight(height);
        iv.setLayoutX(x);
        iv.setLayoutY(y);

        setView(iv);
        if (!pane.getChildren().contains(iv)) {
            pane.getChildren().add(iv);
        }
    }

    public abstract void active();

    // Cập nhật vị trí của powerup.
    @Override
    public void update() {
        y += fallSpeed * deltatime;
        if (view instanceof ImageView img) {
            img.setLayoutY(y);
        }
    }

    @Override
    public void restoreView(Pane parentPane) {
        super.restoreView(parentPane); // Set lại pane
        loadPowerup(loadImage()); // Load lại ảnh và tạo view
    }
}