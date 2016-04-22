package gerardogtn.com.circuitsolver.data.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import gerardogtn.com.circuitsolver.data.model.CircuitComponent;
import gerardogtn.com.circuitsolver.data.model.CircuitConnection;
import gerardogtn.com.circuitsolver.data.model.Gate;
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

    public void addComponent(CircuitComponent component) {
        components.push(component);
    }

    public void setComponents(LinkedList<CircuitComponent> components) {
        this.components = components;
    }

    public void setComponents(CircuitComponent... components) {
        LinkedList<CircuitComponent> componentsList = new LinkedList<>();
        for (CircuitComponent component : components) {
            componentsList.add(component);
        }
        setComponents(componentsList);
    }

    public void addWires(Wire... wires) {
        Collections.addAll(components, wires);
    }

    public void addGates(Gate... gates) {
        Collections.addAll(components, gates);
    }

    public ArrayList<CircuitConnection> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<CircuitConnection> connections) {
        this.connections = connections;
    }

    public void setConnections(CircuitConnection... connections) {
        ArrayList<CircuitConnection> connectionList = new ArrayList<>();
        Collections.addAll(connectionList, connections);
        setConnections(connectionList);
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

    public Wire getWire(String label) {
        for (CircuitComponent component : components) {
            if (component instanceof Wire && component.getLabel().equals(label)) {
                return (Wire) component;
            }
        }
        throw new IllegalArgumentException("Label " + label + " could not be found");
    }

    public CircuitComponent getComponent(String label) {
        for (CircuitComponent component : components) {
            if (component.getLabel().equals(label)) {
                return component;
            }
        }
        throw new IllegalArgumentException("Component: " + label + " could not be found");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CircuitBean that = (CircuitBean) o;

        if (!components.equals(that.components)) return false;
        if (!connections.equals(that.connections)) return false;
        if (startWire != null ? !startWire.equals(that.startWire) : that.startWire != null)
            return false;
        return endWire != null ? endWire.equals(that.endWire) : that.endWire == null;

    }

    @Override
    public int hashCode() {
        int result = components.hashCode();
        result = 31 * result + connections.hashCode();
        result = 31 * result + (startWire != null ? startWire.hashCode() : 0);
        result = 31 * result + (endWire != null ? endWire.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CircuitBean{" +
                "components=" + components +
                ", connections=" + connections +
                ", startWire=" + startWire +
                ", endWire=" + endWire +
                '}';
    }
}
