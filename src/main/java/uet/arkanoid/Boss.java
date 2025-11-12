package uet.arkanoid;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    private double elapsedTime = 0;
    private double animationTime = 0;
    private boolean isAnimating = false;

    private List<Energy> energies = new ArrayList<>();

    private transient Rectangle healthBarBack;
    private transient Rectangle healthBarFront;

    public Boss(int x, int y, int width, int height, Pane pane) {
        super(x, y, width, height, pane);
        initView();
        createHealthBar();
    }

    private void initView() {
        ImageView imageView = new ImageView(FRAMES[0]);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        setView(imageView);
        if (pane != null)
            pane.getChildren().add(imageView);
    }

    public void update(double deltaTime) {
        elapsedTime += deltaTime;

        if (isAnimating) {
            animationTime += deltaTime;
            if (animationTime >= 0.2) {
                animationTime = 0;
                frameIndex++;
                if (frameIndex >= FRAMES.length) {
                    isAnimating = false;
                    frameIndex = 0;
                    elapsedTime = 0;
                    shootEnergy();
                    restoreHealthPoint();
                }
                updateLayout();
            }
            return;
        }

        updatePosition(deltaTime);
        updateHealthBar();
        updateEnergies(deltaTime);

        if (elapsedTime >= 10.0) {
            isAnimating = true;
            animationTime = 0;
            frameIndex = 0;
        }
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
        if (view instanceof ImageView imageView) {
            imageView.setImage(FRAMES[frameIndex % FRAMES.length]);
        }
    }

    private void updateEnergies(double deltaTime) {
        for (Energy e : energies)
            e.update(deltaTime);
        energies.removeIf(e -> !e.isActive());
    }

    public void shootEnergy() {
        double centerX = x + width / 2;
        double centerY = y + height / 2;
        for (int i = 0; i < 10; i++) {
            double angle = Math.random() * 180;
            energies.add(new Energy(centerX, centerY, angle, Gameconfig.SPEED_ENERGY, pane));
        }
    }

    public void subHealthPoint() {
        healthpoint--;
    }
    public void subHealthPoint3() {
        healthpoint-=3;
    }

    private void restoreHealthPoint() {
        healthpoint = Math.min(healthpoint + 10, Gameconfig.Boss_HP);
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
    }

    public void restoreView() {
        initView();
        updateLayout(); // Chỉ cần cập nhật frame hiện tại
        for (Energy e : energies)
            e.initView();
        createHealthBar();
        updateHealthBar();
    }

    // Getters & setters
    public int getHealthpoint() {
        return healthpoint;
    }

    public void setHealthpoint(int healthpoint) {
        this.healthpoint = healthpoint;
    }

    public List<Energy> getEnergies() {
        return energies;
    }

    public void setEnergies(List<Energy> energies) {
        this.energies = energies;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public void setAnimating(boolean animating) {
        this.isAnimating = animating;
    }
}
