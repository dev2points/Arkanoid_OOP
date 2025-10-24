package uet.arkanoid;

import java.util.concurrent.StructuredTaskScope.FailedException;

public class Gameconfig {
    // Screen config
    public static final int screen_width = 800;
    public static final int screen_height = 600;

    // Brick config
    public static final int width_brick = 26;
    public static final int height_brick = 12;
    public static final int width_block_brick_1 = 52;
    public static final int height_block_brick_1 = 14;
    public static final int width_block_brick_2 = 14;
    public static final int height_block_brick_2 = 52;

    // Powerups config
    public static final double speed_powerup = 50;
    public static final double width_powerup = 32;
    public static final double height_powerup = 32;
    public static final double dropRate = 0.25;

    // Paddle config
    public static final double width_paddle = 200;
    public static final double height_paddle = 30;
    public static final int speed_paddle = 500;
    public static final double extend_ratio = 1.6;
    public static final double shrink_ratio = 0.625;

    // Ball config
    public static final double size_ball = 30;
    public static final double speed_ball = 400;

    // Time config
    public static final double FPS = 60.0;
}
