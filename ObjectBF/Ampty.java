package ObjectBF;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by panasyuk on 27.06.2015.
 */
public class Ampty extends Construct {

    public Ampty(int x, int y) {
        setQandrantX(x);
        setQandrantY(y);
        //setColor(Color.lightGray);
        setNameImage("dor.png");

        try {
            setImg(ImageIO.read(new File(getNameImage())));
        } catch (IOException e) {
            System.out.println("cannot found image: " + getNameImage());
        }


        setArmor(0);
    }
}
