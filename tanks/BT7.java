package tanks;

import bullet.Bullet;
import filds.ActionField;
import objectBF.Batlefild;
import service.Direction;

/**
 * Created by panasyuk on 17.06.2015.
 */
public class BT7 extends AbstractTank {
    public BT7(ActionField af, Batlefild bf, Bullet bullet)throws Exception{

        super(af, bf);

        setName("tanks.BT7");
        newBaseBT7();
        speed = 5;
    }
    public BT7(ActionField af, Batlefild bf, int x, int y, Direction direction) throws Exception{

        super(bf, af, x, y, Direction.UP);
        speed = 5;
        newBaseBT7();
        setName("tanks.BT7");
    }
    public void newBaseBT7() throws Exception {
        setX(2 * 64);
        setY(3 * 64);
        setArmor(2);
    }

}
