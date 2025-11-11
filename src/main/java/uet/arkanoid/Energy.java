package uet.arkanoid;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Energy extends BaseObject {
    private double dx, dy;
    private double speed = Gameconfig.SPEED_ENERGY;
    private boolean active = true;

    // Load ảnh 1 lần duy nhất cho tất cả Energy
    private static final Image IMAGE = new Image(
            Energy.class.getResource("/assets/image/boss/energy.png").toExternalForm());

    // Constructor khi tạo mới
    public Energy(double x, double y, double angle, double speed, Pane pane) {
        super(x, y, 50, 50, pane);
        this.speed = speed;
        this.dx = Math.cos(Math.toRadians(angle)) * speed;
        this.dy = Math.sin(Math.toRadians(angle)) * speed;
        initView();
    }

    // Constructor khi deserialized
    public Energy(double x, double y, double width, double height, double dx, double dy, double speed, boolean active) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.active = active;
    }

    // Tạo ImageView từ ảnh đã load
    public void initView() {
        if (pane == null)
            return;
        ImageView view = new ImageView(IMAGE);
        view.setFitWidth(width);
        view.setFitHeight(height);
        setView(view);
        pane.getChildren().add(view);
    }

    public void update(double deltaTime) {
        if (!active)
            return;

        x += dx * deltaTime;
        y += dy * deltaTime;

        if (view != null) {
            view.setLayoutX(x);
            view.setLayoutY(y);
        }

        if (x + width < 0 || x > Gameconfig.screen_width ||
                y + height < 0 || y > Gameconfig.screen_height) {
            destroy(); // destroy sẽ xóa view khỏi pane và set active = false
            active = false;
        }
    }

    @Override
    public void destroy() {
        super.destroy(); // Gọi phương thức destroy của BaseObject để xóa view
        active = false; // Đảm bảo Energy không còn hoạt động
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void restoreView(Pane parentPane) {
        super.restoreView(parentPane); // Set lại pane
        if (active) { // Chỉ tạo lại view nếu Energy đang active
            initView();
        }
    }
}