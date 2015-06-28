package ObjectBF;

import java.awt.*;

/**
 * Created by panasyuk on 27.06.2015.
 */
public class Construct implements Destroy {
    private int qandrantX;
    private int qandrantY;
    private Color color;
    private int armor;

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

            setArmor(getArmor() - 1);
            if (getArmor() == 0) {
                return true;
        }
        return false;
    }

    @Override
    public boolean destroy() {
        return false;
    }
}
