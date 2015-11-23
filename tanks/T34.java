package tanks;

import filds.ActionField;
import filds.Batlefild;
import service.Direction;

/**
 * Created by panasyuk on 23.06.2015.
 */
public class T34 extends AbstractTank {
    public T34 (ActionField af, Batlefild bf) {
        super(bf, af, 128, 512, Direction.UP);
        setName("defender");
        setArmor(1);
        setAmmunition(3);
        getDirectionImageTank();
//

    }


//    public tanks.T34(filds.Batlefild bf, filds.ActionField af, int x, int y, service.Direction direction) {
//        super(bf, af, x, y, service.Direction.UP);
//        setName("defender");
//        setArmor(1);
//        try {
//            setImg(ImageIO.read(new File(getNameImage())));
//        } catch (IOException e) {
//            System.out.println("cannot found image: " + getNameImage());
//        }
//    }
}
