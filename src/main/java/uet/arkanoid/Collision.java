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

        // Kiểm tra va chạm
        if (ball.getX() + r >= paddle.getX()
                && ball.getX() - r <= paddle.getX() + paddle.getWidth()
                && ball.getY() + r >= paddle.getY()
                && ball.getY() - r <= paddle.getY() + paddle.getHeight()) {

            // Set lại vị trí ball
            ball.setY(paddle.getY() - ball.getHeight());

            // Tính vị trí va chạm tương đối
            double relativeIntersectX = (ball.getX() + r - (paddle.getX() + paddle.getWidth() / 2))
                    / (paddle.getWidth() / 2);

            // Giới hạn để không quá mạnh
            relativeIntersectX = Math.max(-1.0, Math.min(1.0, relativeIntersectX));

            // Góc phản xạ (radians): 75° max lệch sang 2 bên
            double bounceAngle = relativeIntersectX * Math.toRadians(60);

            // Tốc độ bóng hiện tại
            double speed = Math.sqrt(ball.getDx() * ball.getDx() + ball.getDy() * ball.getDy());

            // Cập nhật vận tốc theo góc bật
            ball.setDx(speed * Math.sin(bounceAngle));
            ball.setDy(-Math.abs(speed * Math.cos(bounceAngle))); // luôn đi lên

            // Tuỳ chọn: tăng nhẹ tốc độ sau mỗi lần đập
            // ball.setDx(ball.getDx() * 1.05);
            // ball.setDy(ball.getDy() * 1.05);

            PlaySound.soundEffect("/assets/sound/ballSound.mp3");
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

                double overlapX1 = (brickX + brickW) - (ballX - ballR); // chồng bên trái
                double overlapX2 = (ballX + ballR) - brickX; // chồng bên phải
                double overlapY1 = (brickY + brickH) - (ballY - ballR); // chồng phía trên
                double overlapY2 = (ballY + ballR) - brickY; // chồng phía dưới

                double minOverlapX = Math.min(overlapX1, overlapX2);
                double minOverlapY = Math.min(overlapY1, overlapY2);

                if (minOverlapX < minOverlapY) {
                    ball.setDx(-ball.getDx()); // va chạm bên trái/phải
                    // Đẩy bóng ra ngoài brick để tránh dính
                    if (overlapX2 < overlapX1)
                        ball.setX(brickX - ballR - 0.1);
                    else
                        ball.setX(brickX + brickW + ballR + 0.1);
                } else {
                    ball.setDy(-ball.getDy()); // va chạm trên/dưới
                    // Đẩy bóng ra ngoài brick để tránh dính
                    if (overlapY2 < overlapY1)
                        ball.setY(brickY - ballR - 0.1);
                    else
                        ball.setY(brickY + brickH + ballR + 0.1);
                }

                if (brick.frames_isEmpty()) {
                    iterator.remove();
                }
                PlaySound.soundEffect("/assets/sound/ballSound.mp3");
                brick.update();
                Powerups.maybeDrop(brickX + brickW / 2, brickY + brickH / 2);

            }

        }
    }
}