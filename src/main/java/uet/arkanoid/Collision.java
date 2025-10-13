package uet.arkanoid;

import java.util.List;
import java.util.Iterator;

public class Collision {

    // public static void checkWallCollision(Ball ball, Paddle paddle) {
    // double r = ball.getWidth() / 2;

    // if (ball.getX() - r <= 0 ||
    // ball.getX() + r >= ball.()) {
    // ball.setDx(ball.getDx() * -1);
    // }

    // if (ball.getY() - r <= 0) {
    // ball.setDy(ball.getDy() * -1);

    // }
    // }

    public static void checkPaddleCollision(Ball ball, Paddle paddle) {
        double r = ball.getWidth() / 2;

        if (ball.getX() + r >= paddle.getX()
                && ball.getX() - r <= paddle.getX() + paddle.getWidth()
                && ball.getY() + r >= paddle.getY()
                && ball.getY() - r <= paddle.getY() + paddle.getHeight()) {

            ball.setY(paddle.getY() - ball.getHeight());
            double hitPos = (ball.getX() - paddle.getX()) / paddle.getWidth() - 0.5; // -0.5 -> +0.5
            ball.setDx(hitPos * 6);
            ball.setDy(-Math.abs(ball.getDy()));

        }

    }

    public static void checkPowerUpCollision(Paddle paddle, List<Powerups> powerUps) {
        Iterator<Powerups> iterator = powerUps.iterator();

        while (iterator.hasNext()) {
            Powerups p = iterator.next();
            double px = p.getX(), py = p.getY(), pw = p.getWidth(), ph = p.getHeight();
            double padX = paddle.getX(), padY = paddle.getY(), padW = paddle.getWidth(), padH = paddle.getHeight();

            if (px + pw >= padX && px <= padX + padW &&
                    py + ph >= padY && py <= padY + padH) {

                // applyPowerUp(paddle, p.getTypePowerup());
                // p.destroy(paddle.getPane());
                iterator.remove();
            }
        }
    }

    public static void checkBrickCollision(Ball ball, List<Brick> bricks) {
        Iterator<Brick> iterator = bricks.iterator();

        while (iterator.hasNext()) {
            Brick brick = iterator.next();

            double ballX = ball.getX();
            double ballY = ball.getY();
            double ballR = Gameconfig.size_ball;
            double brickX = brick.getX();
            double brickY = brick.getY();
            double brickW = brick.getWidth();
            double brickH = brick.getHeight();

            if (ballX + ballR >= brickX &&
                    ballX <= brickX + brickW &&
                    ballY + ballR >= brickY &&
                    ballY <= brickY + brickH) {

                if (Math.abs((ballY + ballR) - brickY) < 5 || Math.abs(ballY - (brickY + brickH)) < 5)
                    ball.setDy(-ball.getDy());
                else
                    ball.setDx(-ball.getDx());

                if (brick.frames.isEmpty()) {
                    iterator.remove();
                }
                brick.update();

            }
        }
    }
}