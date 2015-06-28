package ObjectBF;

import java.awt.*;

/**
 * Created by panasyuk on 27.06.2015.
 */
public class Ampty extends Construct {
    public Ampty() {
//        setQandrantX(x);
//        setQandrantY(y);
        setColor(Color.blue);
        setArmor(0);
    }

    public Ampty(int x, int y) {
        setQandrantX(x);
        setQandrantY(y);
        setColor(Color.lightGray);
        setArmor(0);
    }
}
