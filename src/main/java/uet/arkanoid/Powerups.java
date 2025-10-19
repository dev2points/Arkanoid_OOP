package uet.arkanoid;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Powerups extends BaseObject {
    private String type;
    static List<Powerups> listpowerups = new ArrayList<>();


    public Powerups(double x, double y, double width, double height, String type_powerup) {
        super(x, y, width, height);
        this.type = type_powerup;
        loadpowerup();
    }

    // ✅ Static factory method: có xác suất rơi Powerup
    public static Powerups maybeDrop(double x, double y) {
        double dropRate = 0.10; // 25% xác suất rơi power-up
        if (Math.random() > dropRate) {
            return null; // không rơi gì
        }

        // Danh sách loại power-up có thể rơi
        String[] types = {
            "Extend paddle", "Shrink paddle"
        };

        int index = (int) (Math.random() * types.length);
        String randomType = types[index];
        System.out.println(index+"huhu");
        Powerups newpowerups = new Powerups(x, y, 32, 32, randomType);
        listpowerups.add(newpowerups);

        return newpowerups;
    }

    private void loadpowerup() {
        Image image;
        System.out.println(type);
         switch (type) {
            case "Extend paddle":
                image = new Image(getClass().getResource("/assets/image/powerups/extend_paddle.png").toExternalForm());
                break;
            case "Shrink paddle":
                image = new Image(getClass().getResource("/assets/image/powerups/shrink_paddle.png").toExternalForm());
                break;
            case "Multi ball":
                image = new Image(getClass().getResource("/assets/image/powerups/multi_ball.png").toExternalForm());
                break;
            case "Add ball":
                image = new Image(getClass().getResource("/assets/image/powerups/add_ball.png").toExternalForm());
                break;
            case "Slow ball":
                image = new Image(getClass().getResource("/assets/image/powerups/slow_ball.png").toExternalForm());
                break;
            case "Fast ball":
                image = new Image(getClass().getResource("/assets/image/powerups/fast_ball.png").toExternalForm());
                break;
            case "Extra life":
                image = new Image(getClass().getResource("/assets/image/powerups/extra_life.png").toExternalForm());
                break;
            default:
                System.out.println("⚠️ Invalid powerup type: " + type);
                image = new Image(getClass().getResource("/assets/image/powerups/default.png").toExternalForm());
                break;
        }

        // ✅ Tạo ImageView và thêm vào pane
        ImageView iv = new ImageView(image);
        iv.setFitWidth(width);
        iv.setFitHeight(height);
        System.out.println(x);
        iv.setLayoutX(x);
        iv.setLayoutY(y);

        setView(iv);
        pane.getChildren().add(iv);
    }

    public static void updateListPowerups() {
        for (Powerups j : listpowerups){
            j.sety();
        }
    }

    public String getType() {
        return type;
    }
    public void sety(){
        y=y+Gameconfig.speed_powerup*deltatime;
         if (view instanceof ImageView img) {
            img.setLayoutY(y);
        }
    }
}
