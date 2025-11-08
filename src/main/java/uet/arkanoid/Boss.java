package uet.arkanoid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Boss extends BaseObject {
    private transient Queue<Image> frames = new LinkedList<>();
    private int frame_count;
    private int healthpoint = Gameconfig.Boss_HP;
    private double dx = 3;
    private double elapsedTime = 0; // thời gian tổng cộng
    private double animationTime = 0; // thời gian hiển thị từng frame
    private boolean isAnimating = false; // trạng thái animation

    List<Energy> energies = new ArrayList<>();

    public Boss(int x, int y, int width, int height) {
        super(x, y, width, height);
        frame_count = 0;
        loadImage();
        updateLayout();
    }

    // Boss có 6 frame animation
    public void loadImage() {
        for (int i = 1; i <= 6; i++) {
            String path = "/assets/image/boss/boss" + i + ".png";
            Image image = new Image(getClass().getResource(path).toExternalForm());
            frames.add(image);
        }
    }

    public void update(double deltaTime) {
        elapsedTime += deltaTime;

        if (isAnimating) {
            animationTime += deltaTime;
            if (animationTime >= 0.2) { // sau mỗi 0.25 giây đổi frame
                animationTime = 0;
                updateLayout();
                frame_count++;
                if (frame_count >= 6) {
                    isAnimating = false;
                    frame_count = 0;
                    elapsedTime = 0;
                    shootEnergy();
                    restoreHealthPoint();
                }
            }
            return; // không di chuyển khi đang thực hiện animation
        }

        updatePosition(deltaTime);

        // Sau 6 giây thì dừng lại và bắt đầu animation
        if (elapsedTime >= 10.0) {
            isAnimating = true;
            animationTime = 0;
            frame_count = 0;
        }

        updateEnergies(deltaTime);
    }

    private void updatePosition(double deltaTime) {
        if (x <= 0) {
            x = 0;
            dx = -dx;
        }
        if (x + width >= Gameconfig.screen_width) {
            x = Gameconfig.screen_width - width;
            dx = -dx;
        }
        x += dx * deltaTime;

        // Cập nhật vị trí ảnh
        if (view != null) {
            view.setLayoutX(x);
            view.setLayoutY(y);
        }
    }

    private void updateLayout() {
        if (frames == null || frames.isEmpty()) {
            System.out.println("Boss frame empty");
            return;
        }

        Image currentFrame = frames.poll();
        frames.add(currentFrame); // xoay vòng frame
        // Cập nhật view
        if (view instanceof ImageView imageView) {
            imageView.setImage(currentFrame);
        } else {
            // Tạo view mới
            ImageView imageView = new ImageView(currentFrame);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            imageView.setLayoutX(x);
            imageView.setLayoutY(y);
            setView(imageView);
            pane.getChildren().add(imageView);
        }
    }

    public void subHealthPoint() {
        healthpoint--;
    }

    private void restoreHealthPoint() {
        healthpoint += 10;
    }

    public void restoreView() {
        // Load lại ảnh
        loadImage();
        // Cập nhật lại frame cuối cùng
        for (int i = 0; i < frame_count; i++) {
            frames.add(frames.poll());
        }

        updateLayout();
    }

    private void updateEnergies(double deltaTime) {
        for (Energy e : energies) {
            e.update(deltaTime);
        }
        energies.removeIf(e -> !e.isActive());
    }

    public void shootEnergy() {
        double centerX = x + width / 2;
        double centerY = y + height / 2;
        for (int i = 0; i < 10; i++) {
            // Chọn góc bắn ngẫu nhiên
            double angle = Math.random() * 180;

            Energy e = new Energy(centerX, centerY, angle, Gameconfig.SPEED_ENERGY);
            energies.add(e);
        }
    }

    public int getFrame_count() {
        return this.frame_count;
    }

    public void setFrame_count(int frame_count) {
        this.frame_count = frame_count;
    }

    public int getHealthpoint() {
        return this.healthpoint;
    }

    public void setHealthpoint(int healthpoint) {
        this.healthpoint = healthpoint;
    }

    public double getDx() {
        return this.dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getElapsedTime() {
        return this.elapsedTime;
    }

    public void setElapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public double getAnimationTime() {
        return this.animationTime;
    }

    public void setAnimationTime(double animationTime) {
        this.animationTime = animationTime;
    }

    public boolean isIsAnimating() {
        return this.isAnimating;
    }

    public boolean getIsAnimating() {
        return this.isAnimating;
    }

    public void setIsAnimating(boolean isAnimating) {
        this.isAnimating = isAnimating;
    }

    public List<Energy> getEnergies() {
        return this.energies;
    }

    public void setEnergies(List<Energy> energies) {
        this.energies = energies;
    }
}
