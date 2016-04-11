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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CircuitConnection that = (CircuitConnection) o;

        if (!entryComponent.equals(that.entryComponent)) return false;
        return exitComponent.equals(that.exitComponent);

    }

    @Override
    public int hashCode() {
        int result = entryComponent.hashCode();
        result = 31 * result + exitComponent.hashCode();
        return result;
    }
}
