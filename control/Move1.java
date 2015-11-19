package control;

import filds.ActionField;
import service.Action;
import tanks.AbstractTank;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by panasyuk on 16.10.2015.
 */
public class Move1 implements KeyListener {
    private AbstractTank abstractTank;
    private Queue<Action> actionsList;
    volatile private Map<Integer, Boolean> mapKeys;
    private Map<Integer, Action> mapActions;
    volatile private Action currentAction;
    private ActionField actionField;


    public Move1(AbstractTank abstractTank, ActionField actionField) {
        this.actionField = actionField;
        this.abstractTank = abstractTank;
        actionsList = new LinkedBlockingQueue<>();
        currentAction = null;
//        mapKeys = new TreeMap<>();
        mapKeys = new LinkedHashMap<>();
        setKeyStatus();
        mapActions = new TreeMap<>();
        setActionsMap();

        new Thread() {
            @Override
            public void run() {
                while (true) {

                    try {
                        abstractTank.waitAction(actionsList.peek());
                        actionsList.remove();
                    } catch (Exception e) {

                    }
                }
            }
        }.start();
//        new Thread() {
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(500);
//                    } catch (Exception r) {
//
//                    }
//                    if (currentAction == null && withKeyIsPress() != -1) {
//                        controlPress(withKeyIsPress());
//                    }
//                }
//            }
//        }.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (mapKeys.containsKey(e.getKeyCode())) {
            mapKeys.put(e.getKeyCode(), true);
            currentAction = mapActions.get(e.getKeyCode());
            //System.out.println(currentAction.toString());
            //actionField.setListAction(new ArrayList<>());
            //System.out.println(actionField.getListAction().size());
//            actionField.getListAction().add(currentAction);

                    controlPress(e.getKeyCode());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (mapKeys.containsKey(e.getKeyCode())) {
            currentAction = null;
            mapKeys.put(e.getKeyCode(), false);
        }
    }

    public void controlPress(Integer a) {

        if (!actionsList.contains(mapActions.get(a))) {
            actionsList.add(mapActions.get(a));
        }

    }

    public void setKeyStatus() {
        mapKeys.put(KeyEvent.VK_UP, false);
        mapKeys.put(KeyEvent.VK_DOWN, false);
        mapKeys.put(KeyEvent.VK_LEFT, false);
        mapKeys.put(KeyEvent.VK_RIGHT, false);
        mapKeys.put(KeyEvent.VK_W, false);
    }

    public int withKeyIsPress() {

        Set<Integer> keys = mapKeys.keySet();
        for (Integer i : keys) {
            if (mapKeys.get(i) == true) {
                return i;
            }
        }
        return -1;
    }

    public void setActionsMap() {
        mapActions.put(KeyEvent.VK_UP, Action.UP);
        mapActions.put(KeyEvent.VK_DOWN, Action.DOWN);
        mapActions.put(KeyEvent.VK_LEFT, Action.LEFT);
        mapActions.put(KeyEvent.VK_RIGHT, Action.RIGHT);
        mapActions.put(KeyEvent.VK_W, Action.FIRE);
    }
}