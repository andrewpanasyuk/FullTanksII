package ObjectBF;

import java.awt.*;

/**
 * Created by panasyuk on 27.06.2015.
 */
public class Brick extends Construct {
    public Brick() {
        setQandrantX(-100);
        setQandrantY(-100);
        setColor(Color.RED);
        setArmor(1);
    }

    public Brick(int x, int y) {
        setQandrantX(x);
        setQandrantY(y);
        setColor(Color.PINK);
        setArmor(1);
    }

    @Override
    public boolean destroy() {
        return super.destroy();
    }
}


