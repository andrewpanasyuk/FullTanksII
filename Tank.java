/**
 * Created by panasyuk on 16.06.2015.
 */
public class Tank {
    private int x;
    private int y;
    private int direction;
    private int speed = 10;
    private ActionField af;
    private Field bf;
    private ControlField cf;

    public Tank(ActionField af, Field bf) {
        this(bf, af, 128, 512, 1);
    }

    public Tank(Field bf, ActionField af, int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.af = af;
        this.bf = bf;
    }

    public void turn(int direction) throws Exception {
        this.direction = direction;
        af.processTurn(this);
    }

    public void move() throws Exception {
        af.processMove(this);
    }

    public void fire() throws Exception {
        Bullet bullet = new Bullet((x + 25), (y + 25), direction);
        af.processFire(bullet);

    }

    public void moveRandom() throws Exception { // ---------------------------------
        while (true) {
            int random = Generation.gen(1, 4);
            this.direction = random;
            if (cf.controlTank(bf, this)) {
                move();
            }
        }
    }

    public void moveRandomWoll() throws Exception { // ---------------------------------
        while (true) {
            int random = Generation.gen(1, 4);
            this.direction = random;
            if (ControlField.controlTank(bf, this)) {
                if (ControlField.controlWoll(bf, this)) {
                    move();
                }
            }
        }
    }

    public void moveRandomWollFire() throws Exception { // ---------------------------------
        while (true) {
            int random = Generation.gen(1, 4);
            this.direction = random;
            if (ControlField.controlTank(bf, this)) {
                if (ControlField.controlWoll(bf, this) == false) {
                    fire();
                }
                move();

            }
        }
    }

    public void moveToQuadrant(int v, int h) throws Exception {
        String newQadrant = af.getQuadrantXY(v, h);
        int separator = newQadrant.indexOf("_");
        int goalY = Integer.parseInt(newQadrant.substring(0, separator));
        int goalX = Integer.parseInt(newQadrant.substring(separator + 1));
        if (x < goalX) {
            while (x < goalX) {
                this.direction = 4;
                move();
            }
        } else {
            while (x > goalX) {
                this.direction = 3;
                move();
            }
        }

        if (y < goalY) {
            while (y < goalY) {
                this.direction = 2;
                move();
            }
        } else {
            while (y > goalY) {
                this.direction = 1;
                move();
            }
        }
        System.out.println(newQadrant);
    }

    public int getX() {
        return x;
    }

    public void updateX(int x) {
        this.x += x;
    }

    public void updateY(int y) {
        this.y += y;
    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

}
