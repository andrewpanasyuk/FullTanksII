/**
 * Created by panasyuk on 16.06.2015.
 */
public enum Direction {
    TOP(1), BOTTOM(2), LEFT(3), RIGHT(4), EMPTY(5);
    private int id;

    private Direction(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
