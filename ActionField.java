import ObjectBF.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class ActionField extends JPanel {
    volatile protected BField battleField;
    private Map<String, AbstractTank> tanks = new TreeMap<>();
    private volatile Map<String, Action> actionMap = new TreeMap<>();
    private List<String> listAction;
    //private Queue<Map<String, Action>> whotImastDo = new ArrayBlockingQueue<Map<String, Action>>(10);
    //private Bullet bullet;
    public ActionField actionField;
    //volatile public List<Bullet> bullets;
    volatile public Map<String, Bullet> bullets;
    public List<Bullet> bulletsActive;


    void runTheGame() throws Exception {
        //tanks.get("agressor").moveRandomWollFire();
    }

//    public void setBullet(Bullet bullet) {
//        this.bullet = bullet;
//    }

    public ActionField getActionField() {
        return actionField;
    }

    public void nextAction(String nameTank, Action action) throws Exception {
        Action current = null;
        if (action != Action.FIRE ) {
            if (iCanDoThisAction(nameTank, action) == true) {
                System.out.println("xx = " + tanks.get(nameTank).getX() / 64 + " yy = " + tanks.get(nameTank).getY() / 64);

                listAction.add(nameTank + "_" + action.toString());
                current = action;

                synchronized (tanks.get(nameTank)) {
                    tanks.get(nameTank).setMissionCompliet(true);
                    actionMap.put(nameTank, action);
                    tanks.get(nameTank).notify();
                    // System.out.println("LA: " + listAction.get(listAction.size() - 1));


                    //actionMap.remove(nameTank);

                }
                //tanks.get(nameTank).setMissionCompliet(true);

            }
        } else if (action == Action.FIRE) {
            iWantToFire(nameTank, action);
            //tanks.get(nameTank).fire();
        } else if (iCanDoThisAction(nameTank, action) == false){
            actionMap.remove(nameTank, action);
        }
        tanks.get(nameTank).setMissionCompliet(true);
    }

    public void iWantToFire(String nameTank, Action action) throws Exception {
        tanks.get(nameTank).getBulletMagazin().add(new Bullet(tanks.get(nameTank)));
//        if (bulletsActive.size() > 2) {
//            //Thread.sleep(500);
//            bullets.put(nameTank, new Bullet(tanks.get(nameTank)));
//        } else {
//            bullets.put(nameTank, new Bullet(tanks.get(nameTank)));
//        }

        //System.out.println(" ********************* " + bullets.size());
        //fff();
        //processFire(bullets.get(0));
        //bullets.remove(0);


    }

    public void fff() {

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (bullets.size() > 0) {
                            //System.out.println("bingo");
                            bulletsActive.add(bullets.get("agressor"));
                            bullets.remove(bullets.get("agressor"));
                            for (int i = 0; i < bulletsActive.size(); i++) {
                                processFire(bulletsActive.get(i));
                            }
//                            Thread.sleep(500);
                        }
                        Thread.sleep(100);
                    } catch (Exception ex) {

                    }
                }
            }
        }.start();
    }

    public boolean iCanDoThisAction(String nameTank, Action action) {
        //System.out.println();
        if (action == Action.FIRE) {
            return true;
        }

        if (action.toString() != tanks.get(nameTank).getDirection().toString()) {
            return true;
        }
        if (!controlField(nameTank, action)) {

            System.out.println(controlField(nameTank, action));
            return false;
        }

        if (iSeeWall(nameTank, action)) {
            //System.out.println(controlField(nameTank, action));
            return false;
        }
        return true;


    }

    public boolean iSeeWall(String nameTank, Action action) {

        int x = tanks.get(nameTank).getX() / 64;
        int y = tanks.get(nameTank).getY() / 64;

        if (action == Action.UP) {
            y = y - 1;
        } else if (action == Action.DOWN) {
            y = y + 1;
        } else if (action == Action.LEFT) {
            x = x - 1;
        } else if (action == Action.RIGHT) {
            x = x + 1;
        }
        if (battleField.scanQuadrant(x, y).getArmor() > 0) {
            return true;
        }
        return false;
    }

    public boolean controlField(String nameTank, Action action) {
        int x = tanks.get(nameTank).getX() / 64;
        int y = tanks.get(nameTank).getY() / 64;
        //System.out.println("x = " + x);

        if (action == Action.UP) {
            y = y - 1;
        } else if (action == Action.DOWN) {
            y = y + 1;
        } else if (action == Action.LEFT) {
            x = x - 1;
        } else if (action == Action.RIGHT) {
            x = x + 1;
        }
        if (y >= 0 && y <= 8 && x >= 0 && x <= 8) {
            System.out.println("x = " + x + "y = " + y);
            return true;
        }
        return false;
    }

    public void processTurn(AbstractTank abstractTank) throws Exception {
        //repaint();
    }

    public void processMove(AbstractTank abstractTank) throws Exception {


        if (ControlField.controlWoll(battleField, abstractTank, actionField)) {
            moveTank(abstractTank);

        }
    }

    public void moveTank(AbstractTank abstractTank) throws Exception {
        if (ControlField.controlWoll(battleField, abstractTank, actionField)) {
            int covered = 0;
            while (covered < 64) {
                if (abstractTank.getDirection() == Direction.UP) {
                    abstractTank.updateY(-1);
                } else if (abstractTank.getDirection() == Direction.DOWN) {
                    abstractTank.updateY(1);
                } else if (abstractTank.getDirection() == Direction.LEFT) {
                    abstractTank.updateX(-1);
                } else {
                    abstractTank.updateX(1);
                }
                covered += 1;
//            Thread.sleep(70);
                //System.out.println(abstractTank.getX() + "_" + abstractTank.getY());
                Thread.sleep(abstractTank.getSpeed());
            }
            abstractTank.setMissionCompliet(false);
        }
        //System.out.println(abstractTank.getX() / 64 + " _ " + abstractTank.getY() / 64);
        abstractTank.setMissionCompliet(false);
    }

    public void processFire(Bullet bullet1) throws Exception {
//        //this.bullet = bullet1;
////        Bullet bb = bullet1;
        Thread t = new Thread() {

            @Override
            public void run() {
//                System.out.println(Thread.currentThread().getName());
                try {

                    int counter = 0;

                    while (bullet1.getX() >= -14 && bullet1.getX() <= 590
                            && bullet1.getY() >= -14 && bullet1.getY() <= 590) {
                        if (bullet1.getDirrect() == Direction.UP) {
                            bullet1.updateY(-1);
                        } else if (bullet1.getDirrect() == Direction.DOWN) {
                            bullet1.updateY(1);
                        } else if (bullet1.getDirrect() == Direction.LEFT) {
                            bullet1.updateX(-1);
                        } else {
                            bullet1.updateX(1);

                        }
                        counter++;
                        //Thread.sleep(bullet1.getSpeed());
                        //repaint();

            Thread.sleep(5);
                        if (counter > 25) {
                            if (processInterception(bullet1)) {
                                bullet1.destroyBullet();
                                //bullets.remove(0);
//                    controlTank();

                            }
                        }
                    }
                    bullet1.destroyBullet();
                    //bullets.remove(0);


                } catch (Exception ex) {

                }
            }
        };
        t.start();
//        t.interrupt();


//        this.bullet = new Bullet();
//        this.bullet = new Bullet((tank.getX() + 25), (tank.getY() + 25), tank.getDirection(), tank.getPower());


    }

