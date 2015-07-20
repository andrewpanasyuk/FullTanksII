package ObjectBF;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by panasyuk on 27.06.2015.
 */
public class Rock extends Construct {
    public Rock(int x, int y) {
        setQandrantX(x);
        setQandrantY(y);
        //setColor(Color.GRAY);
        setNameImage("woll.png");
        try {
            setImg(ImageIO.read(new File(getNameImage())));
        } catch (IOException e) {
            System.out.println("cannot found image: " + getNameImage());
        }

        setArmor(2);
    }

    @Override
    public boolean destroy(int a) {


        if (a > 1) {
            setArmor(getArmor() - 1);
            setNameImage("destroy_woll.png");
            try {
                setImg(ImageIO.read(new File(getNameImage())));
            } catch (IOException e) {
                System.out.println("cannot found image: " + getNameImage());
            }
            if (getArmor() == 0) {
                return true;
            }
            return false;
        }
        return false;
    }
}

