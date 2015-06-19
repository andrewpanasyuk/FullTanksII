/**
 * Created by panasyuk on 17.06.2015.
 */
public class BT7 extends Tank {
    public BT7(ActionField af, Field bf){
        super(af, bf);
        speed = 5;
    }
    public BT7(ActionField af, Field bf, int x, int y, Direction direction){
        super(bf, af, x, y, Direction.UP);
        speed = 5;
    }


}
