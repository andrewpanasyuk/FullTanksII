import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by panasyuk on 17.06.2015.
 */
public class Tiger extends AbstractTank {
    public Tiger(){

    }

    public Tiger(ActionField af, BField bf, Bullet bullet) throws Exception {
        super(af, bf);
        setName("agressor");
        newBaseAgressor();
        setArmor(2);
        setPower(2);




        setNameImageUP("Tank_UP.png");
        //setImg(getNameImage());
        setNameImageD("Tank_D.png");
        setNameImageL("Tank_L.png");
        setNameImageR("Tank_R.png");



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

//    public Tiger(ActionField af, BField bf, int x, int y, Direction direction) throws Exception {
//        super(bf, af, x, y, direction);
//        setName("agressor");
//        setArmor(5);
//        newBaseAgressor();
//        bullet.setArmorPiercing(2);
//        setNameImage("Tank_UP.png");
//        try {
//            setImg(ImageIO.read(new File(getNameImage())));
//        } catch (IOException e) {
//            System.out.println("cannot found image: " + getNameImage());
//        }
//
//
//    }

    public void newBaseAgressor() throws Exception {
        setX(2 * 64);
        setY(3 * 64);
        setArmor(2);

        Thread.sleep(300);
//    int a = Generation.gen(1, 3);
//    if (a == 1) {
//        setX(0*64);
//        setY(0*64);
//    } else if (a == 2) {
//        setX(5*64);
//        setY(5*64);
//    } else if (a==3) {
//        setX(7*64);
//        setY(4*64);
//    }
        //bf.updateQuadrant((getY()/64), (getX()/64), this);

        System.out.println(getX() + "___" + getY());
        System.out.println("x=" + getX() / 64 + "___" + getY() / 64);
        System.out.println(bf.scanQuadrant(getX() / 64, getY() / 64));

    }
//    public int getArmor() {
//        return armor;
//    }

//    public void setArmor(int armor) {
//        this.armor = armor;
//    }

    public void returnAgressor() throws Exception {
        Thread.sleep(3000);
        newBaseAgressor();
        //setArmor(2);
    }

    @Override
    public boolean destroy(int a) {
        setArmor(getArmor() - 1);
        if (getArmor() > 0) {
            return false;
        } else {
            setY(-100);
            setX(-100);
            return true;

        }
    }
//    @Override
//    public void keyPressed(KeyEvent e) {
//        if(e.getKeyCode()==e.VK_W){
//            System.out.println("++++++++++");
//        }
////        if(e.getKeyCode()==e.VK_S){
////            moveDOWN();
////        }
////        if(e.getKeyCode()==e.VK_A){
////            moveLEFT();
////        }
////        if(e.getKeyCode()==e.VK_D){
////            moveRIGHT();
////        }
//    }
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//        System.out.println(e.getKeyCode());
//
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        System.out.println(e.getKeyCode());
//
//    }
}

