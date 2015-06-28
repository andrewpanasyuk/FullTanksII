import ObjectBF.Destroy;

/**
 * Created by panasyuk on 17.06.2015.
 */
public class Tiger extends AbstractTank  {

public Tiger(ActionField af, Field bf, Bullet bullet) throws  Exception{
    super(af, bf);
    newBaseAgressor();
    setArmor(2);
    setPower(2);
}
    public  Tiger(ActionField af, Field bf, int x, int y, Direction direction) throws Exception{
        super(bf, af, x, y, direction);
        setArmor(5);
        newBaseAgressor();
        bullet.setArmorPiercing(2);


    }
public void newBaseAgressor() throws  Exception{
    setX(0*64);
    setY(0*64);
    setArmor(2);

    //Thread.sleep(300);
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
//    System.out.println("a=" + a);
   // bf.updateQuadrant((getY()/64), (getX()/64), this);
    System.out.println(getX() + "___" + getY());
    System.out.println("x=" + getX()/64 + "___" + getY()/64);
    System.out.println(bf.scanQuadrant(getX() / 64, getY() / 64));

}
//    public int getArmor() {
//        return armor;
//    }

//    public void setArmor(int armor) {
//        this.armor = armor;
//    }

    public void returnAgressor() throws Exception{
        Thread.sleep(3000);
        newBaseAgressor();
        //setArmor(2);
    }
}
