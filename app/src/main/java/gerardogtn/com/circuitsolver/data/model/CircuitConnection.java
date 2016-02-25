package gerardogtn.com.circuitsolver.data.model;

/**
 * Created by gerardogtn on 2/15/16.
 */
public class CircuitConnection {

    private CircuitComponent entryComponent;
    private CircuitComponent exitComponent;

    public CircuitConnection(CircuitComponent entryComponent, CircuitComponent exitComponent) {
        this.entryComponent = entryComponent;
        this.exitComponent = exitComponent;
    }

    public CircuitComponent getEntryComponent() {
        return entryComponent;
    }

    public CircuitComponent getExitComponent() {
        return exitComponent;
    }
}
