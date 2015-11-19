package control;

import service.Action;
import tanks.AbstractTank;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by panasyuk on 07.10.2015.
 */
public class Move implements KeyListener {
    AbstractTank tank;
    volatile Map<Integer, Boolean> mapKeys;
    Map<Integer, Action> mapActions;
    boolean keyIsPress = false;
    boolean iAmReadyForActions;
    List<Action> actionList;
    Action currentAction;
    volatile int startPress;
    volatile int finishPress;
    private Queue<Integer> actionsList;
    volatile boolean flag;

    public Move(AbstractTank tank) {
        flag = false;
        mapKeys = new TreeMap<>();
        mapActions = new TreeMap<>();
        setKeyStatus();
        setActionsMap();
        this.tank = tank;
        actionList = new ArrayList<>();
        this.iAmReadyForActions = true;
        this.currentAction = null;
        actionsList = new LinkedBlockingQueue<>();

        new Thread() {
            @Override
            public void run() {
                int a = 0;
                while (a != 14) {
                    //for (int i = 0; i < 15; i++) {
                        if (flag == false) {
                            System.out.println("I send service.Action #" + a);

                            actionsList.add(a);
                            a++;
                            flag = true;
                       // }
                    }

                    try {
                        //Thread.sleep(100);
                    } catch (Exception e) {

                    }
                }
                //System.out.println(Thread.currentThread().getName() + " - I am ready");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    if (flag == true) {
                        System.out.println("I read service.Action #" + actionsList.peek());
                        i++;
                        actionsList.remove();
                        flag = false;
                    }
                }
            }
        }.start();

    }

    public void nac() {
        //if (mapKeys.get(withKeyIsPress()) == true) {
        //if (!actionList.contains(mapActions.get(withKeyIsPress()))) {
        actionList.add(mapActions.get(withKeyIsPress()));
        nextAction(mapActions.get(withKeyIsPress()));
        //}
        //}
    }


    public void nextAction(Action action) {
        //System.out.println(action);
        try {

//            if (!actionList.contains(action)) {
//                actionList.add(action);
////                for (int i = 0; i < actionList.size(); i++){
////                    System.out.println(actionList.get(i) + "///");
////                }
//            } else {
//                //actionList.remove(0);
//            }
//            System.out.println("size = " + actionList.size());
            //while (actionList.size() > 0) {

            if (!tank.getMissionCompliet()) {
                //System.out.println("komand = " + action);
                tank.waitAction(actionList.get(0));
                actionList.remove(0);
            }

//                if (actionList.size() > 1){
//                    actionList.remove(0);
//                    if (withKeyIsPress() != -1) {
//                        generatorPress(mapActions.get(withKeyIsPress()), withKeyIsPress());
//                    }
//                }


            //}
            //System.out.println(actionList.size() + "**");
            //actionList.remove(0);

        } catch (Exception exp) {
            //ignore
        }
    }

    public int withKeyIsPress() {

        Set<Integer> keys = mapKeys.keySet();
        String a;
        for (Integer i : keys) {
            if (mapKeys.get(i) == true) {
                return i;
            }
        }
        return -1;
    }

    public void printWithKeyIsPress() {

        Set<Integer> keys = mapKeys.keySet();
        String a = "";
        for (Integer i : keys) {
            if (mapKeys.get(i) == true) {
                a = a + i + "_";
            }
        }
        System.out.println(a);
    }

    public void generatorPress(Action action, int a) {
        //while (getStartPress() == getFinishPress()) {
        while (withKeyIsPress() == a) {
            nextAction(action);
        }
//            try {
//                Thread.sleep(10);
//            }catch (Exception e){
//
//            }

        //}
    }

    public void translator(Action[] action) {
        boolean free = true;


        iAmReadyForActions = false;
        currentAction = action[0];
        try {
            if (action.length > 1) {

                tank.waitAction(currentAction);
                synchronized (tank) {
                    while (!tank.getMissionCompliet()) {
                        action.wait();
                    }
                    currentAction = action[1];
                }


            }

            tank.waitAction(currentAction);

        } catch (Exception ex) {

        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        new Thread() {
            @Override
            public void run() {


                mapKeys.put(e.getKeyCode(), true);
                try {
//            if (e.getKeyCode() == e.VK_SPACE) {
//                //nextAction(service.Action.FIRE);
//            }
//
//
//            if (e.getKeyCode() == e.VK_UP) {
//                //nextAction(service.Action.UP);
//            }
//            if (e.getKeyCode() == e.VK_RIGHT) {
//                nextAction(service.Action.RIGHT);
//            }
//            if (e.getKeyCode() == e.VK_LEFT) {
//                nextAction(service.Action.LEFT);
//            }
//            if (e.getKeyCode() == e.VK_DOWN) {
//                nextAction(service.Action.DOWN);
//            }

                } catch (Exception ex) {
                }
            }
        }.start();

        nac();


    }


    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.println(e.getKeyCode());
//        if (e.getKeyCode() == e.VK_SPACE) {
//            try {
//                tank.waitAction(service.Action.FIRE);
//            } catch (Exception ex) {
//
//            }
//        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        mapKeys.put(e.getKeyCode(), false);
        //setFinishPress(e.getKeyCode());
//        System.out.println(e.getExtendedKeyCode() + " ----------------");
//        System.out.println(e.getKeyCode() + "++++++++");
        //System.out.println(e.getKeyCode());

    }

    public int getStartPress() {
        return startPress;
    }

    public void setStartPress(int startPress) {
        this.startPress = startPress;
    }

    public int getFinishPress() {
        return finishPress;
    }

    public void setFinishPress(int finishPress) {
        this.finishPress = finishPress;
    }

    public void setKeyStatus() {
        mapKeys.put(KeyEvent.VK_UP, false);
        mapKeys.put(KeyEvent.VK_DOWN, false);
        mapKeys.put(KeyEvent.VK_LEFT, false);
        mapKeys.put(KeyEvent.VK_RIGHT, false);
        mapKeys.put(KeyEvent.VK_SPACE, false);
    }

    public void setActionsMap() {
        mapActions.put(KeyEvent.VK_UP, Action.UP);
        mapActions.put(KeyEvent.VK_DOWN, Action.DOWN);
        mapActions.put(KeyEvent.VK_LEFT, Action.LEFT);
        mapActions.put(KeyEvent.VK_RIGHT, Action.RIGHT);
        mapActions.put(KeyEvent.VK_SPACE, Action.FIRE);
    }

}
