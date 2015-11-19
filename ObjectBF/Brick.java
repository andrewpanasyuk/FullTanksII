package objectBF;

/**
 * Created by panasyuk on 27.06.2015.
 */
public class Brick extends Construct {

    public Brick(int x, int y) {
        setQandrantX(x);
        setQandrantY(y);
        setNameImage("brick.png");
        setImg(getNameImage());
        setArmor(1);
    }

    @Override
    public boolean destroy() {
        return super.destroy();
    }
}


