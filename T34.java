import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by panasyuk on 23.06.2015.
 */
public class T34 extends AbstractTank {
    public T34 (ActionField af, Field bf, Bullet bullet) {
        super(bf, af, 128, 512, Direction.UP);
        setName("defender");
        setArmor(1);
        setNameImageUP("T34_UP.png");
        setNameImageD("T34_D.png");
        setNameImageL("T34_L.png");
        setNameImageR("T34_R.png");
//
        try {
            setImgUP(ImageIO.read(new File(getNameImageUP())));
        } catch (IOException e) {
            System.out.println("cannot found image: " + getNameImageUP());
        }
        try {
            setImgD(ImageIO.read(new File(getNameImageD())));
        } catch (IOException e) {
            System.out.println("cannot found image: " + getNameImageD());
        }
        try {
            setImgL(ImageIO.read(new File(getNameImageL())));
        } catch (IOException e) {
            System.out.println("cannot found image: " + getNameImageL());
        }
        try {
            setImgR(ImageIO.read(new File(getNameImageR())));
        } catch (IOException e) {
            System.out.println("cannot found image: " + getNameImageR());
        }

    }

//    public T34(Field bf, ActionField af, int x, int y, Direction direction) {
//        super(bf, af, x, y, Direction.UP);
//        setName("defender");
//        setArmor(1);
//        try {
//            setImg(ImageIO.read(new File(getNameImage())));
//        } catch (IOException e) {
//            System.out.println("cannot found image: " + getNameImage());
//        }
//    }
}
