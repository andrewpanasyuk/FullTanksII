package filds;

import objectBF.*;

import java.util.Random;

/**
 * Created by panasyuk on 16.06.2015.
 */
public class Batlefild {
    private Construct[][] batlefield;
    public int bfWidth = 576;
    public int bfHeight = 576;

    public Batlefild() {
        this.batlefield = makeField();
    }

    public Batlefild(int i) {
        if (i == 1) {
            this.batlefield = cff();
        } else {
            this.batlefield = makeField();
        }
    }

    private Construct[][] makeField() {
        Construct[][] bf = new Construct[9][9];
        for (int i = 0; i < 9; i++) {
            for (int n = 0; n < 9; n++) {
                int s = new Random().nextInt(9);
                if (s == 0 || s == 1) {
                    bf[i][n] = new Water(n, i);
                } else if (s == 2) {
                    bf[i][n] = new Brick(n, i);
                } else if (s == 3) {
                    bf[i][n] = new Rock(n, i);
                } else {
                    bf[i][n] = new Empty(n, i);
                }
            }
        }
        return bf;
    }

    public Construct[][] cff() {
        String[][] bf = new String[][]{
                {" ", " ", " ", " ", "B", " ", " ", " ", " "},
                {" ", " ", " ", " ", "B", " ", " ", " ", " "},
                {"W", " ", " ", " ", "B", "B", "B", "B", "R"},
                {"R", " ", " ", " ", " ", " ", " ", " ", "R"},
                {"R", " ", " ", " ", " ", " ", " ", " ", "R"},
                {"B", "B", "B", "B", " ", " ", " ", " ", "R"},
                {" ", " ", "W", "B", "W", " ", "W", " ", "R"},
                {" ", " ", " ", "B", "B", "B", "W", " ", " "},
                {" ", "R", " ", "R", "E", " ", "W", " ", " "}
        };


        Construct[][] constractField = new Construct[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (bf[y][x] == "B") {
                    constractField[y][x] = new Brick(x, y);
                } else if (bf[y][x] == "R") {
                    constractField[y][x] = new Rock(x, y);
                } else if (bf[y][x] == "W") {
                    constractField[y][x] = new Water(x, y);
                } else if (bf[y][x] == "E") {
                    constractField[y][x] = new Eagle(x, y);
                } else {
                    constractField[y][x] = new Empty(x, y);
                }
            }
        }
        return constractField;
    }

public boolean searchEagle(){
    Construct eagle = null;
    int x = 0;
    int y = 0;
    for (int xx = 0; xx < 9; xx++) {
        for (int b = 0; b < 9; b++) {
            if (scanQuadrant(xx, b) instanceof Eagle) {
               return true;
            }

        }
    }
    return false;
}
    public Construct[][] getBatlefield() {
        return batlefield;
    }

    public void setBatlefield(Construct[][] batlefield) {
        this.batlefield = batlefield;
    }

    public Construct scanQuadrant(int x, int y) {
        if (x<9 && y < 9) {
            return batlefield[y][x];
        }
        return null;
    }

    public void updateQuadrant(int x, int y, Construct newParametr) {
        this.batlefield[x][y] = newParametr;
    }

    public int getDimentionY() {
        int dimentionY = bfHeight / 64;
        return dimentionY;

    }

    public int getDimentionX() {
        int dimentionX = bfWidth / 64;
        return dimentionX;

    }


    public int getBfWidth() {
        return bfWidth;
    }

    public int getBfHeight() {
        return bfHeight;
    }

}
