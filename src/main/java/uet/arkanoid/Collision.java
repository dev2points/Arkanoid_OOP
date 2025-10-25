package uet.arkanoid;

import java.util.List;

import uet.arkanoid.Powerups.*;

import java.util.Iterator;

public class Collision {

   public static void checkPaddleCollision(Ball ball, Paddle paddle) {
    double r = ball.getWidth() / 2;

    // Kiểm tra va chạm AABB
    if (ball.getX() + r >= paddle.getX()
            && ball.getX() - r <= paddle.getX() + paddle.getWidth()
            && ball.getY() + r >= paddle.getY()
            && ball.getY() - r <= paddle.getY() + paddle.getHeight()) {

        // Tính độ chồng lấn theo X và Y
        double overlapX = Math.min(
            ball.getX() + r - paddle.getX(),
            paddle.getX() + paddle.getWidth() - (ball.getX() - r)
        );
        double overlapY = Math.min(
            ball.getY() + r - paddle.getY(),
            paddle.getY() + paddle.getHeight() - (ball.getY() - r)
        );

        // Xác định hướng va chạm chính
        if (overlapX < overlapY) {
            // ✅ Va chạm cạnh bên paddle
            if (ball.getX() < paddle.getX()) {
                // Cạnh trái
                ball.setX(paddle.getX() - ball.getWidth());
            } else {
            // Cạnh phải
            ball.setX(paddle.getX() + paddle.getWidth());
            }

            double oldDxSign = Math.signum(ball.getDx());
            if (oldDxSign == 0) oldDxSign = 1;

            double speed = Math.sqrt(ball.getDx() * ball.getDx() + ball.getDy() * ball.getDy());
            double rx = ball.getWidth() / 2;

            // Tính vị trí chạm tương đối theo trục Y (-1 = trên cùng, +1 = dưới cùng)
            double contactY = Math.max(paddle.getY(), Math.min(ball.getY() + rx, paddle.getY() + paddle.getHeight()));
            double relativeY = (contactY - (paddle.getY() + paddle.getHeight() / 2.0)) / (paddle.getHeight() / 2.0);
            relativeY = Math.max(-1.0, Math.min(1.0, relativeY));

            // Góc lệch tối đa (so với trục ngang)
            double maxTilt = Math.toRadians(60);
            double tiltAngle = -relativeY * maxTilt;

            // ✅ Nếu bóng chạm phần nửa dưới => bật xuống
            boolean hitLowerHalf = (relativeY > 0);

            // Tính lại vận tốc
            double newDx = -oldDxSign * Math.cos(tiltAngle) * speed;
            double newDy = (hitLowerHalf ? Math.abs(Math.sin(tiltAngle) * speed)
                                        : -Math.abs(Math.sin(tiltAngle) * speed));

            // Đảm bảo có thành phần dọc tối thiểu để tránh kẹt
            double minVertical = 0.12 * speed;
            if (Math.abs(newDy) < minVertical) {
                newDy = (hitLowerHalf ? minVertical : -minVertical);
                double dxSign = Math.signum(newDx);
                double maybeDx = Math.sqrt(Math.max(0, speed * speed - newDy * newDy));
                newDx = dxSign * maybeDx;
            }

            ball.setDx(newDx);
            ball.setDy(newDy);
        }

        else {
            // ✅ Va chạm mặt trên paddle
            ball.setY(paddle.getY() - ball.getHeight());

            double relativeIntersectX =
                (ball.getX() + r - (paddle.getX() + paddle.getWidth() / 2))
                / (paddle.getWidth() / 2);
            relativeIntersectX = Math.max(-1.0, Math.min(1.0, relativeIntersectX));

            double bounceAngle = relativeIntersectX * Math.toRadians(60);
            double speed = Math.sqrt(ball.getDx() * ball.getDx() + ball.getDy() * ball.getDy());

            ball.setDx(speed * Math.sin(bounceAngle));
            ball.setDy(-Math.abs(speed * Math.cos(bounceAngle))); // luôn đi lên
        }

        PlaySound.soundEffect("/assets/sound/ballSound.mp3");
    }
}


    public static void checkBrickCollision(Ball ball, List<Brick> bricks, GameController gameController) {
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
                // Xác định độ chồng trên quả bóng
                double overlapX1 = (brickX + brickW) - (ballX); // chồng bên trái
                double overlapX2 = (ballX + ballR) - brickX; // chồng bên phải
                double overlapY1 = (brickY + brickH) - (ballY); // chồng phía trên
                double overlapY2 = (ballY + ballR) - brickY; // chồng phía dưới

                double minOverlapX = Math.min(overlapX1, overlapX2);
                double minOverlapY = Math.min(overlapY1, overlapY2);

                if (minOverlapX < minOverlapY) {
                    ball.setDx(-ball.getDx()); // va chạm bên trái/phải
                    // Đẩy bóng ra ngoài brick để tránh dính
                    if (overlapX2 < overlapX1)
                        ball.setX(brickX - ballR);
                    else
                        ball.setX(brickX + brickW);
                } else {
                    ball.setDy(-ball.getDy()); // va chạm trên/dưới
                    // Đẩy bóng ra ngoài brick để tránh dính
                    if (overlapY2 < overlapY1)
                        ball.setY(brickY - ballR);
                    else
                        ball.setY(brickY + brickH);
                }
                PlaySound.soundEffect("/assets/sound/ballSound.mp3");
                if (brick.frames_isEmpty()) {
                    iterator.remove();
                    Powerup newPowerup = dropPowerup(brickX + brickW / 2, brickY + brickH / 2,
                            gameController.getPaddle());
                    if (newPowerup != null)
                        gameController.addPowerup(newPowerup);
                }

                brick.update();

            }

        }
    }

    public static Powerup dropPowerup(double x, double y, Paddle paddle) {
        double dropRate = Gameconfig.dropRate; // xác suất rơi power-up
        if (Math.random() < dropRate) {
            return null; // không rơi gì
        }

        // Danh sách các loại Powerup có thể rơi
        String[] types = {
                "Extend paddle",
                "Shrink paddle"
        };

        // Chọn ngẫu nhiên một loại
        int index = (int) (Math.random() * types.length);
        String randomType = types[index];
        System.out.println("Powerup dropped: " + randomType);

        double width = Gameconfig.width_powerup;
        double height = Gameconfig.height_powerup;
        // Khởi tạo powerup tương ứng
        Powerup newPowerup = null;
        switch (randomType) {
            case "Extend paddle":
                newPowerup = new ExtendPaddlePowerup(x, y, width, height, paddle);
                break;
            case "Shrink paddle":
                newPowerup = new ShrinkPaddlePowerup(x, y, width, height, paddle);
                break;
            // bạn có thể thêm các loại khác ở đây
            default:
                System.out.println("Unknown powerup type: " + randomType);
                return null;
        }
        return newPowerup;
    }

    public static void checkPowerUpCollision(Paddle paddle, List<Powerup> Powerup, GameController gameController) {
        Iterator<Powerup> iterator = Powerup.iterator();

        while (iterator.hasNext()) {
            Powerup p = iterator.next();
            double px = p.getX(),
                    py = p.getY(),
                    pw = p.getWidth(),
                    ph = p.getHeight();

            double padX = paddle.getX(),
                    padY = paddle.getY(),
                    padW = paddle.getWidth(),
                    padH = paddle.getHeight();

            if (px + pw >= padX && px <= padX + padW &&
                    py + ph >= padY && py <= padY + padH) {

                p.active();
                p.destroy();
                iterator.remove();
                gameController.deletePowerup(p);
            } else {
                p.update();
            }
        }
    }

}