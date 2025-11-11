package uet.arkanoid;

import java.util.Queue;
import java.util.LinkedList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Brick extends BaseObject {

    private transient Queue<Image> frames = new LinkedList<>();
    private int width_frame;
    private int height_frame;
    private int frame_count;
    private int type_brick;
    private int mapNumber;
    private int frame_remaining;

    // Constructor khi tạo mới
    public Brick(double x, double y, double width, double height, Pane pane, int type_brick, int map) {
        super(x, y, width, height, pane);
        this.type_brick = type_brick;
        this.mapNumber = map;
        check_type(type_brick);
        loadbricks(); // Gọi sau khi pane được set
        update(); // Để hiển thị frame đầu tiên
    }

    // Constructor khi deserialized
    public Brick(double x, double y, double width, double height, int type_brick, int mapNumber, int frame_remaining) {
        super(x, y, width, height);
        this.type_brick = type_brick;
        this.mapNumber = mapNumber;
        this.frame_remaining = frame_remaining;
        check_type(type_brick);
    }

    public boolean frames_isEmpty() {
        return frames.isEmpty() && frame_remaining <= 0; // Check cả frame_remaining
    }

    private void check_type(int type_brick) {
        if (type_brick < 10) {
            frame_count = type_brick; // số frame nứt
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
        if (frame_remaining == 0)
            frame_remaining = frame_count; // Khởi tạo nếu chưa có
    }

    private void loadbricks() {
        String path = "/assets/image/bricks/map"
                + mapNumber
                + "/brick_"
                + type_brick
                + ".png";

        Image sheet = new Image(getClass().getResource(path).toExternalForm());
        PixelReader reader = sheet.getPixelReader();

        if (reader == null) {
            System.out.println("ERROR: Cannot load brick image: " + path);
            return;
        }
        frames = new LinkedList<>(); // Đảm bảo frames được khởi tạo lại
        for (int y = 0; y < frame_count; y++) {
            WritableImage frame = new WritableImage(
                    reader,
                    0,
                    y * height_frame,
                    width_frame,
                    height_frame);
            frames.add(frame);
        }
    }

    @Override
    public void update() {
        if (pane == null)
            return; // Không cập nhật nếu pane chưa được gán

        if (frames != null && !frames.isEmpty()) {
            Image currentFrame;

            if (!is_block()) {
                currentFrame = frames.poll();
                frame_remaining--;
            } else {
                currentFrame = frames.peek();
                frames.add(frames.poll()); // Quay vòng block brick
            }

            if (view instanceof ImageView imageView) {
                imageView.setImage(currentFrame);
            } else {
                ImageView imageView = new ImageView(currentFrame);
                imageView.setFitWidth(width);
                imageView.setFitHeight(height);
                imageView.setLayoutX(x);
                imageView.setLayoutY(y);
                setView(imageView);
                pane.getChildren().add(imageView);
            }
        } else if (view != null) { // Nếu frames rỗng và còn view, thì destroy nó
            destroy();
        }
    }

    public boolean is_block() {
        return type_brick >= 10;
    }

    public int getFramecount() {
        return frame_count;
    }

    @Override
    public void restoreView(Pane parentPane) {
        super.restoreView(parentPane); // Set lại pane
        loadbricks(); // Load lại các frame ảnh
        // Di chuyển đến frame_remaining hiện tại
        for (int i = 0; i < frame_count - frame_remaining; i++) {
            if (!frames.isEmpty() && !is_block()) {
                frames.poll();
            } else if (is_block()) {
                frames.add(frames.poll()); // Quay vòng cho block brick
            }
        }
        update(); // Cập nhật hình ảnh lên view
    }
}