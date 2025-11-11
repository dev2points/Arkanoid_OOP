package uet.arkanoid;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Paddle extends BaseObject {

    private static final int SCREEN_WIDTH = Gameconfig.screen_width;
    private static final int SCREEN_HEIGHT = Gameconfig.screen_height;

    private double speed = Gameconfig.speed_paddle;
    private double moveDir = 0;

    private int state = 0; // -1 = shrink, 0 = normal, 1 = extend

    // Constructor khi tạo mới
    public Paddle(Pane pane) {
        super(SCREEN_WIDTH / 2 - Gameconfig.width_paddle / 2,
                SCREEN_HEIGHT - Gameconfig.height_paddle,
                Gameconfig.width_paddle,
                Gameconfig.height_paddle, pane);
        loadImage();
    }

    // Constructor khi deserialized
    public Paddle(double x, double y, double width, double height, double speed, double moveDir, int state) {
        super(x, y, width, height);
        this.speed = speed;
        this.moveDir = moveDir;
        this.state = state;
    }

    /** Xử lý phóng to paddle theo state logic */
    public void extend() {
        switch (state) {
            case 1 -> {
                /* đang phóng to, giữ nguyên */ }
            case 0 -> applyExtend(); // bình thường → phóng to
            case -1 -> resetSize(); // đang thu nhỏ → về bình thường
        }
    }

    /** Xử lý thu nhỏ paddle theo state logic */
    public void shrink() {
        switch (state) {
            case -1 -> {
                /* đang thu nhỏ, giữ nguyên */ }
            case 0 -> applyShrink(); // bình thường → thu nhỏ
            case 1 -> resetSize(); // đang phóng to → về bình thường
        }
    }

    /** Áp dụng phóng to */
    private void applyExtend() {
        double newWidth = width * Gameconfig.extend_ratio;
        double newX = x - (newWidth - width) / 2;
        width = newWidth;
        x = Math.max(0, Math.min(newX, SCREEN_WIDTH - width));
        state = 1;
        updateView();
    }

    /** Áp dụng thu nhỏ */
    private void applyShrink() {
        double newWidth = width * Gameconfig.shrink_ratio;
        double newX = x - (newWidth - width) / 2;
        width = newWidth;
        x = Math.max(0, Math.min(newX, SCREEN_WIDTH - width));
        state = -1;
        updateView();
    }

    /** Reset paddle về kích thước bình thường */
    public void resetSize() {
        width = Gameconfig.width_paddle;
        x = Math.max(0, Math.min(x, SCREEN_WIDTH - width));
        state = 0;
        updateView();
    }

    /** Cập nhật hiển thị paddle */
    public void updateView() {
        if (view instanceof ImageView img) {
            img.setFitWidth(width);
            img.setLayoutX(x);
        }
    }

    /** Load hình ảnh paddle */
    public void loadImage() { // Bỏ pane khỏi tham số
        if (pane == null)
            return;
        Image paddleImg = new Image(getClass().getResource("/assets/image/paddles/paddle_1.png").toExternalForm());
        ImageView imageView = new ImageView(paddleImg);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        setView(imageView);
        if (!pane.getChildren().contains(imageView)) { // Chỉ thêm nếu chưa có
            pane.getChildren().add(imageView);
        }
    }

    /** Di chuyển paddle */
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
        if (x + width > SCREEN_WIDTH)
            x = SCREEN_WIDTH - width;

        // Cập nhật hiển thị
        updateView();
    }

    /** Lấy trạng thái paddle (-1,0,1) */
    public int getState() {
        return state;
    }

    @Override
    public void restoreView(Pane parentPane) {
        super.restoreView(parentPane); // Set lại pane
        loadImage();
        updateView(); // Cập nhật vị trí và kích thước
    }
}