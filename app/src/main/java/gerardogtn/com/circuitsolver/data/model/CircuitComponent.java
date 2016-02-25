package gerardogtn.com.circuitsolver.data.model;

/**
 * Created by gerardogtn on 2/15/16.
 */
public abstract class CircuitComponent {

    private static final float DEFAULT_X_POSITION = 100;
    private static final float DEFAULT_Y_POSITION = 100;

    private String label;
    private float x;
    private float y;

    public CircuitComponent(String label) {
        this.label = label;
        this.x = DEFAULT_X_POSITION;
        this.y = DEFAULT_Y_POSITION;
    }

    public String getLabel() {
        return label;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CircuitComponent that = (CircuitComponent) o;

        return label.equals(that.label);

    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }
}
