package gerardogtn.com.circuitsolver.data.model;


import java.util.LinkedList;


/**
 * Created by gerardogtn on 2/8/16.
 */
public class CircuitSolution {

    LinkedList<Wire> wires;

    public CircuitSolution() {
        wires = new LinkedList<Wire>();
    }

    public CircuitSolution(LinkedList<Wire> wires) {
        this.wires = wires;
    }

    public CircuitSolution(Wire... wires) {
        this.wires = new LinkedList<Wire>();
        addWires(wires);
    }

    public void addWire(Wire wire) {
        wires.push(wire);
    }

    public void addWires(Wire... wires) {
        for (Wire wire : wires) {
            this.wires.push(wire);
        }
    }

    public LinkedList<Wire> getWires() {
        return wires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CircuitSolution that = (CircuitSolution) o;

        return wires.equals(that.wires);
    }

    @Override
    public int hashCode() {
        return wires.hashCode();
    }
}

