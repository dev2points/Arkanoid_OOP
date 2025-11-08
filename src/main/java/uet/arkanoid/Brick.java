package uet.arkanoid;

import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import java.util.LinkedList;
import java.util.Queue;

public class Brick extends BaseObject {

    // Dùng transient để tránh lỗi khi serialize
    private transient Queue<Image> frames = new LinkedList<>();
    private int widthFrame;
    private int heightFrame;
    private int frameCount;
    private int typeBrick;
    private int mapNumber;
    private int framesRemaining;

    public Brick(double x, double y, double width, double height, Pane pane, int typeBrick, int mapNumber) {
        super(x, y, width, height, pane);
        this.typeBrick = typeBrick;
        this.mapNumber = mapNumber;

        setupBrickType();
        loadFrames();
        initializeView();
        update();
    }

    /** Xác định loại gạch và kích thước frame */
    private void setupBrickType() {
        if (typeBrick < 10) {
            frameCount = typeBrick;
            widthFrame = Gameconfig.width_brick;
            heightFrame = Gameconfig.height_brick;
        } else if (typeBrick < 20) {
            frameCount = 4;
            widthFrame = Gameconfig.width_block_brick_1;
            heightFrame = Gameconfig.height_block_brick_1;
        } else {
            frameCount = 4;
            widthFrame = Gameconfig.width_block_brick_2;
            heightFrame = Gameconfig.height_block_brick_2;
        }
    }

    /** Load toàn bộ frame của gạch */
    private void loadFrames() {
        frames.clear();

        String path = String.format("/assets/image/bricks/map%d/brick_%d.png", mapNumber, typeBrick);
        Image sheet;

        try {
            sheet = new Image(getClass().getResource(path).toExternalForm());
        } catch (Exception e) {
            System.err.println("⚠️ Không thể load ảnh brick: " + path);
            return;
        }

        PixelReader reader = sheet.getPixelReader();
        if (reader == null) {
            System.err.println("⚠️ PixelReader null cho ảnh: " + path);
            return;
        }

        for (int i = 0; i < frameCount; i++) {
            WritableImage frame = new WritableImage(reader, 0, i * heightFrame, widthFrame, heightFrame);
            frames.add(frame);
        }

        framesRemaining = frameCount;
    }

    /** Khởi tạo ImageView lần đầu (để tránh tạo lại mỗi frame) */
    private void initializeView() {
        if (frames.isEmpty()) return;

        Image firstFrame = frames.peek();
        ImageView imageView = new ImageView(firstFrame);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);

        setView(imageView);
        pane.getChildren().add(imageView);
    }

    @Override
    public void update() {
        if (frames == null || frames.isEmpty()) {
            destroy();
            return;
        }

        Image currentFrame;

        // Brick thường bị nứt dần → giảm số frame còn lại
        if (!isBlock()) {
            currentFrame = frames.poll();
            framesRemaining--;
        } else {
            // Block có animation lặp lại
            currentFrame = frames.peek();
            frames.add(frames.poll());
        }

        // Cập nhật ảnh hiển thị
        if (view instanceof ImageView imageView) {
            imageView.setImage(currentFrame);
        }
    }

    public boolean isBlock() {
        return typeBrick > 10;
    }

    public boolean isFramesEmpty() {
        return frames.isEmpty();
    }

    public int getFrameCount() {
        return frameCount;
    }

    /** Khôi phục lại trạng thái ảnh sau khi load lại game */
    public void restoreFrame() {
        loadFrames();

        // Bỏ qua số frame đã bị phá
        for (int i = 1; i < frameCount - framesRemaining; i++) {
            frames.poll();
        }

        update();
    }

    @Override
    public void destroy() {
        if (pane != null && view != null) {
            pane.getChildren().remove(view);
        }
        frames.clear();
    }
 
}
