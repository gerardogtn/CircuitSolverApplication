package gerardogtn.com.circuitsolver.data.model;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import gerardogtn.com.circuitsolver.util.constant.CircuitViewSizeConstant;
import gerardogtn.com.circuitsolver.util.exception.CannotConnectTwoGatesException;
import gerardogtn.com.circuitsolver.util.exception.CircuitComponentNotFoundException;

/**
 * Created by gerardogtn on 2/8/16.
 */
public class Circuit {

    private LinkedList<CircuitComponent> components;
    private List<CircuitConnection> connections;

    private Wire startWire;
    private Wire endWire;

    private static Circuit mInstance;

    private Circuit() {
        components = new LinkedList<>();
        connections = new ArrayList<>();
    }

    public static Circuit getInstance() {
        if (mInstance == null) {
            mInstance = new Circuit();
        }
        return mInstance;
    }

    public static Circuit getNewInstance() {
        mInstance = new Circuit();
        return mInstance;
    }


    public Wire getEndWire() {
        return endWire;
    }

    public void setEndWire(Wire endWire) {
        this.endWire = endWire;
    }

    public Wire getStartWire() {
        return startWire;
    }

    public void setStartWire(Wire startWire) {
        this.startWire = startWire;
    }

    public List<CircuitConnection> getConnections() {
        return connections;
    }

    public List<CircuitComponent> getComponents() {
        return components;
    }

    public void addComponent(CircuitComponent component) {
        components.add(component);
    }

    public void addComponents(CircuitComponent... components) {
        for (CircuitComponent component : components) {
            addComponent(component);
        }
    }

    public void addConnection(CircuitComponent entryComponent, CircuitComponent exitComponent) {
        if (entryComponent instanceof Gate && exitComponent instanceof Gate) {
            throw new CannotConnectTwoGatesException();
        } else {
            addConnection(new CircuitConnection(entryComponent, exitComponent));
        }
    }

    public void addConnection(CircuitConnection connection) {
        connections.add(connection);
    }

    public void addLinearConnection(CircuitComponent... components) {
        for (int i = 0; i < components.length - 1; i++) {
            addConnection(components[i], components[i + 1]);
        }
    }

    public LinkedList<CircuitComponent> getNextComponent(CircuitComponent component) {
        LinkedList<CircuitComponent> output = new LinkedList<>();
        for (CircuitConnection connection : connections) {
            if (connection.getEntryComponent().equals(component)) {
                output.add(connection.getExitComponent());
            }
        }
        return output;
    }

    public CircuitComponent getComponentAtPosition(PointF currentTouchPosition) {
        CircuitComponent currentComponent;
        PointF currentComponentPosition;
        Iterator<CircuitComponent> descendingComponentsIterator = components.descendingIterator();
        while (descendingComponentsIterator.hasNext()) {
            currentComponent = descendingComponentsIterator.next();
            currentComponentPosition = new PointF(currentComponent.getX(), currentComponent.getY());

            if (isPositionInCircleWithCenterAt(currentComponentPosition, currentTouchPosition)) {
                return currentComponent;
            }
        }

        throw new CircuitComponentNotFoundException();
    }

    private boolean isPositionInCircleWithCenterAt(PointF componentPosition,
                                                   PointF currentTouchPosition) {
        return Math.pow(componentPosition.x - currentTouchPosition.x, 2)
                + Math.pow(componentPosition.y - currentTouchPosition.y, 2)
                < Math.pow(CircuitViewSizeConstant.sCircuitComponentRadius, 2);
    }
}
