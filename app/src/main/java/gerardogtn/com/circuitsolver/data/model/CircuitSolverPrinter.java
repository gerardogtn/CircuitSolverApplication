package gerardogtn.com.circuitsolver.data.model;

import java.util.LinkedList;

/**
 * Created by gerardogtn on 3/11/16.
 */
public class CircuitSolverPrinter {

    private static LinkedList<Wire> sWires;
    private static StringBuffer sBuffer;

    public static String getSolution() {
        sBuffer = new StringBuffer();
        sWires = Circuit.getInstance().getWires();
        appendHeaderToBuffer();
        appendCsvToBuffer();
        return sBuffer.toString();
    }

    private static void appendCsvToBuffer() {
        for (CircuitSolution solution : CircuitSolver.solve(Circuit.getInstance())) {
            for (int i = 0; i < sWires.size() - 1; i++) {
                Wire currentWire = sWires.get(i);
                boolean found = false;
                for (Wire currentSolutionWire : solution.getWires()) {
                    if (currentWire.equals(currentSolutionWire)) {
                        sBuffer.append("1, ");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    sBuffer.append("0, ");
                }
            }

            boolean found = false;
            Wire currentWire = sWires.getLast();
            for (Wire currentSolutionWire : solution.getWires()) {
                if (currentWire.equals(currentSolutionWire)) {
                    sBuffer.append("1");
                    found = true;
                    break;
                }
            }
            if (!found) {
                sBuffer.append("0");
            }
            sBuffer.append("\n");
        }
    }

    private static void appendHeaderToBuffer() {
        for (int i = 0; i < sWires.size() - 1; i++) {
            Wire w = sWires.get(i);
            sBuffer.append(w.toString());
            sBuffer.append(", ");
        }
        sBuffer.append(sWires.getLast().toString());
        sBuffer.append("\n");
    }

}
