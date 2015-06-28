/**
 * Created by panasyuk on 16.06.2015.
 */
public class Bullet {
    private int x;
    private int y;
    private int speed = 1;
    private Direction dirrect;
    private int armorPiercing;

    Bullet() {
        this.x = -100;
        this.y = -100;
        this.speed = 10;
        this.dirrect = Direction.UP;
    }
    Bullet (int x, int y, Direction dirrect, int armorPiercing) {
        this.x = x;
        this.y = y;
        this.dirrect = dirrect;
        this.armorPiercing = armorPiercing;

    }

    public int getArmorPiercing() {
        return armorPiercing;
    }

    public void setArmorPiercing(int armorPiercing) {
        this.armorPiercing = armorPiercing;
    }

    public int getSpeed() {
        return speed;
    }

    public Direction getDirrect() {
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

    public void setDirrect(Direction dirrect) {
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
