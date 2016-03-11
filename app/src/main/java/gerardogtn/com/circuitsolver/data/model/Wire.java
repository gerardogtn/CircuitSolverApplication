package gerardogtn.com.circuitsolver.data.model;

/**
 * Created by gerardogtn on 2/8/16.
 */
public class Wire extends CircuitComponent{

    public Wire(String label) {
        super(label);
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