//    public Bullet getBullet() {
//        return bullet;
//    }

    public boolean processInterception(Bullet bullet) throws Exception {

        String qad = getQadrant(bullet.getY(), bullet.getX());
        int separator = qad.indexOf("_");
        int y = Integer.parseInt(qad.substring(0, separator));
        int x = Integer.parseInt(qad.substring(separator + 1));
        if (y >= 0 && y <= 8 && x >= 0 && x <= 8) {
            if (battleField.scanQuadrant(y, x).getArmor() > 0) {


                babah(battleField.scanQuadrant(y, x), x, y, bullet.getArmorPiercing());
                return true;
            }
            if (!controlTank(bullet).equals(bullet.getTank().getName())) {
                //System.out.println("popal!");
                babah(tanks.get(controlTank(bullet)), x, y, bullet.getArmorPiercing());
                return true;
            }
            return false;
        } else {
            return true;
        }

    }

    public String controlTank(Bullet bullet) {
        String k = "";
        for (String key : tanks.keySet()) {
            // System.out.println(tanks.get(key).getName() + " " + tanks.get(key).getX() + "_" + tanks.get(key).getY());
            if (bullet.getX() / 64 == tanks.get(key).getX() / 64 && bullet.getY() / 64 == tanks.get(key).getY() / 64) {
                k = key;
            }
        }
        return k;
    }

    public int numberAction(Action action) {
        int a = 0;
        for (int i = 0; i < Action.values().length; i++) {
            if (Action.values()[i].equals(action)) {
                a = i;
            }
        }
        return a;
    }

    public String getQuadrantXY(int v, int h) {
        return (v - 1) * 64 + "_" + (h - 1) * 64;
    }

    public String getQadrant(int x, int y) {
        return y / 64 + "_" + x / 64;
    }

    public ActionField() throws Exception {


        battleField = new BField(1);

//        bullet = new Bullet(10, 10, Direction.EMPTY, 1);
        tanks.put("defender", new T34(this, battleField));
        Tiger agressor = new Tiger(this, battleField);
        listAction = new ArrayList<>();

        tanks.put("agressor", agressor);

//        this.bullet = new Bullet(100, 100, Direction.DOWN, 1);
        //this.bullet = new Bullet();
        bullets = new LinkedHashMap<>();
        bulletsActive = new LinkedList<>();
        JFrame frame = new JFrame("BATTLE FIELD, DAY 4 - my - ");
        frame.setLocation(750, 100);
        frame.setMinimumSize(new Dimension(battleField.getBfWidth() + 20,
                battleField.getBfHeight() + 35));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Button button = new Button("Agressor");
        Button button1 = new Button("Defender");
        JPanel startPanel = new JPanel();
        //JPanel gamePanel = new JPanel();
        //gamePanel.add(this);
        startPanel.add(button);
        startPanel.add(button1);
        fff();

        new Thread() {
            @Override
            public void run() {

                try {

                    frame.addKeyListener(new Move1(tanks.get("agressor")));
                    System.out.println(actionMap.keySet());
                    while (true) {
                        synchronized (tanks.get("agressor")) {

                            tanks.get("agressor").wait();
                            //if (tanks.get("agressor").getMissionCompliet()) {
                            if (tanks.get("agressor").getDirection().toString().equals(actionMap.get("agressor").toString())) {
                                moveTank(tanks.get("agressor"));
//                            }

//                            if (actionMap.get("agressor").equals(Action.FIRE)) {
//                                tanks.get("agressor").setMissionCompliet(false);
//                                new Thread() {
//                                    @Override
//                                    public void run() {
//                                        try {
//                                            tanks.get("agressor").fire();
//                                        } catch (Exception rr) {
//
//                                        }
//                                    }
//                                }.start();
//                                //tanks.get("agressor").fire();
//
                            } else {
                                tanks.get("agressor").setDirection(Direction.values()[numberAction(actionMap.get("agressor"))]);
                                tanks.get("agressor").setMissionCompliet(false);
                            }
                            tanks.get("agressor").notify();
                        }
                        //}

                    }
                } catch (Exception s) {

                }
            }
        }.start();

//        new Thread() {
//            @Override
//            public void run() {
//                frame.addKeyListener(new Move(tanks.get("agressor")));
////                frame.addKeyListener(new FireListener(tanks.get("agressor")));
//            }
//        }.start();
        Thread thr = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        //tanks.get("defender").moveRandomWoll();
                        tanks.get("defender").fire();
                        Thread.sleep(5000);
                    }
