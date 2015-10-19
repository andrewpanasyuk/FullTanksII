import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by panasyuk on 10.10.2015.
 */
public class FireListener implements KeyListener {
    AbstractTank tank;

    public FireListener(AbstractTank tank) {
        this.tank = tank;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            if (e.getKeyCode() == 32) {
                tank.fire();
            }
        } catch (Exception ex) {

        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
