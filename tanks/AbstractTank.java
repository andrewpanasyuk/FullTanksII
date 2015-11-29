package tanks;


import bullet.Bullet;
import filds.ActionField;
import filds.ControlField;
import filds.Batlefild;
import objectBF.Eagle;
import service.Action;
import service.Destroy;
import service.Direction;
import service.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by panasyuk on 16.06.2015.
 */
public abstract class AbstractTank implements Destroy, Drawable, Runnable {
    private String name;
    private volatile boolean missionCompliet;
    private volatile int x;
    private volatile int y;
    private Direction direction;
    protected int speed = 20;
    private ActionField af;
    protected Batlefild bf;
    private int armor;
    protected Bullet bullet;
    protected int power;
    private String nameImage;
    private HashMap<Direction, Image> directionImageTank;
    private Image image;
    private ExecutorService poolBullet;
    private int ammunition;
    volatile private Action currentAction;

    private List<Bullet> bulletMagazin = new ArrayList<>();


    public AbstractTank() {
        currentAction = null;
        directionImageTank = new HashMap<>();
        poolBullet = Executors.newFixedThreadPool(3);
        ammunition = 0;

    }

    public List<Bullet> getBulletMagazin() {
        return bulletMagazin;
    }

    public void setBulletMagazin(List<Bullet> bulletMagazin) {
        this.bulletMagazin = bulletMagazin;
    }

