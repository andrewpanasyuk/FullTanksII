/**
 * Created by panasyuk on 16.06.2015.
 */
public class ControlField {
    private boolean field;
    private boolean woll;
    private Field bf;
    private Tank tank;

    public static boolean controlTank(Field bf, Tank tank) {
//		this.bf=bf;
//		this.tank=tank;
        int finish = 512;
        int nextPosition = -100;


        if (tank.getDirection() == 1) {
            nextPosition = tank.getY() - 1;
        } else if (tank.getDirection() == 2) {
            nextPosition = tank.getY() + 1;
        } else if (tank.getDirection() == 3) {
            nextPosition = tank.getX() - 1;
        } else {
            nextPosition = tank.getX() + 1;
        }
        if (nextPosition > 0 && nextPosition < finish) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean controlWoll(Field bf, Tank tank) {
        int y = tank.getY() / 64;
        int x = tank.getX() / 64;
        if (tank.getDirection() == 1 && y != 0) {
            y = y - 1;
        } else if (tank.getDirection() == 2 && y != 8) {
            y = y + 1;
        } else if (tank.getDirection() == 3 && x != 0) {
            x = x - 1;
        } else if (tank.getDirection() == 4 && x != 8) {
            x = x + 1;
        }
        System.out.println("x: " + x);
        System.out.println("y: " + y);
        if (bf.getBatlefield()[y][x]!="B") {
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
