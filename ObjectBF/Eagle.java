package objectBF;

/**
 * Created by panasyuk on 27.06.2015.
 */
public class Eagle extends Construct {
    public Eagle(int x, int y) {
        setQandrantX(x);
        setQandrantY(y);
        setNameImage("eagle.png");
        setImg(getNameImage());
        setArmor(5);
    }

    @Override
    public boolean destroy(int a) {
        if (getArmor() == 0){

        }
        return super.destroy(a);
    }
}

