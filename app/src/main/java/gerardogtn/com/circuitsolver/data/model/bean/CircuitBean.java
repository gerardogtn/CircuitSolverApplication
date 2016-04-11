package gerardogtn.com.circuitsolver.data.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.LinkedList;

import gerardogtn.com.circuitsolver.data.model.CircuitComponent;
import gerardogtn.com.circuitsolver.data.model.CircuitConnection;
import gerardogtn.com.circuitsolver.data.model.Wire;

/**
 * Created by gerardogtn on 3/12/16.
 */
public class CircuitBean {

    @SerializedName("components")
    private LinkedList<CircuitComponent> components;

    @SerializedName("connections")
    private ArrayList<CircuitConnection> connections;

    @SerializedName("start_wire")
    private Wire startWire;

    @SerializedName("end_wire")
    private Wire endWire;

    public CircuitBean() {
        components = new LinkedList<>();
        connections = new ArrayList<>();
    }

    public LinkedList<CircuitComponent> getComponents() {
        return components;
    }

    public void setComponents(LinkedList<CircuitComponent> components) {
        this.components = components;
    }

    public ArrayList<CircuitConnection> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<CircuitConnection> connections) {
        this.connections = connections;
    }

    public Wire getStartWire() {
        return startWire;
    }

    public void setStartWire(Wire startWire) {
        this.startWire = startWire;
    }

    public Wire getEndWire() {
        return endWire;
    }

    public void setEndWire(Wire endWire) {
        this.endWire = endWire;
    }
}