//                            tanks.get("defender").moveRandomWoll();
//                            runTheGame();
                } catch (Exception el) {

                }
            }
        };

        thr.start();

        //frame.addKeyListener(new FireListener(tanks.get("agressor")));
//        frame.add(startPanel);

        new Thread() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(10);
                        repaint();
                    } catch (Exception ex) {

                    }
                }
            }
        }.start();
        frame.add(this);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(ActionField.this);
                Thread thr = new Thread() {
                    @Override
                    public void run() {
                        try {

                            tanks.get("defender").moveRandomWoll();
//                            tanks.get("defender").moveRandomWoll();
//                            runTheGame();
                        } catch (Exception el) {

                        }
                    }
                };

                thr.start();

                Thread thr1 = new Thread() {
                    @Override
                    public void run() {
                        try {
                            tanks.get("agressor").moveRandomWoll();
//                            runTheGame();
                        } catch (Exception el) {

                        }
                    }
                };
                thr1.start();
                frame.pack();
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(ActionField.this);
                Thread thr = new Thread() {
                    @Override
                    public void run() {
                        try {
                            tanks.get("agressor").moveRandomWollFire();
//                            runTheGame();
                        } catch (Exception el) {

                        }
                    }
                };
                thr.start();

                frame.pack();

            }
        });
        frame.setVisible(true);
        frame.pack();
    }

