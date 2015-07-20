import ObjectBF.*;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class ActionField extends JPanel {
    private boolean COLORDED_MODE = false;
    private Field battleField;
    private Map<String, AbstractTank> tanks = new TreeMap<>();
//    private AbstractTank [] tanks;
//    private AbstractTank defender;
//    private AbstractTank agressor;
//    private BT7 bt7;
    private Bullet bullet;
    public ActionField actionField;

    void runTheGame() throws Exception {
       tanks.get("agressor").moveRandomWollFire();
//       tanks.get("defender").moveRandomWollFire();
//      defender.moveRandomWollFire();
//        defender.moveToQuadrant(1, 1);
        System.out.println(tanks.get("agressor").searchEagle() + "            !!!");
//        tanks.get("agressor").moveToQuadrant(7, 8);
        //tanks.get("agressor").destroyEagle();
//        tanks.get("agressor").moveToQuadrant(agressor.getY()/64+1, agressor.getX()/64+1);
//            abstractTank.moveRandomWoll();
//            abstractTank.moveRandom();

//		defender.move();
//		abstractTank.move();
//		abstractTank.move();
//        while (true) {
//            tanks.get("defender").fire();
//        }
//		abstractTank.move();
//		abstractTank.fire();
//		abstractTank.move();
//		abstractTank.turn(3);
//		abstractTank.move();
//		abstractTank.fire();
        //System.out.println("++");
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public ActionField getActionField() {
        return actionField;
    }

    public void processTurn(AbstractTank abstractTank) throws Exception {
        repaint();
    }

    public void processMove(AbstractTank abstractTank) throws Exception {
        int covered = 0;
        if (ControlField.controlWoll(battleField, abstractTank)) {
            while (covered < 64) {
                if (abstractTank.getDirection() == Direction.UP) {
                    abstractTank.updateY(-1);
                } else if (abstractTank.getDirection() == Direction.DOWN) {
                    abstractTank.updateY(1);
                } else if (abstractTank.getDirection() == Direction.LEFT) {
                    abstractTank.updateX(-1);
                } else {
                    abstractTank.updateX(1);
                }
                covered += 1;
                repaint();
                Thread.sleep(abstractTank.getSpeed());
            }
        }
    }

    public void processFire(Bullet bullet) throws Exception {
        this.bullet = bullet;
        int counter = 0;
        System.out.println("counter = " + counter);
        while (bullet.getX() >= -14 && bullet.getX() <= 590
                && bullet.getY() >= -14 && bullet.getY() <= 590) {
            if (bullet.getDirrect() == Direction.UP) {
                bullet.updateY(-1);
            } else if (bullet.getDirrect() == Direction.DOWN) {
                bullet.updateY(1);
            } else if (bullet.getDirrect() == Direction.LEFT) {
                bullet.updateX(-1);
            } else {
                bullet.updateX(1);
            }
            counter++;
            repaint();
            Thread.sleep(bullet.getSpeed());
            if (counter>40) {
                if (processInterception()) {
                    bullet.destroyBullet();
                    //controlTank();

                }
            }
        }
    }

    private boolean processInterception() throws Exception {

        String qad = getQadrant(bullet.getY(), bullet.getX());
        int separator = qad.indexOf("_");
        int y = Integer.parseInt(qad.substring(0, separator));
        int x = Integer.parseInt(qad.substring(separator + 1));
        if (y >= 0 && y <= 8 && x >= 0 && x <= 8) {
            if (battleField.scanQuadrant(y, x).getArmor() > 0) {
                babah(battleField.scanQuadrant(y, x), x, y, bullet.getArmorPiercing());
                    repaint();
                    return true;
            } else if (controlTank().length()>0){
                babah(tanks.get(controlTank()), y, x, bullet.getArmorPiercing());
                repaint();
                System.out.println(controlTank());
                return true;
            }
            return false;
        } else {
            return true;
        }

    }
//    public void popadanie(int x, int y,int a){
//        for (int i = 0; i<tanks.size(); i++){
//            if (tanks[i].getX() == x && tanks[i].getY() == y) {
//                babah(tanks[i], y, x, a);
//            }
//        }
//
//    }
public String controlTank() throws Exception{
    String k = "";
    for (String key: tanks.keySet()){
        if (bullet.getX()/64 == tanks.get(key).getX()/64 && bullet.getY()/64 == tanks.get(key).getY()/64){
            k = key;
        }
    }
    return k;



//    if (tanks.get("agressor").getX() == -100){
//        //agressor.returnAgressor();
//    }
}
    public String getQuadrantXY(int v, int h) {
        return (v - 1) * 64 + "_" + (h - 1) * 64;
    }

    public String getQadrant(int x, int y) {
        return y / 64 + "_" + x / 64;
    }

    public ActionField() throws Exception {
        battleField = new Field(1);
        bullet = new Bullet(-100, -100, Direction.EMPTY, 1);
        tanks.put("defender", new T34(this, battleField, bullet));
        tanks.put("agressor", new Tiger(this, battleField, bullet));
        //tanks.put("BT7", new BT7(this, battleField, bullet));

        JFrame frame = new JFrame("BATTLE FIELD, DAY 4 - my - ");
        frame.setLocation(750, 100);
        frame.setMinimumSize(new Dimension(battleField.getBfWidth(),
                battleField.getBfHeight() + 22));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int i = 0;
        Color cc;
        for (int v = 0; v < 9; v++) {
            for (int h = 0; h < 9; h++) {
                if (COLORDED_MODE) {
                    if (i % 2 == 0) {
                        cc = new Color(252, 241, 177);
                    } else {
                        cc = new Color(233, 243, 255);
                    }
                } else {
                    cc = new Color(180, 180, 180);
                }
                i++;
                g.setColor(cc);
                g.fillRect(h * 64, v * 64, 64, 64);
            }
        }

        for (int j = 0; j < battleField.getDimentionX(); j++) {
            for (int k = 0; k < battleField.getDimentionY(); k++) {
                if (battleField.scanQuadrant(j, k) instanceof Brick) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int x = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.drawImage(battleField.scanQuadrant(j, k).getImg(), j*64, k*64, new ImageObserver(){
                        @Override
                        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                            return false;
                        }
                    });
//                    g.setColor(battleField.scanQuadrant(j, k).getColor());
//                    g.fillRect(x, y, 64, 64);
                } else if (battleField.scanQuadrant(j, k) instanceof Rock) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int x = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.drawImage(battleField.scanQuadrant(j, k).getImg(), j*64, k*64, new ImageObserver(){
                        @Override
                        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                            return false;
                        }
                    });
//                    g.setColor(battleField.scanQuadrant(j, k).getColor());
//                    g.fillRect(x, y, 64, 64);
                } else if (battleField.scanQuadrant(j, k) instanceof Water) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int x = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.drawImage(battleField.scanQuadrant(j, k).getImg(), j*64, k*64, new ImageObserver(){
                        @Override
                        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                            return false;
                        }
                    });
