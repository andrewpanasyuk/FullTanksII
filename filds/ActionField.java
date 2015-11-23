package filds;

import bullet.Bullet;
import control.History;
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
    private volatile Map<String, AbstractTank> tanks = new TreeMap<>();
    private volatile Map<String, Action> actionMap = new TreeMap<>();
    public ActionField actionField;
    volatile public Map<String, Bullet> bullets;
    private boolean gameStatus;
    private long startTime;
    volatile private Queue<Action> listAction;
    private ExecutorService poolThread;
    private ExecutorService poolBullet;
    private Queue<Bullet> bulletList;
    private List<String> historyGame;
    Action current;

    public ActionField() throws Exception {
        current = null;
        historyGame = new ArrayList<>();
        startTime = System.currentTimeMillis();
        gameStatus = true;
        batleField = new Batlefild(1);
        tanks.put("defender", new T34(this, batleField));
        tanks.put("agressor", new Tiger(this, batleField));
        bulletList = new ArrayDeque<>();

        listAction = new ArrayBlockingQueue<Action>(5);
        poolThread = Executors.newFixedThreadPool(20);
        poolBullet = Executors.newFixedThreadPool(3);
        bullets = new LinkedHashMap<>();
        createGamePanel();
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
        Set<String> name = tanks.keySet();
        for (String a : name) {
            tanks.get(a).draw(g);
        }
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

    public Map<String, AbstractTank> getTanks() {
        return tanks;
    }

    public void finalPanel(String nameWinner) {
        JFrame selectPanel = new JFrame("Final!!!");
        selectPanel.setMinimumSize(new Dimension(350, 250));
        selectPanel.setLocation(500, 250);
        JPanel mes = new JPanel();
        mes.setLayout(new GridBagLayout());
        JLabel txt = new JLabel("Winner - " + nameWinner);
        //JButton agressor = new JButton("agressor");
        JButton repeat = new JButton("Repeat rec. Game");
        //agressor.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //selectPanel.setVisible(false);
//                //startGame("agressor");
//            }
//        });
        repeat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPanel.setVisible(false);

                povtor();
//
            }
        });
        mes.add(txt, new GridBagConstraints(0, 0, 0, 1, 1, 0, GridBagConstraints.CENTER, 0, new Insets(0, 0, 5, 0), 0, 0));
//        mes.add(agressor, new GridBagConstraints(0, 1, 1, 0, 1, 0, GridBagConstraints.CENTER, 0, new Insets(0, 0, 5, 0), 0, 0));
        mes.add(repeat, new GridBagConstraints(1, 1, 1, 0, 1, 0, GridBagConstraints.CENTER, 0, new Insets(0, 0, 5, 0), 0, 0));
        selectPanel.setContentPane(mes);


        selectPanel.setVisible(true);
        selectPanel.pack();


    }

    public void restarParam() {
        batleField = new Batlefild(1);
        tanks.clear();
        tanks.put("defender", new T34(this, batleField));
        try {
            tanks.put("agressor", new Tiger(this, batleField));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void povtor() {
        poolThread.submit(
                new Thread() {
                    @Override
                    public void run() {
                        restarParam();
                        Long t = 0l;
                        int d = 0;
                        String ss;
                        History h = new History();
                        List l = null;
                        try {
                            l = h.repeatGame();
                            for (int i = 0; i < l.size(); i++) {

                                ss = (String) l.get(i);
                                int f = ss.indexOf("_");
                                t = Long.valueOf(ss.substring(0, f));
                                String n = ss.substring(f + 1, ss.indexOf("_", f + 1));
                                Action a = Action.valueOf(ss.substring(ss.lastIndexOf("_") + 1, ss.length()));
                                Thread.sleep(t);
                                if (a != Action.FIRE) {
                                    Direction direction = Direction.valueOf(ss.substring(ss.lastIndexOf("_") + 1, ss.length()));
                                    if (tanks.get(n).getDirection() != direction) {
                                        tanks.get(n).setDirection(direction);
                                    } else {
                                        poolThread.submit(new Thread() {
                                                              public void run() {
                                                                  try {
                                                                      tanks.get(n).move();
                                                                  } catch (Exception e) {
                                                                      e.printStackTrace();
                                                                  }
                                                              }
                                                          }
                                        );
                                    }

                                } else {
                                    poolBullet.submit(
                                            new Thread() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        tanks.get(n).fire();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                    );

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

    }


    public ActionField getActionField() {
        return actionField;
    }

    public void recHistoryGame(String nameTank, Action action) {
        if (action != null) {
            Long timeOfAction = System.currentTimeMillis();
            Long delta = timeOfAction - startTime;
            startTime = timeOfAction;
            String history = Long.toString(delta) + "_" + nameTank + "_" + action;
            historyGame.add(history);
        }

    }

    public void nextAction(String nameTank, Action action) throws Exception {
        if (action != Action.FIRE) {
            if (iCanDoThisAction(nameTank, action)) {

                synchronized (tanks.get(nameTank)) {
                    tanks.get(nameTank).setMissionCompliet(true);
                    actionMap.put(nameTank, action);
                    recHistoryGame(nameTank, action);
                    tanks.get(nameTank).notify();
                }
            }
        } else if (action == Action.FIRE) {
            recHistoryGame(nameTank, action);
            iWantToFire(nameTank, action);
        } else if (iCanDoThisAction(nameTank, action) == false) {
            actionMap.remove(nameTank, action);
        }
        tanks.get(nameTank).setMissionCompliet(true);
    }

    public void iWantToFire(String nameTank, Action action) throws Exception {
        if (tanks.get(nameTank).controlAmmunition()) {
            poolBullet.submit(tanks.get(nameTank));
        } else {
            Thread.sleep(50);
            tanks.get(nameTank).setAmmunition(3);
        }

    }


    public boolean iCanDoThisAction(String nameTank, Action action) {
        if (action == Action.FIRE) {
            return true;
        }
        if (action.toString() != tanks.get(nameTank).getDirection().toString()) {
            return true;
        }
        if (!controlField(nameTank, action)) {
            return false;
        }
        if (iSeeWall(nameTank, action)) {
            return false;
        }
        return true;
    }

    public boolean iSeeWall(String nameTank, Action action) {
        if (controlField(nameTank, action)) {
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
            return true;
        }
        return false;
    }


    public void processMove(AbstractTank abstractTank) throws Exception {
        if (ControlField.controlWoll(batleField, abstractTank, actionField) && ControlField.controlTank(batleField, abstractTank)) {
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
                    bullet.destroyBullet();
                }
            }
        }
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
                            tanks.get(whoIsEnamy(nameTank)).destroyEagle();
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
                        while (gameStatus) {
                            Set<String> name = tanks.keySet();
                            for (String a : name) {
                                if (tanks.get(a).getArmor() == 0) {
                                    gameStatus = false;
                                    try {
                                        History history = new History();
                                        history.addHistory(historyGame);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    finalPanel(whoIsEnamy(a));
                                }
                            }
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        poolThread.submit(
                new Thread() {
                    @Override
                    public void run() {
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
                                    tanks.get(nameTank).setCurrentAction(null);
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

    public boolean getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Action getCurrent() {
        return current;
    }

    public void setCurrent(Action current) {
        this.current = current;
    }
}