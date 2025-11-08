package uet.arkanoid;

import java.util.*;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;

public class Brick extends BaseObject {

    // Cache chung cho toàn bộ gạch (ảnh đã load)
    private static final Map<String, List<Image>> brickCache = new HashMap<>();

    private List<Image> frames = new ArrayList<>();
    private int width_frame;
    private int height_frame;
    private int frame_count;
    private int type_brick;
    private int mapNumber;
    private int frameIndex = 0; // dùng cho block animation

    public Brick(double x, double y, double width, double height, Pane pane, int type_brick, int map) {
        super(x, y, width, height, pane);
        this.type_brick = type_brick;
        this.mapNumber = map;

        check_type(type_brick);
        loadFrames(type_brick);

        // Khởi tạo ImageView chỉ 1 lần
        if (!frames.isEmpty()) {
            ImageView imageView = new ImageView(frames.get(0));
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            imageView.setLayoutX(x);
            imageView.setLayoutY(y);
            setView(imageView);
            pane.getChildren().add(imageView);
        }
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
    }

    /** Load hoặc dùng lại frame từ cache */
    private void loadFrames(int type_brick) {
        String path = "/assets/image/bricks/map" + mapNumber + "/brick_" + type_brick + ".png";

        // Nếu đã có cache → dùng lại
        if (brickCache.containsKey(path)) {
            frames = brickCache.get(path);
            return;
        }

        // Nếu chưa → load mới
        Image sheet = new Image(getClass().getResource(path).toExternalForm());
        PixelReader reader = sheet.getPixelReader();
        if (reader == null) {
            System.out.println("ERROR: Cannot load brick image: " + path);
            return;
        }

        List<Image> frameList = new ArrayList<>();
        for (int y = 0; y < frame_count; y++) {
            WritableImage frame = new WritableImage(reader, 0, y * height_frame, width_frame, height_frame);
            frameList.add(frame);
        }

        // Lưu vào cache để viên khác dùng lại
        brickCache.put(path, frameList);
        frames = frameList;
    }

    @Override
    public void update() {
        if (frames == null || frames.isEmpty()) return;

        Image currentFrame;

        if (is_block()) {
            // block xoay vòng animation
            currentFrame = frames.get(frameIndex);
            frameIndex = (frameIndex + 1) % frames.size();
        } else {
            // gạch thường nứt dần rồi biến mất
            currentFrame = frames.get(0);
            frames.remove(0);
            if (frames.isEmpty()) {
                destroy();
                return;
            }
        }

        // Cập nhật ảnh hiển thị
        if (view instanceof ImageView imageView) {
            imageView.setImage(currentFrame);
        }
    }

    public boolean is_block() {
        return type_brick > 10;
    }
    public boolean frames_isEmpty(){
        return frames.isEmpty();
    }
    @Override
    public void destroy() {
        if (pane != null && view != null) {
            pane.getChildren().remove(view);
        }
        frames.clear();
    }
}