    public AbstractTank(ActionField af) {
        this.af = af;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public boolean controlAmmunition() {
        if (ammunition > 0) {
            ammunition = getAmmunition() - 1;
            return true;
        }
        return false;
    }

    public AbstractTank(ActionField af, Batlefild bf) {
        this(bf, af, 128, 512, Direction.UP);
    }

    @Override
    public void run() {
        try {
            fire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AbstractTank(Batlefild bf, ActionField af, int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.af = af;
        this.bf = bf;
        missionCompliet = true;
    }


    public void move() throws Exception {
        System.out.println(getX() + "_" + getY());
        af.processMove(this);
    }

    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    public ExecutorService getPoolBullet() {
        return poolBullet;
    }

    public void setPoolBullet(ExecutorService poolBullet) {
        this.poolBullet = poolBullet;
    }

    public void setMissionCompliet(boolean missionCompliet) {
        this.missionCompliet = missionCompliet;
    }

    public boolean getMissionCompliet() {
        return missionCompliet;
    }

    public void fire() throws Exception {
        af.processFire(this);

    }


    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {

        this.bullet = bullet;
    }


    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void moveRandomWoll() throws Exception { // ---------------------------------
        while (true) {
            int random = new Random().nextInt(3);
            if (random == 0) {
                this.direction = Direction.UP;

            } else if (random == 1) {
                this.direction = Direction.DOWN;
            } else if (random == 2) {
                this.direction = Direction.LEFT;
            } else {
                this.direction = Direction.RIGHT;
            }
            this.image = getDirectionImageTank().get(direction);
            if (ControlField.controlTank(bf, this)) {
                if (ControlField.controlWoll(bf, this, af)) {
//                    fire();
//                    Thread.sleep(20);
                    move();

                    //System.out.println(getName() + " bullet: x =  " + getBullet().getX() + " y =  " + getBullet().getY());
                }
            }

        }
    }

    public String searchEagle() {
        String coordEagle = "";
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
//                if (bf.scanQuadrant(x, y).getArmor() > 4) {
                if (bf.scanQuadrant(x, y) instanceof Eagle) {
                    coordEagle = x + "_" + y;
                }
            }
        }

        return coordEagle;
    }


    public void destroyEagle() throws Exception {


        int x = 0;
        int y = 0;
        for (int xx = 0; xx < 9; xx++) {
            for (int b = 0; b < 9; b++) {
                if (bf.scanQuadrant(xx, b) instanceof Eagle) {
                    x = xx * 64;
                    y = b * 64;
                }

            }
        }
        while (af.getGameStatus()) {
            Action a = null;
            if (getY() > y) {
                setDirection(Direction.UP);
                a = Action.UP;
            } else if (getY() < y) {
                setDirection(Direction.DOWN);
                a = Action.DOWN;
            } else if (getY() == y) {

                if (getX() < x) {
                    setDirection(Direction.RIGHT);
                    a = Action.RIGHT;
                } else if (getX() > x) {
                    setDirection(Direction.LEFT);
                    a = Action.LEFT;
                }
            }
            if (af.iSeeWall(name, a)) {
                fire();
            }
            if (canIseeEnemy(af.whoIsEnamy(name))) {
                fire();
            }
            if (canIseeEagle(x, y)) {
                while (bf.scanQuadrant(x/64, y/64).getArmor() > 0) {
                    fire();
                }

            }
            if (bf.scanQuadrant(x/64, y/64).getArmor() == 0) {
                af.setGameStatus(false);
                af.finalPanel(name);
               // af.finish(name);
            }
            move();
        }
    }

    public boolean canIseeEagle(int x, int y) {
        int eagleX = x / 64;
        int eagleY = y / 64;
        System.out.println(eagleX + " = " + eagleY + " ---|||||--- " + getX()/64 + " == " + getY()/64);

        if (getX() / 64 == eagleX) {
            int count = Math.abs(getY() / 64 - eagleY);
            int a = 0;
            for (int i = 0; i < count; i++) {
                if (getY() / 64 < eagleY) {
                    if (bf.scanQuadrant(getX() / 64, getY() / 64 + i).getArmor() < 1) {
                        a++;
                    }
                } else if (getY() / 64 > eagleY) {
                    if (bf.scanQuadrant(getX() / 64, getY() / 64 - i).getArmor() < 1) {
                        a++;
                    }
                }
                if (a == count) {
                    return true;
                }
            }

        }
        if (getY() / 64 == eagleY) {
            int count = Math.abs(getX() / 64 - eagleX);
            int a = 0;
            for (int i = 0; i <= count; i++) {
                if (getX() / 64 < eagleX) {
                    if (bf.scanQuadrant(getX() / 64 + i, getY() / 64).getArmor() < 1) {
                        a++;
                    }
                } else if (getX() / 64 > eagleX) {
                    if (bf.scanQuadrant((getX() / 64) - i, getY() / 64).getArmor() < 1) {
                        a++;
                    } else {
                        return false;
                    }
                }
                if (a == count) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean canIseeEnemy(String enemy) {
        int enemyX = af.getTanks().get(enemy).getX() / 64;
        int enemyY = af.getTanks().get(enemy).getY() / 64;


        if (getX() / 64 == enemyX) {
            int count = Math.abs(getY() / 64 - enemyY);
            int a = 0;
            for (int i = 0; i < count; i++) {
                if (getY() / 64 < enemyY) {
                    if (bf.scanQuadrant(getX() / 64, getY() / 64 + i).getArmor() < 1) {
                        a++;
                    }
                } else if (getY() / 64 > enemyY) {
                    if (bf.scanQuadrant(getX() / 64, getY() / 64 - i).getArmor() < 1) {
                        a++;
                    }
                }
                if (a == count) {
                    return true;
                }
            }

        }
        if (getY() / 64 == enemyY) {
            int count = Math.abs(getX() / 64 - enemyX);
            int a = 0;
            for (int i = 0; i <= count; i++) {
                if (getX() / 64 < enemyX) {
                    if (bf.scanQuadrant(getX() / 64 + i, getY() / 64).getArmor() < 1) {
                        a++;
                    }
                } else if (getX() / 64 > enemyX) {
                    if (bf.scanQuadrant((getX() / 64) - i, getY() / 64).getArmor() < 1) {
                        a++;
                    } else {
                        return false;
                    }
                }
                if (a == count) {
                    return true;
                }
            }
        }
        return false;
    }

//    public String whereEnemy(String enemy) {
//        String coord;
//        int x = af.getTanks().get(enemy).getX();
//        int y = af.getTanks().get(enemy).getY();
//        coord = Integer.toString(x) + "_" + Integer.toString(y);
//        return coord;
//    }
//
//    public void radarEnemy() {
//
//    }

    public void destroyEnemy() throws Exception {
        String enemyName = af.whoIsEnamy(name);
        Action a = null;

        while (af.getGameStatus()) {
            int x = af.getTanks().get(enemyName).getX();
            int y = af.getTanks().get(enemyName).getY();
//            radarEnemy();
            if (getY() > y) {
                setDirection(Direction.UP);
                a = Action.UP;
            } else if (getY() < y) {
                setDirection(Direction.DOWN);
                a = Action.DOWN;
            } else if (getY() == y) {

                if (getX() < x) {
                    setDirection(Direction.RIGHT);
                    a = Action.RIGHT;
                } else if (getX() > x) {
                    setDirection(Direction.LEFT);
                    a = Action.LEFT;
                }
            }
            if (af.iSeeWall(name, a)) {
                fire();
            }
            if (canIseeEnemy(enemyName)) {
                fire();
                if (af.getTanks().get(enemyName).getArmor() == 0){
                    af.getTanks().get(enemyName).destroy();
                }
            }
            move();
        }
//        String enemy = af.whoIsEnamy(name);
//
//        canIseeEnemy(enemy);
//        int x = 0;
//        int y = 0;
//        for (int a = 0; a < 9; a++) {
//            for (int b = 0; b < 9; b++) {
//                if (bf.scanQuadrant(a, b).getArmor() > 4) {
//                    y = a;
//                    x = b;
//                }
//
//            }
//        }
//        moveToQuadrantFire(x + 1, y + 1);
//        while (bf.scanQuadrant(x, y).getArmor() != 0) {
//            fire();
//        }
    }

    public void moveRandomWollFire() throws Exception { // ---------------------------------
        while (getArmor() > 0) {
            int random = new Random().nextInt(3);
            if (random == 0) {
//                af.nextAction(name, Action.UP);
                this.direction = Direction.UP;
            } else if (random == 1) {
//                af.nextAction(name, Action.DOWN);
                this.direction = Direction.DOWN;
            } else if (random == 2) {
//                af.nextAction(name, Action.LEFT);
                this.direction = Direction.LEFT;
            } else {
//                af.nextAction(name, Action.RIGHT);
                this.direction = Direction.RIGHT;
            }
            System.out.println(getX() / 64 + "_" + getY() / 64);
            move();
//            af.nextAction(name, Action.FIRE);
            fire();
//            if (ControlField.controlTank(bf, this)) {
//                if (ControlField.controlWoll(bf, this, af) == false) {
//
//                    //System.out.println("bumc");
//                }
//                System.out.println("r = " + random + "; x = " + getX() + "; y = " + getY() + "; f - " + "fff");
//                move();
//
//            }
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
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public void waitAction(Action action) throws Exception {
        System.out.println(currentAction + "  /////****////****///---" + action);
        setCurrentAction(action);
//        currentAction = action;
        af.nextAction(getName(), action);


//        }

        //System.out.println(action.name());
        // move();

    }

    public void setCurrentAction(Action currentAction) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        if (currentAction != Action.FIRE) {
        this.currentAction = currentAction;
//        }
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public void moveToQuadrantFire(int v, int h) throws Exception {
        System.out.println(v + " ************* " + h);
        String newQadrant = af.getQuadrantXY(v, h);
        int separator = newQadrant.indexOf("_");
        int goalY = v * 64;
        int goalX = h * 64;
        System.out.println(goalX + " _________ " + goalY);
        while (af.getGameStatus()) {
            if (x < goalX) {
                while (x < goalX) {
                    this.direction = Direction.RIGHT;
                    move();
                    if (af.iSeeWall(name, Action.RIGHT)) {
                        fire();
                    }

                }
            } else {
                while (x > goalX) {
                    this.direction = Direction.LEFT;
                    move();
                    if (af.iSeeWall(name, Action.LEFT)) {
                        fire();
                    }

                }
            }


            if (y < goalY) {
                while (y < goalY) {
                    this.direction = Direction.DOWN;
                    move();
                    if (af.iSeeWall(name, Action.DOWN)) {
                        fire();
                    }

                }
            } else {
                while (y > goalY) {
                    this.direction = Direction.UP;
                    move();
                    if (af.iSeeWall(name, Action.UP)) {
                        fire();
                    }

                }

            }
            if (bf.scanQuadrant(goalX, goalY).getArmor() == 0) {
                af.setGameStatus(false);
            }
        }

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

    public void moves(AbstractTank abstractTank) throws Exception {
//        int covered = 0;
//        while (covered < 64) {
        if (abstractTank.getDirection() == Direction.UP) {
            abstractTank.updateY(-1);
        } else if (abstractTank.getDirection() == Direction.DOWN) {
            abstractTank.updateY(1);
        } else if (abstractTank.getDirection() == Direction.LEFT) {
            abstractTank.updateX(-1);
        } else {
            abstractTank.updateX(1);
        }
//            covered += 1;
        //repaint();
//            Thread.sleep(abstractTank.getSpeed());
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


    @Override
    public boolean destroy(int a) {
        setArmor(getArmor() - 1);
        if (getArmor() == 0) {
            setY(-100);
            setX(-100);
            af.setGameStatus(false);

            return true;

        }
        return false;
    }

    @Override
    public boolean destroy() {

        return false;
    }

    public HashMap<Direction, Image> getDirectionImageTank() {
        directionImageTank = new HashMap<>();
        // System.out.println(getName());
        try {
            directionImageTank.put(Direction.UP, ImageIO.read(new File("imageBF/" + getName() + "_UP.png")));
            System.out.println("imageBF/" + getName() + "_UP.png");
            directionImageTank.put(Direction.RIGHT, ImageIO.read(new File("imageBF/" + getName() + "_R.png")));
            directionImageTank.put(Direction.LEFT, ImageIO.read(new File("imageBF/" + getName() + "_L.png")));
            directionImageTank.put(Direction.DOWN, ImageIO.read(new File("imageBF/" + getName() + "_D.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directionImageTank;
    }

    public void setDirectionImageTank(HashMap<Direction, Image> directionImageTank) {
        this.directionImageTank = directionImageTank;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(directionImageTank.get(direction), getX(), getY(), new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
    }
}
