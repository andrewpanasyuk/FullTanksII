/**
 * Created by panasyuk on 16.06.2015.
 */
public class Tank {
    private int x;
    private int y;
    private Direction direction;
    protected int speed = 2;
    private ActionField af;
    protected Field bf;
    private ControlField cf;

    public Tank() {

    }

    public Tank(ActionField af, Field bf) {
        this(bf, af, 128, 512, Direction.UP);
    }

    public Tank(Field bf, ActionField af, int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.af = af;
        this.bf = bf;
    }

    public void turn(Direction direction) throws Exception {
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
            if (random == 1) {
                this.direction = Direction.UP;
            } else if (random == 2) {
                this.direction = Direction.DOWN;
            } else if (random == 3) {
                this.direction = Direction.LEFT;
            } else {
                this.direction = Direction.RIGHT;
            }
            if (cf.controlTank(bf, this)) {
                move();
            }
        }
    }

    public void moveRandomWoll() throws Exception { // ---------------------------------
        while (true) {
            int random = Generation.gen(1, 4);
            if (random == 1) {
                this.direction = Direction.UP;
            } else if (random == 2) {
                this.direction = Direction.DOWN;
            } else if (random == 3) {
                this.direction = Direction.LEFT;
            } else {
                this.direction = Direction.RIGHT;
            }
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
            if (random == 1) {
                this.direction = Direction.UP;
            } else if (random == 2) {
                this.direction = Direction.DOWN;
            } else if (random == 3) {
                this.direction = Direction.LEFT;
            } else {
                this.direction = Direction.RIGHT;
            }
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
                this.direction = Direction.RIGHT;
                //this.direction.setId(4);
                move();
            }
        } else {
            while (x > goalX) {
                this.direction = Direction.LEFT;
                move();
            }
        }

        if (y < goalY) {
            while (y < goalY) {
                this.direction = Direction.DOWN;
                move();
            }
        } else {
            while (y > goalY) {
                this.direction = Direction.UP;
                move();
            }
        }
        //System.out.println(newQadrant);
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

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void tankDestroy(){
        setY(-100);
        setX(-100);
    }
}
