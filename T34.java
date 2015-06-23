/**
 * Created by panasyuk on 23.06.2015.
 */
public class T34 extends AbstractTank {
    public T34 (ActionField af, Field bf) {
        super(bf, af, 128, 512, Direction.UP);
    }

    public T34(Field bf, ActionField af, int x, int y, Direction direction) {
        super(bf, af, x, y, Direction.UP);
    }
}
