/**
 * Created by panasyuk on 16.06.2015.
 */
public class Field {
    private String[][] batlefield;
    public int bfWidth = 576;
    public int bfHeight = 576;

    Field() {
        this.batlefield = makeField();
    }

    private String[][] makeField() {
        String[][] bf = new String[9][9];
        for (String[] a : bf) {
            for (int i = 0; i <= 8; i++) {
                int s = Generation.gen(0, 2);
                if (s == 0 || s == 1) {
                    a[i] = " ";
                } else {
                    a[i] = "B";
                }
            }
        }
        return bf;
    }

    public String[][] getBatlefield() {
        return batlefield;
    }

    public void setBatlefield(String[][] batlefield) {
        this.batlefield = batlefield;
    }
    public String scanQuadrant(int x, int y) {
        return batlefield[x][y];
    }
    public void updateQuadrant(int x, int y, String newParametr) {
        this.batlefield[x][y] = newParametr;
    }
    public int getDimentionY(){
        int dimentionY = bfHeight/64;
        return dimentionY;

    }
    public int getDimentionX(){
        int dimentionX = bfWidth/64;
        return dimentionX;

    }

    public int getBfWidth() {
        return bfWidth;
    }

    public int getBfHeight() {
        return bfHeight;
    }

}
