/**
 * Created by panasyuk on 17.06.2015.
 */
public class BT7 extends AbstractTank {
    public BT7(ActionField af, BField bf, Bullet bullet)throws Exception{

        super(af, bf);

        setName("BT7");
        newBaseBT7();
        speed = 5;
    }
    public BT7(ActionField af, BField bf, int x, int y, Direction direction) throws Exception{

        super(bf, af, x, y, Direction.UP);
        speed = 5;
        newBaseBT7();
        setName("BT7");
    }
    public void newBaseBT7() throws Exception {
        setX(2 * 64);
        setY(3 * 64);
        setArmor(2);
    }

}
