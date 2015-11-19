package filds;

import bullet.Bullet;
import control.Move1;
import objectBF.*;
import service.Action;
import service.Destroy;
import service.Direction;
import tanks.AbstractTank;
import tanks.T34;
import tanks.Tiger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;


public class ActionField extends JPanel {
    private JFrame frame;
    private Batlefild batleField;
    private Bullet bullet;
    private Map<String, AbstractTank> tanks = new TreeMap<>();
    private volatile Map<String, Action> actionMap = new TreeMap<>();
    public ActionField actionField;
    volatile public Map<String, Bullet> bullets;


    volatile private Queue<Action> listAction;
    private ExecutorService poolThread;
    private ExecutorService poolBullet;
    private Queue<Bullet> bulletList;
//    private List<String> listAction;

    public ActionField() throws Exception {
        batleField = new Batlefild(1);
        tanks.put("defender", new T34(this, batleField));
        tanks.put("agressor", new Tiger(this, batleField));
        //bullet = new Bullet();
        bulletList = new ArrayDeque<>();

        listAction = new ArrayBlockingQueue<Action>(5);
        poolThread = Executors.newFixedThreadPool(20);
        poolBullet = Executors.newFixedThreadPool(3);

        bullets = new LinkedHashMap<>();
        // bulletsActive = new LinkedList<>();

        createGamePanel();
//        while (true) {
////            System.out.println("size = " + poolThread.);
//            Thread.sleep(2000);
//        }

    }

    public void createGamePanel() {
        frame = new JFrame("  ----   BATTLE FIELD    ----  ");
        frame.setLocation(450, 80);
        frame.setMinimumSize(new Dimension(batleField.getBfWidth() + 20, batleField.getBfHeight() + 35));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        frame.pack();
        repaintFrame();
        selectTanksPanel();

//        startGame();
    }

    public Queue<Action> getListAction() {
        return listAction;
    }

