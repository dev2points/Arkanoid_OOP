package uet.arkanoid;

import javafx.scene.layout.Pane;

public class Background extends BaseObject {
    private int type;

    // Constructor khi tạo mới
    Background(int type, Pane pane) {
        super(0, 0, Gameconfig.screen_width, Gameconfig.screen_height, pane);
        this.type = type;
        loadImage();
    }

    // Constructor khi deserialized
    public Background(double x, double y, double width, double height, int type) {
        super(x, y, width, height);
        this.type = type;
    }

    private void loadImage() { // Bỏ pane khỏi tham số
        switch (type) {
            case 1:
                loadImage("/assets/image/background/background_1.jpg");
                break;
            case 2:
                loadImage("/assets/image/background/background_2.jpg");
                break;
            case 3:
                loadImage("/assets/image/background/background_3.jpg");
                break;
            default:
                System.out.println("Cant acess to background path");
                loadImage("/assets/image/background/background_1.jpg");
                break;
        }
    }

    @Override
    public void restoreView(Pane parentPane) {
        super.restoreView(parentPane); // Set lại pane
        loadImage(); // Load lại ảnh nền
    }

    // Getter cho type, cần thiết khi serialize
    public int getType() {
        return type;
    }
}