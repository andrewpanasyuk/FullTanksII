package objectBF;

import service.Destroy;
import service.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * Created by panasyuk on 27.06.2015.
 */
public abstract class Construct implements Destroy, Drawable {
    private int qandrantX;
    private int qandrantY;
    private Color color;
    private int armor;
    private String nameImage;
    private Image img;




    public Image getImg() {
        return img;
    }


    public void setImg(String name) {
        try {
            this.img = ImageIO.read(new File("imageBF/" + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getQandrantX() {
        return qandrantX;
    }

    public void setQandrantX(int qandrantX) {
        this.qandrantX = qandrantX;
    }

    public int getQandrantY() {
        return qandrantY;
    }

    public void setQandrantY(int qandrantY) {
        this.qandrantY = qandrantY;
    }

    @Override
    public boolean destroy(int a) {
        System.out.println("armor do = " + getArmor());
            setArmor(getArmor() - 1);
            if (getArmor() == 0) {
                System.out.println("armor posle = " + getArmor());
//                setNameImage("dor.png");
//                setImg(getNameImage());
                return true;
        }
        return false;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getImg(), getQandrantX()*64, getQandrantY()*64,new ImageObserver() {
                    @Override
                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return false;
                    }
                });
    }

    @Override
    public boolean destroy() {
        return false;
    }

}
