import ObjectBF.*;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
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
//        tanks.get("agressor").destroyEagle();
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
        if (ControlField.controlWoll(battleField, abstractTank, actionField)) {
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
            if (counter > 40) {
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
            } else if (controlTank().length() > 0) {
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
    public String controlTank() {
        String k = "";
        for (String key : tanks.keySet()) {
            if (bullet.getX() / 64 == tanks.get(key).getX() / 64 && bullet.getY() / 64 == tanks.get(key).getY() / 64) {
                k = key;
            }
        }
        return k;
//    try {
//        setImg(ImageIO.read(new File(getNameImage())));
//    } catch (IOException e) {
//        System.out.println("cannot found image: " + getNameImage());
//    }


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
        frame.setMinimumSize(new Dimension(battleField.getBfWidth() + 20,
                battleField.getBfHeight() + 35));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        for (int j = 0; j < battleField.getDimentionX(); j++) {
            for (int k = 0; k < battleField.getDimentionY(); k++) {
                String coordinates = getQuadrantXY(j + 1, k + 1);
                int separator = coordinates.indexOf("_");
                int y = Integer.parseInt(coordinates
                        .substring(0, separator));
                int x = Integer.parseInt(coordinates
                        .substring(separator + 1));
                g.drawImage(battleField.scanQuadrant(j, k).getImg(), j * 64, k * 64, new ImageObserver() {
                    @Override
                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                        return false;
                    }
                });
            }
        }


//        if (tanks.get(controlTank()).getDirection() == Direction.UP){
//            tanks.get(controlTank()).setNameImage("T34_UP.png");
//            g.drawImage(tanks.get(controlTank()).getImg(), tanks.get(controlTank()).getX(), tanks.get(controlTank()).getY(), new ImageObserver() {
//                @Override
//                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
//                    return false;
//                }
//            });
//            System.out.println("5555555555555555555555555555555");
//        }
        if (tanks.get("defender").getDirection() == Direction.UP) {
            tanks.get("defender").setNameImageUP("T34_UP.png");
            g.drawImage(tanks.get("defender").getImgUP(), tanks.get("defender").getX(), tanks.get("defender").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });

        } else if (tanks.get("defender").getDirection() == Direction.DOWN) {
            tanks.get("defender").setNameImageD("T34_D.png");
            g.drawImage(tanks.get("defender").getImgD(), tanks.get("defender").getX(), tanks.get("defender").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        } else if (tanks.get("defender").getDirection() == Direction.LEFT) {
            tanks.get("defender").setNameImageL("T34_L.png");
            g.drawImage(tanks.get("defender").getImgL(), tanks.get("defender").getX(), tanks.get("defender").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        } else {
            tanks.get("defender").setNameImageR("T34_R.png");
            g.drawImage(tanks.get("defender").getImgR(), tanks.get("defender").getX(), tanks.get("defender").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        }

//        g.setColor(Color.YELLOW);
//        g.fillOval(bullet.getX(), bullet.getY(), 10, 10);


        if (tanks.get("agressor").getDirection() == Direction.UP) {
            tanks.get("agressor").setNameImageUP("Tank_UP.png");

            g.drawImage(tanks.get("agressor").getImgUP(), tanks.get("agressor").getX(), tanks.get("agressor").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        } else if (tanks.get("agressor").getDirection() == Direction.DOWN) {
            tanks.get("agressor").setNameImageD("Tank_D.png");

            g.drawImage(tanks.get("agressor").getImgD(), tanks.get("agressor").getX(), tanks.get("agressor").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        } else if (tanks.get("agressor").getDirection() == Direction.LEFT) {
            tanks.get("agressor").setNameImageL("Tank_L.png");
            g.drawImage(tanks.get("agressor").getImgL(), tanks.get("agressor").getX(), tanks.get("agressor").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        } else {
            tanks.get("agressor").setNameImageR("Tank_R.png");
            g.drawImage(tanks.get("agressor").getImgR(), tanks.get("agressor").getX(), tanks.get("agressor").getY(), new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
        }

        for (int j = 0; j < battleField.getDimentionX(); j++) {
            for (int k = 0; k < battleField.getDimentionY(); k++) {
                String coordinates = getQuadrantXY(j + 1, k + 1);
                int separator = coordinates.indexOf("_");
                int y = Integer.parseInt(coordinates
                        .substring(0, separator));
                int x = Integer.parseInt(coordinates
                        .substring(separator + 1));
                if (battleField.scanQuadrant(j, k) instanceof Water) {
                    //battleField.scanQuadrant(j, k).setNameImage("water_pr.png");
                    g.drawImage(battleField.scanQuadrant(j, k).getImg(), j * 64, k * 64, new ImageObserver() {
                        @Override
                        public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                            return false;
                        }

                    });
                }
            }
        }



        g.setColor(Color.YELLOW);
        g.fillOval(bullet.getX(), bullet.getY(), 10, 10);
    }

    public void babah(Destroy element, int y, int x, int a) {
        if (element.destroy(a)) {
            battleField.updateQuadrant(y, x, new Ampty(y, x));
        }
    }
}