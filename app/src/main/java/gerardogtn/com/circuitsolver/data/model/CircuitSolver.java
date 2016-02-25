package gerardogtn.com.circuitsolver.data.model;

import java.util.LinkedList;

/**
 * Created by gerardogtn on 2/15/16.
 */
public class CircuitSolver {

    private static Circuit circuit;
    private static Wire endWire;
    private static LinkedList<CircuitSolution> solutions;

    public static LinkedList<CircuitSolution> solve(Circuit circuit) {
        initializeVariables(circuit);
        LinkedList<CircuitComponent> currentComponents = new LinkedList<CircuitComponent>();
        currentComponents.add(circuit.getStartWire());
        backtrackSolver(currentComponents, circuit.getNextComponent(circuit.getStartWire()));
        return solutions;
    }

    private static void initializeVariables(Circuit circuit) {
        solutions = new LinkedList<CircuitSolution>();
        CircuitSolver.circuit = circuit;
        endWire = circuit.getEndWire();
    }

    private static void backtrackSolver(LinkedList<CircuitComponent> currentComponents, LinkedList<CircuitComponent> nextComponents) {
        for (CircuitComponent component : nextComponents) {
            currentComponents.push(component);
            if (component.equals(endWire)) {
                addSolution(currentComponents);
            } else {
                backtrackSolver(currentComponents, circuit.getNextComponent(component));
            }
            currentComponents.pop();
        }
    }

    private static void addSolution(LinkedList<CircuitComponent> components) {
        LinkedList<Wire> wires = new LinkedList<Wire>();
        for (CircuitComponent component : components) {
            if (component instanceof Wire) {
                wires.add((Wire) component);
            }
        }
        solutions.add(new CircuitSolution(wires));
    }

}
