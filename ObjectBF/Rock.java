package objectBF;

/**
 * Created by panasyuk on 27.06.2015.
 */
public class Rock extends Construct {
    public Rock(int x, int y) {
        setQandrantX(x);
        setQandrantY(y);
        setNameImage("woll.png");
        setImg(getNameImage());
        setArmor(2);
    }

    @Override
    public boolean destroy(int a) {
        if (a > 1) {
            setArmor(getArmor() - 1);
            if (getArmor() >= 1) {

                setNameImage("destroy_woll.png");
                setImg(getNameImage());
            } else  {
                setNameImage("dor.png");
                setImg(getNameImage());

                return true;
            }
        }
//        if (getArmor() == 0) {
//            return true;
//        }
        return false;
    }
}

