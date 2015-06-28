import ObjectBF.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class ActionField extends JPanel {
    private boolean COLORDED_MODE = false;
    private Field battleField;
    private ConstructField cf;
    //private AbstractTank abstractTank;
    private T34 defender;
    private Tiger agressor;
    private BT7 bt7;
    private Bullet bullet;
    public ActionField actionField;

    void runTheGame() throws Exception {
//bt7 = new BT7(actionField, battleField);
//            System.out.println(bt7.getSpeed());
       agressor.moveRandomWollFire();
      //defender.moveRandomWollFire();
//        defender.moveToQuadrant(1, 1);
//         defender.moveToQuadrant(agressor.getY()/64+1, agressor.getX()/64+1);
//            abstractTank.moveRandomWoll();
//            abstractTank.moveRandom();

//		defender.move();
//		abstractTank.move();
//		abstractTank.move();
        while (true) {
            defender.fire();
        }
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
        System.out.println("AP = " + bullet.getArmorPiercing());
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
            repaint();
            Thread.sleep(bullet.getSpeed());
            if (processInterception()) {
                bullet.destroyBullet();
            }
        }
    }

    private boolean processInterception() throws Exception {

        String qad = getQadrant(bullet.getX(), bullet.getY());
        int separator = qad.indexOf("_");
        int y = Integer.parseInt(qad.substring(0, separator));
        int x = Integer.parseInt(qad.substring(separator + 1));

        if (y >= 0 && y <= 8 && x >= 0 && x <= 8) {
            if (battleField.scanQuadrant(y, x).getArmor() > 0) {
                babah(battleField.scanQuadrant(y, x), y, x, bullet.getArmorPiercing());
                    repaint();
                    return true;
            }
            return false;
        } else {
            return true;
        }

    }

    public String getQuadrantXY(int v, int h) {
        return (v - 1) * 64 + "_" + (h - 1) * 64;
    }

    public String getQadrant(int x, int y) {
        return y / 64 + "_" + x / 64;
    }

    // construction
    public ActionField() throws Exception {
        battleField = new Field(1);
        bullet = new Bullet(-100, -100, Direction.EMPTY, 1);
        defender = new T34(this, battleField, bullet);
        //agressor = new Tiger(actionField, battleField, 0, 0, Direction.LEFT);
        //agressor = new Tiger(actionField, battleField);
        agressor = new Tiger(this, battleField, bullet);
        //battleField.updateQuadrant(agressor.getX(), agressor.getY(), "T");

        JFrame frame = new JFrame("BATTLE FIELD, DAY 4 - OLEG - ");
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
                    g.setColor(battleField.scanQuadrant(j, k).getColor());
                    g.fillRect(x, y, 64, 64);
                } else if (battleField.scanQuadrant(j, k) instanceof Rock) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int x = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.setColor(battleField.scanQuadrant(j, k).getColor());
                    g.fillRect(x, y, 64, 64);
                } else if (battleField.scanQuadrant(j, k) instanceof Water) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int x = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.setColor(battleField.scanQuadrant(j, k).getColor());
                    g.fillRect(x, y, 64, 64);
                } else if (battleField.scanQuadrant(j, k) instanceof Ampty) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int x = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.setColor(battleField.scanQuadrant(j, k).getColor());
                    g.fillRect(x, y, 64, 64);
                } else if (battleField.scanQuadrant(j, k) instanceof Eagle) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates
                            .substring(0, separator));
                    int x = Integer.parseInt(coordinates
                            .substring(separator + 1));
                    g.setColor(battleField.scanQuadrant(j, k).getColor());
                    g.fillRect(x, y, 64, 64);
                }
            }
        }

        g.setColor(new Color(255, 0, 0));
        g.fillRect(defender.getX(), defender.getY(), 64, 64);
        g.setColor(new Color(155, 0, 0));
        g.fillRect(agressor.getX(), agressor.getY(), 64, 64);


        g.setColor(new Color(0, 255, 0));
        if (defender.getDirection() == Direction.UP) {
            g.fillRect(defender.getX() + 20, defender.getY(), 24, 34);
        } else if (defender.getDirection() == Direction.DOWN) {
            g.fillRect(defender.getX() + 20, defender.getY() + 30, 24, 34);
        } else if (defender.getDirection() == Direction.LEFT) {
            g.fillRect(defender.getX(), defender.getY() + 20, 34, 24);
        } else {
            g.fillRect(defender.getX() + 30, defender.getY() + 20, 34, 24);
        }

        g.setColor(new Color(255, 255, 0));
        g.fillRect(bullet.getX(), bullet.getY(), 14, 14);

        g.setColor(new Color(0, 55, 0));
        if (agressor.getDirection() == Direction.UP) {
            g.fillRect(agressor.getX() + 20, agressor.getY(), 24, 34);
        } else if (agressor.getDirection() == Direction.DOWN) {
            g.fillRect(agressor.getX() + 20, agressor.getY() + 30, 24, 34);
        } else if (agressor.getDirection() == Direction.LEFT) {
            g.fillRect(agressor.getX(), agressor.getY() + 20, 34, 24);
        } else {
            g.fillRect(agressor.getX() + 30, agressor.getY() + 20, 34, 24);
        }
    }

    public void babah(Destroy element, int y, int x, int a) {
       if (element.destroy(a)){
           battleField.updateQuadrant(y, x, new Ampty(y, x));
           //return true;
       }
       //return element;
    }
}