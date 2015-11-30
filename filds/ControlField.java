package filds;

import objectBF.Empty;
import objectBF.Water;
import service.Direction;
import tanks.AbstractTank;

/**
 * Created by panasyuk on 16.06.2015.
 */
public class ControlField {
    private boolean field;
    private boolean woll;
    private Batlefild bf;
    private AbstractTank abstractTank;
    private ActionField af;

    public static boolean controlTank(Batlefild bf, AbstractTank abstractTank) {
        System.out.println("x = " + abstractTank.getX());
        System.out.println("y = " + abstractTank.getY());
        int finish = 512;
        int nextPosition = 0;


        if (abstractTank.getDirection() == Direction.UP) {
            nextPosition = abstractTank.getY() - 1;
        } else if (abstractTank.getDirection() == Direction.DOWN) {
            nextPosition = abstractTank.getY() + 1;
        } else if (abstractTank.getDirection() == Direction.LEFT) {
            nextPosition = abstractTank.getX() - 1;
        } else {
            nextPosition = abstractTank.getX() + 1;
        }
        if (nextPosition >= 0 && nextPosition <= finish) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean controlWoll(Batlefild bf, AbstractTank abstractTank, ActionField af) {

            int y = abstractTank.getY() / 64;
            int x = abstractTank.getX() / 64;
        if (y < 0){
            return false;
        }
            if (abstractTank.getDirection() == Direction.UP && y != 0) {
                y = y - 1;
            } else if (abstractTank.getDirection() == Direction.DOWN && y != 8) {
                y = y + 1;
            } else if (abstractTank.getDirection() == Direction.LEFT && x != 0) {
                x = x - 1;
            } else if (abstractTank.getDirection() == Direction.RIGHT && x != 8) {
                x = x + 1;
            }

            if (bf.getBatlefield()[y][x] instanceof Empty || bf.getBatlefield()[y][x] instanceof Water) {
//            if (bf.getBatlefield()[y][x] instanceof Water) {
////                af.paintComponent(Graphics g){
////
////                };
//               // bf.getBatlefield()[y][x] = new Water(x, y, 1);
//
//
//
//
////                af.paintComponent();
//
////                af.getBatlefield()[y][x] =  new Empty(x, y);
////                abstractTank.
//
//            }
                return true;
            } else {
                return false;
            }
        }

    public boolean isField() {
        return field;
    }
    public boolean isWoll() {
        return woll;
    }

}
