package ObjectBF;

import java.lang.reflect.Field;

/**
 * Created by panasyuk on 27.06.2015.
 */
public class ConstructField {
    public Construct[][]cf;

public ConstructField(String[][] battleField) {
    this.cf = makeArray(battleField);
}

    public Construct[][] makeArray(String[][]battleField){

        Construct[][] constructField = new Construct[9][9];

        for (int i = 0; i < 9; i++) {
            for (int n = 0; n <9; n++) {
                if (battleField[i][n].equals("B")) {
                    constructField[i][n] = new Brick(i * 64, n * 64);
                } else if (battleField[i][n].equals("R")) {
                    constructField[i][n] = new Rock(i * 64, n * 64);
                } else if (battleField[i][n].equals("W")) {
                    constructField[i][n] = new Water(i * 64, n * 64);
                } else {
                    constructField[i][n] = new Ampty(i * 64, n * 64);
                }
            }
        }
        return constructField;
    }
//    System.out.println("22222    " + constructField[0][0].getArmor());


    public Construct[][] getCf() {
        return cf;
    }

    public void setCf(Construct[][] cf) {
        this.cf = cf;
    }
}
