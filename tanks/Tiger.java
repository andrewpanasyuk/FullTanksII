package tanks;

import filds.ActionField;
import objectBF.Batlefild;
import service.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by panasyuk on 17.06.2015.
 */
public class Tiger extends AbstractTank {
    public Tiger(){

    }

    public Tiger(ActionField af, Batlefild bf) throws Exception {
        super(af, bf);
        setName("agressor");

        setArmor(2);
        setPower(2);
        getDirectionImageTank();
        setAmmunition(2);
        newBaseAgressor();

    }


    public void newBaseAgressor() throws Exception {
        setX(2 * 64);
        setY(3 * 64);
        setArmor(2);

        Thread.sleep(300);

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

