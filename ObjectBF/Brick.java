package ObjectBF;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by panasyuk on 27.06.2015.
 */
public class Brick extends Construct {

    public Brick(int x, int y) {
        setQandrantX(x);
        setQandrantY(y);
//        setColor(Color.PINK);
        setNameImage("kirp.png");

        try {
            setImg(ImageIO.read(new File(getNameImage())));
        } catch (IOException e) {
            System.out.println("cannot found image: " + getNameImage());
        }

        setArmor(1);


    }

    @Override
    public boolean destroy() {
        return super.destroy();
    }
}


