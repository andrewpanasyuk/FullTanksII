/**
 * Created by panasyuk on 16.06.2015.
 */
public class Generation {
    public static int gen(int a, int b) {
        int s = a + (int) (Math.random() * ((b - a) + 1));
        return s;
    }
}
