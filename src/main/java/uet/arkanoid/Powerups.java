package uet.arkanoid;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Powerups extends BaseObject {

    public Powerups(double x, double y, double width, double height, Pane pane, String type_powerup) {
        super(x, y, width, height, pane);
        loadpowerup(type_powerup);
    }

    private void loadpowerup(String type_powerup) {
        Image sheet;
        switch (type_powerup) {
            case "Extend paddle":
                sheet = new Image("file:assets/image/brick_1.png");
                break;
            case "Shrink paddle":
                sheet = new Image("file:assets/image/brick_2.png");
                break;
            case "Multi ball":
                sheet = new Image("file:assets/image/brick_3.png");
                break;
            case "Add ball":
                sheet = new Image("file:assets/image/brick_4.png");
                break;
            case "Slow ball":
                sheet = new Image("file:assets/image/brick_5.png");
                break;
            case "Fast ball":
                sheet = new Image("file:assets/image/brick_5.png");
                break;
            case "Extra life":
                sheet = new Image("file:assets/image/brick_5.png");
                break;
            case "Brick explosion":
                sheet = new Image("file:assets/image/brick_5.png");
                break;
            // Thêm các powerup khác
            default:
                System.out.println("Invalid powerup type");
                sheet = new Image("file:assets/image/brick_1.png");
                break;
        }
    }

    @Override
    public void update() {
        this.setY(this.getY() + Gameconfig.speed_powerup);
    }

}
