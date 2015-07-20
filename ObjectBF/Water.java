package ObjectBF;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

/**
 * Created by panasyuk on 27.06.2015.
 */
public class Water extends Construct{
    public Water(int x, int y){
        setQandrantX(x);
        setQandrantY(y);
//        setColor(Color.blue);
        setNameImage("water.png");
        try {
            setImg(ImageIO.read(new File(getNameImage())));
        } catch (IOException e) {
            System.out.println("cannot found image: " + getNameImage());
        }


        setArmor(-1);
    }
}