//    public void start(String ss) {
//        try {
//            actionField.runTheGame();
//
//
//        } catch (Exception r) {
//
//        }
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int j = 0; j < battleField.getDimentionX(); j++) {
            for (int k = 0; k < battleField.getDimentionY(); k++) {
                String coordinates = getQuadrantXY(j + 1, k + 1);
                int separator = coordinates.indexOf("_");
                int y = Integer.parseInt(coordinates
                        .substring(0, separator));
                int x = Integer.parseInt(coordinates
                        .substring(separator + 1));
                g.drawImage(battleField.scanQuadrant(j, k).getImg(), j * 64, k * 64, new ImageObserver() {
                    @Override
                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return false;
                    }
                });
            }
        }
//
//
        if (tanks.get("defender").getDirection() == Direction.UP) {
            tanks.get("defender").setNameImageUP("T34_UP.png");

            g.drawImage(tanks.get("defender").getImgUP(), tanks.get("defender").getX(), tanks.get("defender").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });


        } else if (tanks.get("defender").getDirection() == Direction.DOWN) {
            tanks.get("defender").setNameImageD("T34_D.png");
            g.drawImage(tanks.get("defender").getImgD(), tanks.get("defender").getX(), tanks.get("defender").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        } else if (tanks.get("defender").getDirection() == Direction.LEFT) {
            tanks.get("defender").setNameImageL("T34_L.png");
            g.drawImage(tanks.get("defender").getImgL(), tanks.get("defender").getX(), tanks.get("defender").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        } else {
            tanks.get("defender").setNameImageR("T34_R.png");
            g.drawImage(tanks.get("defender").getImgR(), tanks.get("defender").getX(), tanks.get("defender").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        }


        if (tanks.get("agressor").getDirection() == Direction.UP) {
            tanks.get("agressor").setNameImageUP("Tank_UP.png");

            g.drawImage(tanks.get("agressor").getImgUP(), tanks.get("agressor").getX(), tanks.get("agressor").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        } else if (tanks.get("agressor").getDirection() == Direction.DOWN) {
            tanks.get("agressor").setNameImageD("Tank_D.png");

            g.drawImage(tanks.get("agressor").getImgD(), tanks.get("agressor").getX(), tanks.get("agressor").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        } else if (tanks.get("agressor").getDirection() == Direction.LEFT) {
            tanks.get("agressor").setNameImageL("Tank_L.png");
            g.drawImage(tanks.get("agressor").getImgL(), tanks.get("agressor").getX(), tanks.get("agressor").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        } else {
            tanks.get("agressor").setNameImageR("Tank_R.png");
            g.drawImage(tanks.get("agressor").getImgR(), tanks.get("agressor").getX(), tanks.get("agressor").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        }


        for (int j = 0; j < battleField.getDimentionX(); j++) {
            for (int k = 0; k < battleField.getDimentionY(); k++) {
                String coordinates = getQuadrantXY(j + 1, k + 1);
                int separator = coordinates.indexOf("_");
                int y = Integer.parseInt(coordinates
                        .substring(0, separator));
                int x = Integer.parseInt(coordinates
                        .substring(separator + 1));
                if (battleField.scanQuadrant(j, k) instanceof Water) {
                    g.drawImage(battleField.scanQuadrant(j, k).getImg(), j * 64, k * 64, new ImageObserver() {
                        @Override
                        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                            return false;
                        }

                    });
                }
            }
        }
        g.setColor(Color.YELLOW);
        for (int i = 0; i < bulletsActive.size(); i++) {
            //g.fillOval(bullet.getX(), bullet.getY(), 10, 10);
            g.fillOval(bulletsActive.get(i).getX(), bulletsActive.get(i).getY(), 10, 10);
        }


    }

    public void babah(Destroy element, int y, int x, int a) {
        if (element.destroy(a)) {
            //System.out.println(bullet.getX() + " " + bullet.getY() + " __ " + "x = " + x * 64 + " y = " + y * 64);
            battleField.updateQuadrant(y, x, new Ampty(y, x));
        }
    }
}