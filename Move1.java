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


    public Move1(AbstractTank abstractTank) {
        this.abstractTank = abstractTank;
        actionsList = new LinkedBlockingQueue<>();

//        mapKeys = new TreeMap<>();
        mapKeys = new LinkedHashMap<>();
        setKeyStatus();

        mapActions = new TreeMap<>();
        setActionsMap();

        readList();


    }

    public void readList() {
        new Thread() {
            @Override
            public void run() {
                while (true) {

                    if (actionsList.size() > 0) {
                        try {
                            abstractTank.waitAction(actionsList.peek());
                            actionsList.remove();
                        } catch (Exception e) {

                        }
                    } else
                        if (withKeyIsPress() != -1) {
                           // System.out.println(withKeyIsPress() + " **");
                            controlPress(withKeyIsPress());
                            try {
                                Thread.sleep(100);
                            } catch (Exception ex) {

                            }
                        }


                }
            }
        }.start();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (mapKeys.containsKey(e.getKeyCode())) {
            mapKeys.put(e.getKeyCode(), true);
            controlPress(e.getKeyCode());
            try {
                //Thread.sleep(50);
            }catch (Exception ee){

            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        new Thread() {
            @Override
            public void run() {
                    mapKeys.put(e.getKeyCode(), false);
            }
        }.start();


    }

    public void controlPress(Integer a) {
        if (!actionsList.contains(mapActions.get(a))) {
            actionsList.add(mapActions.get(a));
        }

    }

    public void setKeyStatus() {
        mapKeys.put(KeyEvent.VK_UP, false);
        mapKeys.put(KeyEvent.VK_DOWN, false);
        mapKeys.put(KeyEvent.VK_W, false);
        mapKeys.put(KeyEvent.VK_RIGHT, false);
        mapKeys.put(KeyEvent.VK_SPACE, false);
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
