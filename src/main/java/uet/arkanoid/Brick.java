package uet.arkanoid;

import java.util.Queue;
import java.util.LinkedList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Brick extends BaseObject {

    private Queue<Image> frames = new LinkedList<>();
    private int width_frame;
    private int height_frame;
    private int frame_count;
    private int type_brick;
    // private int block_frame_count;

    /**
     * Khởi tạo brick gồm Queue frames để lưu trữ các hình ảnh của viên gạch.
     * Tự động load các hình ảnh dựa trên type_brick.
     */
    public Brick(double x, double y, double width, double height, Pane pane, int type_brick) {
        super(x, y, width, height, pane);
        setType_brick(type_brick);
        check_type(type_brick);
        loadbricks(type_brick);
        update();
    }

    private void setType_brick(int type_brick) {
        this.type_brick = type_brick;
    }

    public boolean frames_isEmpty() {
        return frames.isEmpty();
    }

    private void check_type(int type_brick) {
        if (type_brick < 10) {
            frame_count = type_brick;
            width_frame = Gameconfig.width_brick;
            height_frame = Gameconfig.height_brick;
        } else if (type_brick < 20) {
            frame_count = 4;
            width_frame = Gameconfig.width_block_brick_1;
            height_frame = Gameconfig.height_block_brick_1;
        } else {
            frame_count = 4;
            width_frame = Gameconfig.width_block_brick_2;
            height_frame = Gameconfig.height_block_brick_2;
        }
    }

    /*
     * Load các hình ảnh của viên gạch từ sprite sheet dựa trên type_brick.
     * Mỗi loại gạch có số lượng hình ảnh tương ứng với type_brick.
     */
    private void loadbricks(int type_brick) {
        Image sheet;
        switch (type_brick) {
            case 1:
                sheet = new Image(getClass().getResource("/assets/image/bricks/brick_1.png").toExternalForm());
                break;
            case 2:
                sheet = new Image(getClass().getResource("/assets/image/bricks/brick_2.png").toExternalForm());
                break;
            case 3:
                sheet = new Image(getClass().getResource("/assets/image/bricks/brick_3.png").toExternalForm());
                break;
            case 4:
                sheet = new Image(getClass().getResource("/assets/image/bricks/brick_4.png").toExternalForm());
                break;
            case 5:
                sheet = new Image(getClass().getResource("/assets/image/bricks/brick_5.png").toExternalForm());
                break;
            case 11:
                sheet = new Image(getClass().getResource("/assets/image/bricks/brick_11.png").toExternalForm());
                break;
            case 12:
                sheet = new Image(getClass().getResource("/assets/image/bricks/brick_12.png").toExternalForm());
                break;
            case 21:
                sheet = new Image(getClass().getResource("/assets/image/bricks/brick_21.png").toExternalForm());
                break;
            case 22:
                sheet = new Image(getClass().getResource("/assets/image/bricks/brick_22.png").toExternalForm());
                break;
            // Thêm các bricks khác
            default:
                System.out.println("Invalid brick type: " + type_brick);
                sheet = new Image("file:assets/image/bricks/brick_1.png");
                break;
        }
        PixelReader reader = sheet.getPixelReader();
        for (int y = 0; y < frame_count; y++) {
            WritableImage frame = new WritableImage(
                    reader,
                    0, // vị trí X trong sheet chỉ có 1 column
                    y * height_frame, // vị trí Y trong sheet
                    width_frame, // chiều rộng của frame
                    height_frame // chiều cao của frame
            );
            frames.add(frame);
            // System.out.println("Add frame to brick queue");
        }
    }

    @Override
    /*
     * Cập nhật hình ảnh của brick sau mỗi lần va chạm nếu bị vỡ sẽ tự động xóa khỏi
     * Pane.
     */
    public void update() {
        if (frames != null && !frames.isEmpty()) {
            Image currentFrame;
            if (!is_block())
                // Lấy frame hiện tại và loại bỏ khỏi queue
                currentFrame = frames.poll();
            else {
                currentFrame = frames.peek();
                frames.add(frames.poll());

            }
            if (view instanceof ImageView imageView) {
                // Nếu view đã tồn tại thì chỉ thay đổi ảnh
                imageView.setImage(currentFrame);
                // System.out.println("Update brick image");
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
            // System.out.println("Destroy brick");
            // if (view != null && view.getParent() instanceof Pane p) {
            // destroy(p);
            // }
            destroy();
        }
    }

    // public void setActive_block_brick() {
    // active_block_brick = true;
    // }

    public boolean is_block() {
        if (type_brick > 10)
            return true;
        return false;
    }

    // public void block_update(double deltaTime) {
    // if (!active_block_brick)
    // return;
    // eslaped += deltaTime;
    // if (eslaped > 0.3) {

    // if (view instanceof ImageView imageView) {
    // // Nếu view đã tồn tại thì chỉ thay đổi ảnh
    // imageView.setImage(frames.peek());
    // // System.out.println("Update brick image");
    // } else {
    // // Nếu chưa có view thì tạo mới
    // ImageView imageView = new ImageView(frames.peek());
    // imageView.setFitWidth(width);
    // imageView.setFitHeight(height);
    // imageView.setLayoutX(x);
    // imageView.setLayoutY(y);
    // setView(imageView);
    // pane.getChildren().add(imageView);
    // active_block_brick = false;
    // }
    // frames.add(frames.poll());
    // block_frame_count++;
    // if (block_frame_count == frame_count) {
    // active_block_brick = false;
    // block_frame_count = 0;
    // }
    // eslaped = 0;
    // }

    // }
}
