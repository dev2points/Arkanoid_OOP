package uet.arkanoid;

import java.io.Serializable;

public class User implements Serializable {
    private int hp;
    private int score;

    public User(int hp, int score) {
        this.hp = hp;
        this.score = score;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(hp, 0);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = Math.max(score, 0);
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public void loseHp(int amount) {
        this.hp = hp - amount;
    }

    public void addHp(int amount) {
        this.hp = hp + amount;
    }

    public boolean isDead() {
        return hp <= 0;
    }
}
