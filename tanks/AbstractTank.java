package tanks;


import bullet.Bullet;
import filds.ActionField;
import filds.ControlField;
import objectBF.Batlefild;
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

    private List<Bullet> bulletMagazin = new ArrayList<>();


    public AbstractTank() {
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
        ammunition = getAmmunition() - 1;
        if (ammunition == 0) {
            return false;
//            try {
//                Thread.sleep(500);
//                setAmmunition(3);
//
//            } catch (InterruptedException e) {
//
//            }

        }
        return true;
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
        missionCompliet = false;
        //this.bullet = new Bullet(this);
//        getDirectionImageTank();
    }


    public void move() throws Exception {


        af.processMove(this);

        //fire();
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

//        this.bullet.setX(x + 25);
//        this.bullet.setY(y + 25);
//        this.bullet.setDirrect(direction);
//        this.bullet.setArmorPiercing(power);
        //this.setBullet(new Bullet(this));

        af.processFire(this);

    }


    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {

        this.bullet = bullet;
    }

//    public void moveRandom() throws Exception { // ---------------------------------
//        while (true) {
//            int random = Generation.gen(1, 4);
//            if (random == 1) {
//                this.direction = service.Direction.UP;
//            } else if (random == 2) {
//                this.direction = service.Direction.DOWN;
//            } else if (random == 3) {
//                this.direction = service.Direction.LEFT;
//            } else {
//                this.direction = service.Direction.RIGHT;
//            }
//            if (cf.controlTank(bf, this)) {
//                move();
//            }
//        }
//    }

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
                if (bf.scanQuadrant(x, y).getArmor() > 4) {
                    coordEagle = x + "_" + y;
                }
            }
        }

        return coordEagle;
    }

    public void destroyEagle() throws Exception {
        int x = 0;
        int y = 0;
        for (int a = 0; a < 9; a++) {
            for (int b = 0; b < 9; b++) {
                if (bf.scanQuadrant(a, b).getArmor() > 4) {
                    y = a;
                    x = b;
                }

            }
        }
        moveToQuadrantFire(x + 1, y + 1);
        while (bf.scanQuadrant(x, y).getArmor() != 0) {
            fire();
        }
    }

    public void moveRandomWollFire() throws Exception { // ---------------------------------
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
            fire();
            if (ControlField.controlTank(bf, this)) {
                if (ControlField.controlWoll(bf, this, af) == false) {

                    //System.out.println("bumc");
                }
                System.out.println("r = " + random + "; x = " + getX() + "; y = " + getY() + "; f - " + "fff");
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
        af.nextAction(getName(), action);


//        }

        //System.out.println(action.name());
        // move();

    }

    public void moveToQuadrantFire(int v, int h) throws Exception {
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
