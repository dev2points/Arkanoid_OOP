package uet.arkanoid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Boss extends BaseObject {
    private transient Queue<Image> frames = new LinkedList<>();
    private int frame_count;
    private int healthpoint = Gameconfig.Boss_HP;
    private double dx = 3;
    private double elapsedTime = 0;
    private double animationTime = 0;
    private boolean isAnimating = false;

    List<Energy> energies = new ArrayList<>();

    private transient Rectangle healthBarBack;
    private transient Rectangle healthBarFront;

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
        createHealthBar();
    }

    public void update(double deltaTime) {
        elapsedTime += deltaTime;

        if (isAnimating) {
            animationTime += deltaTime;
            if (animationTime >= 0.2) { // đổi frame mỗi 0.2s
                animationTime = 0;
                frame_count++;
                if (frame_count >= 6) {
                    isAnimating = false;
                    frame_count = 0;
                    elapsedTime = 0;
                    shootEnergy();
                    restoreHealthPoint();
                }
                updateLayout();
            }
            return; // không di chuyển khi đang thực hiện animation
        }
        updateHealthBar();
        updatePosition(deltaTime);

        // Bắt đầu animation
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
        healthpoint = Math.min(healthpoint + 10, Gameconfig.Boss_HP);
    }

    public void restoreView() {
        // Load lại ảnh
        frames = new LinkedList<>();
        loadImage();
        // Cập nhật lại frame cuối cùng
        for (int i = 0; i < frame_count; i++) {
            frames.add(frames.poll());
        }
        for (Energy energy : energies)
            energy.loadImage();
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

    private void createHealthBar() {
        if (pane == null)
            return;

        double barWidth = width;
        double barHeight = 10;

        healthBarBack = new Rectangle(barWidth, barHeight, Color.GRAY);
        healthBarFront = new Rectangle(barWidth, barHeight, Color.RED);
        healthBarBack.setLayoutX(x);
        healthBarBack.setLayoutY(y - 15);
        healthBarFront.setLayoutX(x);
        healthBarFront.setLayoutY(y - 15);

        pane.getChildren().addAll(healthBarBack, healthBarFront);
    }

    private void updateHealthBar() {
        if (healthBarFront == null)
            return;
        double percent = (double) healthpoint / Gameconfig.Boss_HP;
        healthBarFront.setWidth(width * percent);
        if (healthBarBack != null && healthBarFront != null) {
            healthBarBack.setLayoutX(x);
            healthBarBack.setLayoutY(y - 15);
            healthBarFront.setLayoutX(x);
            healthBarFront.setLayoutY(y - 15);
        }
        // updateHealthBar();

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
