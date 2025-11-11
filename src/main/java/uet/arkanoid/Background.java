package uet.arkanoid;

import javafx.scene.layout.Pane;

public class Background extends BaseObject {
    private int type;

    Background(int type, Pane pane) {
        super(0, 0, Gameconfig.screen_width, Gameconfig.screen_height, pane);
        this.type = type;
        loadImage();
    }

    private void loadImage() {
        switch (type) {
            case 1:
                loadImage("/assets/image/background/background_1.jpg", pane);
                break;
            case 2:
                loadImage("/assets/image/background/background_2.jpg", pane);
                break;
            case 3:
                loadImage("/assets/image/background/background_3.jpg", pane);
                break;
            default:
                System.out.println("Cant acess to background path");
                loadImage("/assets/image/background/background_1.jpg", pane);
                break;
        }
    }

}