    public void setListAction(Queue<Action> listAction) {
        this.listAction = listAction;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Construct[] construct : batleField.getBatlefield()) {
            for (Construct con : construct) {
                con.draw(g);
            }
        }
        tanks.get("defender").draw(g);
        tanks.get("agressor").draw(g);
        Iterator<Bullet> bulletIterator = bulletList.iterator();
        while (bulletIterator.hasNext()) {
            bulletIterator.next().draw(g);
        }
    }

    public void selectTanksPanel() {
        JFrame selectPanel = new JFrame("Select Tanks");
        selectPanel.setMinimumSize(new Dimension(350, 250));
        selectPanel.setLocation(500, 250);
        JPanel mes = new JPanel();
        mes.setLayout(new GridBagLayout());
        JLabel txt = new JLabel("Please, select your Tank!");
        JButton agressor = new JButton("agressor");
        JButton defender = new JButton("defender");
        agressor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPanel.setVisible(false);
                startGame("agressor");
            }
        });
        defender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPanel.setVisible(false);
                startGame("defender");
            }
        });
        mes.add(txt, new GridBagConstraints(0, 0, 0, 1, 1, 0, GridBagConstraints.CENTER, 0, new Insets(0, 0, 5, 0), 0, 0));
        mes.add(agressor, new GridBagConstraints(0, 1, 1, 0, 1, 0, GridBagConstraints.CENTER, 0, new Insets(0, 0, 5, 0), 0, 0));
        mes.add(defender, new GridBagConstraints(1, 1, 1, 0, 1, 0, GridBagConstraints.CENTER, 0, new Insets(0, 0, 5, 0), 0, 0));
        selectPanel.setContentPane(mes);


        selectPanel.setVisible(true);
        selectPanel.pack();


    }


    public ActionField getActionField() {
        return actionField;
    }

    public void nextAction(String nameTank, Action action) throws Exception {
        Action current = null;
        //System.out.println(action.toString());
        if (action != Action.FIRE) {
            if (iCanDoThisAction(nameTank, action)) {

//                System.out.println("xx = " + tanks.get(nameTank).getX() / 64 + " yy = " + tanks.get(nameTank).getY() / 64);

                //listAction.add(nameTank + "_" + action.toString());
                current = action;

                synchronized (tanks.get(nameTank)) {
                    tanks.get(nameTank).setMissionCompliet(true);
                    actionMap.put(nameTank, action);
                    tanks.get(nameTank).notify();
                }
            }
        } else if (action == Action.FIRE) {
            //tanks.get(nameTank).getPoolBullet().submit(new Bullet().run());
            iWantToFire(nameTank, action);
        } else if (iCanDoThisAction(nameTank, action) == false) {
            actionMap.remove(nameTank, action);
        }
        tanks.get(nameTank).setMissionCompliet(true);
    }

    public void iWantToFire(String nameTank, Action action) throws Exception {
        if (tanks.get(nameTank).controlAmmunition()) {
//        poolThread.submit(tanks.get(nameTank));
            poolBullet.submit(tanks.get(nameTank));
        } else {
            Thread.sleep(500);
            tanks.get(nameTank).setAmmunition(3);
        }
//        poolBullet.
//        tanks.get(nameTank).getPoolBullet().submit();

    }


    public boolean iCanDoThisAction(String nameTank, Action action) {
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
        if (batleField.scanQuadrant(x, y).getArmor() > 0) {
            return true;
        }
        return false;
    }

    public boolean controlField(String nameTank, Action action) {
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
        if (y >= 0 && y <= 8 && x >= 0 && x <= 8) {
            System.out.println("x = " + x + "y = " + y);
            return true;
        }
        return false;
    }


    public void processMove(AbstractTank abstractTank) throws Exception {
        if (ControlField.controlWoll(batleField, abstractTank, actionField)) {
            moveTank(abstractTank);
        }
    }

    public void moveTank(AbstractTank abstractTank) throws Exception {
        if (ControlField.controlWoll(batleField, abstractTank, actionField)) {
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
                Thread.sleep(abstractTank.getSpeed());
                abstractTank.setMissionCompliet(false);
            }

        }
        abstractTank.setMissionCompliet(false);
    }

    public void processFire(AbstractTank abstractTank) throws Exception {
        Bullet bullet = new Bullet(abstractTank);
//        bullet = new Bullet(abstractTank);
        bulletList.add(bullet);
        int counter = 0;

        while (bullet.getX() >= -14 && bullet.getX() <= 590
                && bullet.getY() >= -14 && bullet.getY() <= 590) {
            if (bullet.getDirrect() == Direction.UP) {
                bullet.updateY(-1);
            } else if (bullet.getDirrect() == Direction.DOWN) {
                bullet.updateY(1);
            } else if (bullet.getDirrect() == Direction.LEFT) {
                bullet.updateX(-1);
            } else {
                bullet.updateX(1);

            }
            counter++;

            Thread.sleep(5);
            if (counter > 25) {
                if (processInterception(bullet)) {
                    //bulletList.poll();
                    bullet.destroyBullet();
                }
            }
        }
        //bulletList.poll();
        bullet.destroyBullet();

    }

    public String whoIsEnamy(String tank) {
        String enemy = null;
        Set<String> name = tanks.keySet();
        for (String a : name) {
            if (!tank.equals(a)) {
                enemy = a;
            }
        }
        return enemy;
    }

    public boolean processInterception(Bullet bullet) throws Exception {
        String enemy = whoIsEnamy(bullet.getTank().getName());

        String qad = getQadrant(bullet.getY(), bullet.getX());
        int separator = qad.indexOf("_");
        int y = Integer.parseInt(qad.substring(0, separator));
        int x = Integer.parseInt(qad.substring(separator + 1));
        if (y >= 0 && y <= 8 && x >= 0 && x <= 8) {
            if (batleField.getBatlefield()[x][y].getArmor() > 0) {
                babah(batleField.getBatlefield()[x][y], bullet.getArmorPiercing(), y, x);
                return true;
            }
            if (bullet.getX() / 64 == tanks.get(enemy).getX() / 64 && bullet.getY() / 64 == tanks.get(enemy).getY() / 64) {
                babah(tanks.get(enemy), bullet.getArmorPiercing(), y, x);
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


    public void startGame(String nameTank) {
        poolThread.submit(
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            tanks.get(whoIsEnamy(nameTank)).moveRandomWollFire();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        poolThread.submit(
                new Thread() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName().toString());

                        try {
                            frame.addKeyListener(new Move1(tanks.get(nameTank), actionField));
                            while (true) {
                                synchronized (tanks.get(nameTank)) {

                                    tanks.get(nameTank).wait();
                                    if (tanks.get(nameTank).getDirection().toString().equals(actionMap.get(nameTank).toString())) {
                                        moveTank(tanks.get(nameTank));
                                    } else {
                                        tanks.get(nameTank).setDirection(Direction.values()[numberAction(actionMap.get(nameTank))]);
                                        tanks.get(nameTank).setMissionCompliet(false);
                                    }
                                    tanks.get(nameTank).notify();
                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void repaintFrame() {

        Thread t = new Thread() {
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
        };
        poolThread.submit(t);
    }

    public void babah(Destroy element, int a, int x, int y) {
        if (element.destroy(a)) {
            batleField.updateQuadrant(y, x, new Empty(x, y));
        }
    }


}