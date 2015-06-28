package ObjectBF;

import java.awt.*;

/**
 * Created by panasyuk on 27.06.2015.
 */
public class Rock extends Construct {
    public Rock(int x, int y) {
        setQandrantX(x);
        setQandrantY(y);
        setColor(Color.GRAY);
        setArmor(2);
    }

    @Override
    public boolean destroy(int a) {
        if (a > 1) {
            setArmor(getArmor() - 1);
            if (getArmor() == 0) {
                return true;
            }
            return false;
        }
        return false;
    }
}

