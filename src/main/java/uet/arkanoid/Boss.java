package uet.arkanoid;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Boss extends BaseObject {
    // Load tất cả frame 1 lần duy nhất
    private static final Image[] FRAMES = new Image[6];

    static {
        for (int i = 0; i < 6; i++) {
            String path = "/assets/image/boss/boss" + (i + 1) + ".png";
            FRAMES[i] = new Image(Boss.class.getResource(path).toExternalForm());
        }
    }

    private int frameIndex = 0;
    private int healthpoint = Gameconfig.Boss_HP;
    private double dx = 3;
    private double elapsedTime = 0; // thời gian tổng cộng
    private double animationTime = 0; // thời gian hiển thị từng frame
    private boolean isAnimating = false; // trạng thái animation

    private List<Energy> energies = new ArrayList<>();

    public Boss(int x, int y, int width, int height) {
        super(x, y, width, height);
        initView();
    }

    private void initView() {
        ImageView imageView = new ImageView(FRAMES[0]);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        setView(imageView);
        pane.getChildren().add(imageView);
    }

    public void update(double deltaTime) {
        elapsedTime += deltaTime;

        if (isAnimating) {
            animationTime += deltaTime;
            if (animationTime >= 0.2) { // đổi frame sau 0.2s
                animationTime = 0;
                updateLayout();
                frameIndex++;
                if (frameIndex >= FRAMES.length) {
                    isAnimating = false;
                    frameIndex = 0;
                    elapsedTime = 0;
                    shootEnergy();
                    restoreHealthPoint();
                }
            }
            return; // không di chuyển khi đang animation
        }

        updatePosition(deltaTime);

        // Sau 10 giây thì bắt đầu animation
        if (elapsedTime >= 10.0) {
            isAnimating = true;
            animationTime = 0;
            frameIndex = 0;
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

        if (view instanceof ImageView imageView) {
            imageView.setLayoutX(x);
            imageView.setLayoutY(y);
        }
    }

    private void updateLayout() {
        Image currentFrame = FRAMES[frameIndex % FRAMES.length];
        if (view instanceof ImageView imageView) {
            imageView.setImage(currentFrame);
        }
    }

    public void subHealthPoint() {
        healthpoint--;
    }

    private void restoreHealthPoint() {
        healthpoint += 10;
    }

    public void shootEnergy() {
        double centerX = x + width / 2;
        double centerY = y + height / 2;
        for (int i = 0; i < 10; i++) {
            double angle = Math.random() * 180;
            Energy e = new Energy(centerX, centerY, angle, Gameconfig.SPEED_ENERGY);
            energies.add(e);
        }
    }

    private void updateEnergies(double deltaTime) {
        for (Energy e : energies) {
            e.update(deltaTime);
        }
        energies.removeIf(e -> !e.isActive());
    }

    public void restoreView() {
        updateLayout(); // chỉ cần update frame hiện tại
    }

    // Getters và setters
    public int getHealthpoint() { return healthpoint; }
    public void setHealthpoint(int healthpoint) { this.healthpoint = healthpoint; }
    public List<Energy> getEnergies() { return energies; }
    public void setEnergies(List<Energy> energies) { this.energies = energies; }
    public double getDx() { return dx; }
    public void setDx(double dx) { this.dx = dx; }
    public boolean isAnimating() { return isAnimating; }
    public void setAnimating(boolean animating) { this.isAnimating = animating; }
}
