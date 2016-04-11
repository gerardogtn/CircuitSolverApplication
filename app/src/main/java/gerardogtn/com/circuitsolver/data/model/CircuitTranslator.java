package gerardogtn.com.circuitsolver.data.model;

import java.util.ArrayList;
import java.util.LinkedList;

import gerardogtn.com.circuitsolver.data.model.bean.CircuitBean;

/**
 * Created by gerardogtn on 4/10/16.
 */
public class CircuitTranslator {

    public static CircuitBean getCircuitBean(Circuit circuit) {
        CircuitBean output = new CircuitBean();

        output.setStartWire(circuit.getStartWire());
        output.setEndWire(circuit.getEndWire());
        output.setComponents((LinkedList<CircuitComponent>) circuit.getComponents());
        output.setConnections((ArrayList<CircuitConnection>) circuit.getConnections());

        return output;
    }

    public static void updateCircuitSingleton(CircuitBean circuitBean) {
        Circuit circuit = Circuit.getNewInstance();

        circuit.setStartWire(circuitBean.getStartWire());
        circuit.setEndWire(circuitBean.getEndWire());
        circuit.setComponents(circuitBean.getComponents());
        circuit.setConnections(circuitBean.getConnections());
    }
}
