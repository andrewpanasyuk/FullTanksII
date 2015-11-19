import bullet.Bullet;
import filds.ActionField;
import tanks.AbstractTank;

import java.util.List;

/**
 * Created by panasyuk on 18.10.2015.
 */
public class Shot implements Runnable {
    private ActionField af;
    private List <Bullet> bullets;
    AbstractTank tank;



    public Shot(AbstractTank tank, Bullet bullet, ActionField af){

    }
    public Shot(AbstractTank tank, ActionField af){
        this.af=af;
        bullets = tank.getBulletMagazin();
        this.tank = tank;

    }


    @Override
    public void run() {
        System.out.println("i am srart!");
        while (true) {
            //for (int i = 0; i < bullets.size(); i ++){
            if (bullets.size() >0){
                af.bullets.put(tank.getName(), bullets.get(0) );
                //af.bulletsActive.add(bullets.get(0));
                bullets.clear();
            }

            //System.out.println(bullets.size() + "   shot");
            try {
                Thread.sleep(10);
            }catch (Exception e){

            }
        }


    }
}
