package tanks;

import filds.ActionField;
import filds.Batlefild;
import service.Direction;

/**
 * Created by panasyuk on 17.06.2015.
 */
public class Tiger extends AbstractTank {
    public Tiger(){

    }

    public Tiger(ActionField af, Batlefild bf) throws Exception {
        super(af, bf);
        setName("agressor");
        setArmor(2);
        setPower(2);
        getDirectionImageTank();
        setAmmunition(3);
        newBaseAgressor();

    }


    public void newBaseAgressor() throws Exception {
        setX(8 * 64);
        setY(0 * 64);
        setArmor(2);
        setDirection(Direction.DOWN);
        Thread.sleep(300);
    }

    public void returnAgressor() throws Exception {
        Thread.sleep(3000);
        newBaseAgressor();
    }

    @Override
    public boolean destroy(int a) {
        setArmor(getArmor() - 1);
        if (getArmor() > 0) {
            return false;
        } else {
            setY(-100);
            setX(-100);
            return true;

        }
    }

}

