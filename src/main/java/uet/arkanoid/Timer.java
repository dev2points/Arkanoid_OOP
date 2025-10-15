package uet.arkanoid;

public class Timer {
    private long lastTime;
    private double deltaTime;
    private int fps;
    private long fpsTimer;
    private int frameCount;
    private double fpsti; 
    public Timer() {
        lastTime = System.nanoTime();
        fpsTimer = System.currentTimeMillis();
        fpsti = 1_000_000_000.0;
    }

    public void update() {
        long now = System.nanoTime();
        deltaTime = (now - lastTime) / fpsti * Gameconfig.FPS/60; // giây thực giữa 2 frame
        lastTime = now;

        
        // Tính FPS thực tế
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
