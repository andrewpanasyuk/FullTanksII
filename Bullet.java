/**
 * Created by panasyuk on 16.06.2015.
 */
public class Bullet {
    private int x;
    private int y;
    private int speed = 10;
    private Direction dirrect;
    private int armorPiercing;
    //private String nameTank;
    private AbstractTank tank;

    Bullet() {
        this.x = -100;
        this.y = -100;
        this.speed = 10;
        this.dirrect = Direction.UP;
    }
    Bullet (String nameTank, int x, int y, Direction dirrect, int armorPiercing) {
        //this.nameTank = nameTank;
        this.x = x;
        this.y = y;
        this.dirrect = dirrect;
        this.armorPiercing = armorPiercing;

    }
    Bullet (AbstractTank tank) {
        this.tank = tank;
        this.x = tank.getX()+25;
        this.y = tank.getY()+25;
        this.dirrect = tank.getDirection();
        this.armorPiercing = tank.getPower();

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

        setY(this.y + y);
    }

    public AbstractTank getTank() {
        return tank;
    }
}