//                    g.setColor(battleField.scanQuadrant(j, k).getColor());
//                    g.fillRect(x, y, 64, 64);
                } else if (battleField.scanQuadrant(j, k) instanceof Ampty) {

                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int x = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.drawImage(battleField.scanQuadrant(j, k).getImg(), j*64, k*64, new ImageObserver(){
                        @Override
                        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                            return false;
                        }
                    });
//                    g.setColor(battleField.scanQuadrant(j, k).getColor());
//                    g.fillRect(x, y, 64, 64);
                } else if (battleField.scanQuadrant(j, k) instanceof Eagle) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int x = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int y = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.drawImage(battleField.scanQuadrant(j, k).getImg(), j*64, k*64, new ImageObserver(){
                        @Override
                        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                            return false;
                        }
                    });



//                    g.setColor(battleField.scanQuadrant(j, k).getColor());
//                    g.fillRect(x, y, 64, 64);
                }
            }
        }

//        g.setColor(new Color(255, 0, 0));
//        g.fillRect(tanks.get("defender").getX(), tanks.get("defender").getY(), 64, 64);

//        g.drawImage(tanks.get("agressor").getImgUP(), tanks.get("agressor").getX()*64, tanks.get("agressor").getY()*64, new ImageObserver() {
//            @Override
//            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
//                return false;
//            }
//        });
//        g.setColor(new Color(155, 0, 0));
//        g.fillRect(tanks.get("agressor").getX(), tanks.get("agressor").getY(), 64, 64);


//        g.setColor(new Color(0, 255, 0));
        if (tanks.get("defender").getDirection() == Direction.UP) {
            g.drawImage(tanks.get("defender").getImgUP(), tanks.get("defender").getX(), tanks.get("defender").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });

//            g.fillRect(tanks.get("defender").getX() + 20, tanks.get("defender").getY(), 24, 34);
        } else if (tanks.get("defender").getDirection() == Direction.DOWN) {
            g.drawImage(tanks.get("defender").getImgD(), tanks.get("defender").getX(), tanks.get("defender").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
//            g.fillRect(tanks.get("defender").getX() + 20, tanks.get("defender").getY() + 30, 24, 34);
        } else if (tanks.get("defender").getDirection() == Direction.LEFT) {
            g.drawImage(tanks.get("defender").getImgL(), tanks.get("defender").getX(), tanks.get("defender").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
//            g.fillRect(tanks.get("defender").getX(), tanks.get("defender").getY() + 20, 34, 24);
        } else {
            g.drawImage(tanks.get("defender").getImgR(), tanks.get("defender").getX(), tanks.get("defender").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
//            g.fillRect(tanks.get("defender").getX() + 30, tanks.get("defender").getY() + 20, 34, 24);
        }

        g.setColor(new Color(255, 255, 0));
        g.fillRect(bullet.getX(), bullet.getY(), 14, 14);



        //g.setColor(new Color(0, 55, 0));
        if (tanks.get("agressor").getDirection() == Direction.UP) {
            g.drawImage(tanks.get("agressor").getImgUP(), tanks.get("agressor").getX(), tanks.get("agressor").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
//            g.fillRect(tanks.get("agressor").getX() + 20, tanks.get("agressor").getY(), 24, 34);
        } else if (tanks.get("agressor").getDirection() == Direction.DOWN) {
            g.drawImage(tanks.get("agressor").getImgD(), tanks.get("agressor").getX(), tanks.get("agressor").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
//            g.fillRect(tanks.get("agressor").getX() + 20, tanks.get("agressor").getY() + 30, 24, 34);
        } else if (tanks.get("agressor").getDirection() == Direction.LEFT) {
            g.drawImage(tanks.get("agressor").getImgL(), tanks.get("agressor").getX(), tanks.get("agressor").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
//            g.fillRect(tanks.get("agressor").getX(), tanks.get("agressor").getY() + 20, 34, 24);
        } else {
            g.drawImage(tanks.get("agressor").getImgR(), tanks.get("agressor").getX(), tanks.get("agressor").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
//            g.fillRect(tanks.get("agressor").getX() + 30, tanks.get("agressor").getY() + 20, 34, 24);
        }
    }

    public void babah(Destroy element, int y, int x, int a) {
        //System.out.println(element.toString().toString());
       if (element.destroy(a)){
           battleField.updateQuadrant(y, x, new Ampty(y, x));
       }
    }
}