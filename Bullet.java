/**
 * Created by panasyuk on 16.06.2015.
 */
public class Bullet {
    private int x;
    private int y;
    private int speed = 1;
    private int dirrect;

    Bullet() {
        this.x = -100;
        this.y = -100;
        this.speed = 10;
        this.dirrect = 1;
    }
    Bullet (int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.dirrect = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirrect() {
        return dirrect;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDirrect(int dirrect) {
        this.dirrect = dirrect;
    }
    public void destroyBullet(){
        this.x = -100;
        this.y = -100;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void updateX (int x) {
        this.x += x;
    }
    public void updateY (int y) {
        this.y += y;
    }
}
