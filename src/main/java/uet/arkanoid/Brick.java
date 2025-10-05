package uet.arkanoid;

import java.util.Queue;
import java.util.LinkedList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.Node;

public class Brick extends BaseObject {
    Queue<Image> frames = new LinkedList<>();

    /**
     * Khởi tạo brick gồm Queue frames để lưu trữ các hình ảnh của viên gạch.
     * Tự động load các hình ảnh dựa trên type_brick.
     */
    public Brick(double x, double y, double width, double height, Pane pane, int type_brick) {
        super(x, y, width, height, pane);
        loadbricks(type_brick);
    }

    /*
     * Load các hình ảnh của viên gạch từ sprite sheet dựa trên type_brick.
     * Mỗi loại gạch có số lượng hình ảnh tương ứng với type_brick.
     */
    private void loadbricks(int type_brick) {
        Image sheet;
        switch (type_brick) {
            case 1:
                sheet = new Image("file:assets/image/brick_1.png");
                break;
            case 2:
                sheet = new Image("file:assets/image/brick_2.png");
                break;
            case 3:
                sheet = new Image("file:assets/image/brick_3.png");
                break;
            case 4:
                sheet = new Image("file:assets/image/brick_4.png");
                break;
            case 5:
                sheet = new Image("file:assets/image/brick_5.png");
                break;
            // Thêm các bricks khác
            default:
                System.out.println("Invalid brick type");
                sheet = new Image("file:assets/image/brick_1.png");
                break;
        }
        PixelReader reader = sheet.getPixelReader();

        for (int y = 0; y < type_brick; y++) {
            WritableImage frame = new WritableImage(
                    reader,
                    0, // vị trí X trong sheet chỉ có 1 column
                    y * Gameconfig.height_brick, // vị trí Y trong sheet
                    Gameconfig.width_brick, // chiều rộng của frame
                    Gameconfig.height_brick // chiều cao của frame
            );
            frames.add(frame);
        }
    }

    @Override
    /*
     * Cập nhật hình ảnh của viên gạch dựa trên hàng đợi frames.
     * Nếu chưa có view tự động tạo mới ImageView.
     * Nếu đã có view chỉ thay đổi ảnh.
     * Nếu hàng đợi rỗng, hủy đối tượng khỏi Pane.
     */
    public void update(Pane pane) {
        if (frames != null && !frames.isEmpty()) {
            // Lấy frame hiện tại và loại bỏ khỏi queue
            Image currentFrame = frames.poll();

            if (view instanceof ImageView imageView) {
                // Nếu view đã tồn tại thì chỉ thay đổi ảnh
                imageView.setImage(currentFrame);
            } else {
                // Nếu chưa có view thì tạo mới
                ImageView imageView = new ImageView(currentFrame);
                imageView.setFitWidth(width);
                imageView.setFitHeight(height);
                imageView.setLayoutX(x);
                imageView.setLayoutY(y);
                setView(imageView);
                pane.getChildren().add(imageView);
            }
        } else {
            // Nếu không còn frame -> hủy đối tượng
            if (view != null && view.getParent() instanceof Pane p) {
                destroy(p);
            }
        }
    }
}
