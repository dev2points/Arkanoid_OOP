package uet.arkanoid;

/**
 * Lớp Timer dùng để quản lý thời gian và tính FPS.
 * Nhật phụ trách.
 */
public class Timer {
    private long lastTime;
    private double deltaTime;
    private int fps;
    private long fpsTimer;
    private int frameCount;

    public Timer() {
        lastTime = System.nanoTime();
        fpsTimer = System.currentTimeMillis();
        deltaTime = 0;
    }

    public void update() {
        long now = System.nanoTime();
        deltaTime = (now - lastTime) / 1_000_000_000.0;
        lastTime = now;

        frameCount++;
        if (System.currentTimeMillis() - fpsTimer >= 1000) {
            fps = frameCount;
            frameCount = 0;
            fpsTimer += 1000;
            // System.out.println("FPS: " + fps);
        }
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public int getFPS() {
        return fps;
    }
}
