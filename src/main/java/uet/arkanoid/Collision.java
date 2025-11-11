package uet.arkanoid;

import java.util.List;
import uet.arkanoid.Powerups.*;
import java.util.Iterator;

public class Collision {

    public static void checkPaddleCollision(GameController gameController) {
        List<Ball> balls = gameController.getBalls();
        Paddle paddle = gameController.getPaddle();

        // Kiểm tra va chạm AABB
        for (Ball ball : balls) {
            double r = ball.getWidth() / 2;
            if (ball.getX() + r >= paddle.getX()
                    && ball.getX() - r <= paddle.getX() + paddle.getWidth()
                    && ball.getY() + r >= paddle.getY()
                    && ball.getY() - r <= paddle.getY() + paddle.getHeight()) {

                // Tính độ chồng lấn theo X và Y
                double overlapX = Math.min(
                        ball.getX() + r - paddle.getX(),
                        paddle.getX() + paddle.getWidth() - (ball.getX() - r));
                double overlapY = Math.min(
                        ball.getY() + r - paddle.getY(),
                        paddle.getY() + paddle.getHeight() - (ball.getY() - r));

                // Xác định hướng va chạm chính
                if (overlapX < overlapY) {
                    // Va chạm cạnh bên paddle
                    if (ball.getX() < paddle.getX()) {
                        // Cạnh trái
                        ball.setX(paddle.getX() - ball.getWidth());
                    } else {
                        // Cạnh phải
                        ball.setX(paddle.getX() + paddle.getWidth());
                    }

                    double oldDxSign = Math.signum(ball.getDx());
                    if (oldDxSign == 0)
                        oldDxSign = 1;

                    double speed = Math.sqrt(ball.getDx() * ball.getDx() + ball.getDy() * ball.getDy());
                    double rx = ball.getWidth() / 2;

                    // Tính vị trí chạm tương đối theo trục Y (-1 = trên cùng, +1 = dưới cùng)
                    double contactY = Math.max(paddle.getY(),
                            Math.min(ball.getY() + rx, paddle.getY() + paddle.getHeight()));
                    double relativeY = (contactY - (paddle.getY() + paddle.getHeight() / 2.0))
                            / (paddle.getHeight() / 2.0);
                    relativeY = Math.max(-1.0, Math.min(1.0, relativeY));

                    // Góc lệch tối đa (so với trục ngang)
                    double maxTilt = Math.toRadians(60);
                    double tiltAngle = -relativeY * maxTilt;

                    // Nếu bóng chạm phần nửa dưới => bật xuống
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
                    // Va chạm mặt trên paddle
                    ball.setY(paddle.getY() - ball.getHeight());

                    double relativeIntersectX = (ball.getX() + r - (paddle.getX() + paddle.getWidth() / 2))
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
    }

    public static void checkBrickCollision(GameController gameController) {
        List<Ball> balls = gameController.getBalls();
        List<Brick> bricks = gameController.getBricks();

        for (Ball ball : balls) {
            Iterator<Brick> iterator = bricks.iterator();
            double ballR = ball.getWidth() / 2.0;
            double ballCenterX = ball.getX() + ballR;
            double ballCenterY = ball.getY() + ballR;

            double dx = ball.getDx();
            double dy = ball.getDy();
            double speed = Math.sqrt(dx * dx + dy * dy); // tốc độ gốc

            while (iterator.hasNext()) {
                Brick brick = iterator.next();

                double brickX = brick.getX();
                double brickY = brick.getY();
                double brickW = brick.getWidth();
                double brickH = brick.getHeight();

                if (ballCenterX + ballR >= brickX &&
                        ballCenterX - ballR <= brickX + brickW &&
                        ballCenterY + ballR >= brickY &&
                        ballCenterY - ballR <= brickY + brickH) {

                    double overlapLeft = (ballCenterX + ballR) - brickX;
                    double overlapRight = (brickX + brickW) - (ballCenterX - ballR);
                    double overlapTop = (ballCenterY + ballR) - brickY;
                    double overlapBottom = (brickY + brickH) - (ballCenterY - ballR);

                    double minOverlapX = Math.min(overlapLeft, overlapRight);
                    double minOverlapY = Math.min(overlapTop, overlapBottom);

                    if (minOverlapX < minOverlapY) {
                        if (overlapLeft < overlapRight) {
                            if (dx > 0) {
                                ball.setX(brickX - 2 * ballR);
                                dx = -dx;
                            }
                        } else {
                            if (dx < 0) {
                                ball.setX(brickX + brickW);
                                dx = -dx;
                            }
                        }
                    } else {
                        if (overlapTop < overlapBottom) {
                            if (dy > 0) {
                                ball.setY(brickY - 2 * ballR);
                                dy = -dy;
                            }
                        } else {
                            if (dy < 0) {
                                ball.setY(brickY + brickH);
                                dy = -dy;
                            }
                        }
                    }

                    if (Math.abs(minOverlapX - minOverlapY) < 1.0) {
                        dx = -dx;
                        dy = -dy;
                    }

                    // Chuẩn hóa lại vận tốc (giữ nguyên tốc độ)
                    double newSpeed = Math.sqrt(dx * dx + dy * dy);
                    dx = dx / newSpeed * speed;
                    dy = dy / newSpeed * speed;

                    ball.setDx(dx);
                    ball.setDy(dy);

                    PlaySound.soundEffect("/assets/sound/ballSound.mp3");

                    if (brick.frames_isEmpty()) {
                        iterator.remove();
                        Powerup newPowerup = dropPowerup(
                                brickX + brickW / 2.0,
                                brickY + brickH / 2.0,
                                gameController.getPaddle(),
                                gameController);
                        if (newPowerup != null) {
                            gameController.addPowerup(newPowerup);
                        }
                    }

                    brick.update();
                    gameController.getUser().addScore(1);
                    break;
                }
            }
        }
    }

    public static Powerup dropPowerup(double x, double y, Paddle paddle, GameController gameController) {
        double dropRate = Gameconfig.dropRate; // xác suất rơi power-up
        if (Math.random() < dropRate) {
            return null; // không rơi gì
        }

        // Danh sách các loại Powerup có thể rơi
        String[] types = {
                "Extend paddle",
                "Multi ball",
                "Shrink paddle",
                "Extra HP"
        };

        // Chọn ngẫu nhiên một loại
        int index = (int) (Math.random() * types.length);
        String randomType = types[index];

        double width = Gameconfig.width_powerup;
        double height = Gameconfig.height_powerup;
        // Khởi tạo powerup tương ứng
        Powerup newPowerup = null;
        switch (randomType) {
            case "Extend paddle":
                newPowerup = new ExtendPaddlePowerup(x, y, width, height, paddle, gameController.getPane());
                break;
            case "Shrink paddle":
                newPowerup = new ShrinkPaddlePowerup(x, y, width, height, paddle, gameController.getPane());
                break;
            case "Multi ball":
                newPowerup = new MultiBallPowerup(x, y, width, height, gameController);
                break;
            case "Extra HP":
                newPowerup = new ExtraHpPowerup(x, y, width, height, gameController);
            default:
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

    public static void checkBossCollision(GameController gameController) {
        List<Ball> balls = gameController.getBalls();
        Boss boss = gameController.getBoss();

        double bossR = boss.getWidth() / 2.0;
        double bossCenterX = boss.getX() + bossR;
        double bossCenterY = boss.getY() + bossR;

        for (Ball ball : balls) {
            double ballR = ball.getWidth() / 2.0;
            double ballCenterX = ball.getX() + ballR;
            double ballCenterY = ball.getY() + ballR;

            double dx = ball.getDx();
            double dy = ball.getDy();

            double distX = ballCenterX - bossCenterX;
            double distY = ballCenterY - bossCenterY;
            double distance = Math.sqrt(distX * distX + distY * distY);

            if (distance < ballR + bossR) {
                // Chuẩn hóa vector pháp tuyến
                double nx = distX / distance;
                double ny = distY / distance;

                // Tính phản xạ: v' = v - 2*(v·n)*n
                double dot = dx * nx + dy * ny;
                dx = dx - 2 * dot * nx;
                dy = dy - 2 * dot * ny;

                double overlap = (ballR + bossR) - distance;
                ball.setX(ball.getX() + nx * overlap * 0.5);
                ball.setY(ball.getY() + ny * overlap * 0.5);

                // Cập nhật vận tốc
                ball.setDx(dx);
                ball.setDy(dy);

                PlaySound.soundEffect("/assets/sound/ballSound.mp3");
                boss.subHealthPoint();
                gameController.getUser().addScore(1);

                if (boss.getHealthpoint() <= 0) {
                    boss.destroy();
                    break;
                }

                Powerup newPowerup = dropPowerup(
                        ballCenterX, ballCenterY,
                        gameController.getPaddle(), gameController);
                if (newPowerup != null) {
                    gameController.addPowerup(newPowerup);
                }
            }
        }
        checkEnergy(gameController);
    }

    private static void checkEnergy(GameController gameController) {
        List<Energy> energies = gameController.getBoss().getEnergies();
        Paddle paddle = gameController.getPaddle();
        User user = gameController.getUser();

        Iterator<Energy> iterator = energies.iterator();
        while (iterator.hasNext()) {
            Energy energy = iterator.next();

            // Tọa độ trung tâm của Energy (hình tròn)
            double eCenterX = energy.getX() + energy.getWidth() / 2.0;
            double eCenterY = energy.getY() + energy.getHeight() / 2.0;
            double radius = energy.getWidth() / 2.0;

            // Biên của paddle
            double pX = paddle.getX();
            double pY = paddle.getY();
            double pW = paddle.getWidth();
            double pH = paddle.getHeight();

            // Tìm điểm gần nhất trên paddle so với tâm hình tròn
            double nearestX = Math.max(pX, Math.min(eCenterX, pX + pW));
            double nearestY = Math.max(pY, Math.min(eCenterY, pY + pH));

            // Tính khoảng cách từ tâm đến điểm gần nhất
            double dx = eCenterX - nearestX;
            double dy = eCenterY - nearestY;
            double distanceSquared = dx * dx + dy * dy;

            // Nếu khoảng cách nhỏ hơn bán kính -> va chạm
            if (distanceSquared <= radius * radius) {
                // Giảm máu người chơi
                user.addHp(-1);

                // Xóa energy
                energy.destroy();
                iterator.remove();
            }
        }
    }

}