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
            for (Wire currentWire : sWires) {
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
            sBuffer.append("\n");
        }
    }

    private static void appendHeaderToBuffer() {
        for (Wire w : sWires) {
            sBuffer.append(w.toString());
            sBuffer.append(", ");
        }
        sBuffer.append("\n");
    }

}
