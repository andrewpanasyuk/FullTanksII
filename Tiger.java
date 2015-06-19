/**
 * Created by panasyuk on 17.06.2015.
 */
public class Tiger extends Tank {
private int armor;

public Tiger(ActionField af, Field bf){
    super(af, bf);
    this.armor = 1;
    newBaseAgressor();
}
    public  Tiger(ActionField af, Field bf, int x, int y, Direction direction) {
        super(bf, af, x, y, direction);
        this.armor = 1;


    }
public void newBaseAgressor(){
    int a = Generation.gen(1, 3);
    if (a == 1) {
        setX(0);
        setY(0);
    } else if (a == 2) {
        setX(320);
        setY(320);
    } else if (a==3) {
        setX(448);
        setY(256);
    }
    this.bf.updateQuadrant((getX()/64), (getY()/64), "T");

}
    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
