import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by panasyuk on 07.10.2015.
 */
public class Move implements KeyListener{
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==e.VK_W){
            System.out.println("++++++++++");
        }
//        if(e.getKeyCode()==e.VK_S){
//            moveDOWN();
//        }
//        if(e.getKeyCode()==e.VK_A){
//            moveLEFT();
//        }
//        if(e.getKeyCode()==e.VK_D){
//            moveRIGHT();
//        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyCode());

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyCode());

    }
}
