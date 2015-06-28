import ObjectBF.Ampty;

/**
 * Created by panasyuk on 16.06.2015.
 */
public class ControlField {
    private boolean field;
    private boolean woll;
    private Field bf;
    private AbstractTank abstractTank;

    public static boolean controlTank(Field bf, AbstractTank abstractTank) {
//		this.bf=bf;
//		this.abstractTank=abstractTank;
        int finish = 512;
        int nextPosition = -100;


        if (abstractTank.getDirection() == Direction.UP) {
            nextPosition = abstractTank.getY() - 1;
        } else if (abstractTank.getDirection() == Direction.DOWN) {
            nextPosition = abstractTank.getY() + 1;
        } else if (abstractTank.getDirection() == Direction.LEFT) {
            nextPosition = abstractTank.getX() - 1;
        } else {
            nextPosition = abstractTank.getX() + 1;
        }
        if (nextPosition > 0 && nextPosition < finish) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean controlWoll(Field bf, AbstractTank abstractTank) {
        int y = abstractTank.getY() / 64;
        int x = abstractTank.getX() / 64;
        if (abstractTank.getDirection() == Direction.UP && y != 0) {
            y = y - 1;
        } else if (abstractTank.getDirection() == Direction.DOWN && y != 8) {
            y = y + 1;
        } else if (abstractTank.getDirection() == Direction.LEFT && x != 0) {
            x = x - 1;
        } else if (abstractTank.getDirection() == Direction.RIGHT && x != 8) {
            x = x + 1;
        }
//        System.out.println("x: " + x);
//        System.out.println("y: " + y);
        if (bf.getBatlefield()[y][x] instanceof Ampty) {
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
