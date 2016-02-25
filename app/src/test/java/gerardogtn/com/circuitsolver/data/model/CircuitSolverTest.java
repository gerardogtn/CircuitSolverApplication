package gerardogtn.com.circuitsolver.data.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by gerardogtn on 2/8/16.
 */
public class CircuitSolverTest {

    HashMap<Character, Wire> wireHashMap = new HashMap<Character, Wire>();
    HashMap<Character, Gate> gateHashMap = new HashMap<Character, Gate>();

    Circuit oneGateCircuit;
    Circuit twoGateCircuit;
    Circuit threeGateLoopCircuit;
    Circuit noSolutionLoopCircuit;

    @Before
    public void setUp() throws Exception {
        createWires();
        createGates();
        setUpCircuits();
    }

    private void createWires() {
        for (char i = 'a'; i < 'w'; i++) {
            wireHashMap.put(i, new Wire(String.valueOf(i)));
        }
    }

    private void createGates() {
        for (char i = 'x'; i <= 'z'; i++) {
            gateHashMap.put(i, new Gate(String.valueOf(i)));
        }
    }

    private void setUpCircuits() {
        setUpOneGateCircuit();
        setUpTwoGateCircuit();
        setUpThreeGateLoopedCircuit();
        setUpNoSolutionLoopCircuitWithSameComponents();
    }

    private void setUpOneGateCircuit() {
        oneGateCircuit = Circuit.getNewInstance();
        Wire wireA = wireHashMap.get('a');
        Wire wireB = wireHashMap.get('b');
        Gate gateX = gateHashMap.get('x');
        oneGateCircuit.addConnection(wireA, gateX);
        oneGateCircuit.addConnection(gateX, wireB);
        oneGateCircuit.setStartWire(wireA);
        oneGateCircuit.setEndWire(wireB);
    }

    @Test
    public void testOneGateCircuit() {
        LinkedList<CircuitSolution> solutions = new LinkedList<CircuitSolution>();
        CircuitSolution solution = new CircuitSolution();
        solution.addWire(wireHashMap.get('a'));
        solution.addWire(wireHashMap.get('b'));
        solutions.add(solution);
        assertEquals(CircuitSolver.solve(oneGateCircuit), solutions);
    }

    private void setUpTwoGateCircuit() {
        twoGateCircuit = Circuit.getNewInstance();
        Wire wireC = wireHashMap.get('c');
        Wire wireD = wireHashMap.get('d');
        Wire wireE = wireHashMap.get('e');
        Gate gateX = gateHashMap.get('x');
        Gate gateY = gateHashMap.get('y');
        twoGateCircuit.addComponents(wireC, wireD, wireE, gateX, gateY);
        twoGateCircuit.addLinearConnection(wireC, gateX, wireD, gateY, wireE);
        twoGateCircuit.setStartWire(wireC);
        twoGateCircuit.setEndWire(wireE);
    }

    @Test
    public void testTwoGateCircuit() {
        CircuitSolution solution = new CircuitSolution();
        solution.addWires(wireHashMap.get('c'), wireHashMap.get('d'), wireHashMap.get('e'));
        List<CircuitSolution> solutions = new ArrayList<CircuitSolution>();
        solutions.add(solution);
        assertEquals(CircuitSolver.solve(twoGateCircuit), solutions);
    }

    private void setUpThreeGateLoopedCircuit() {
        threeGateLoopCircuit = Circuit.getNewInstance();
        Wire wireF = wireHashMap.get('f');
        Wire wireG = wireHashMap.get('g');
        Wire wireH = wireHashMap.get('h');
        Wire wireI = wireHashMap.get('i');
        Wire wireJ = wireHashMap.get('j');
        Gate gateX = gateHashMap.get('x');
        Gate gateY = gateHashMap.get('y');
        Gate gateZ = gateHashMap.get('z');

        threeGateLoopCircuit.addLinearConnection(wireF, wireG, gateX, wireI, gateZ, wireJ);
        threeGateLoopCircuit.addLinearConnection(wireF, wireH, gateY, wireI);
        threeGateLoopCircuit.setStartWire(wireF);
        threeGateLoopCircuit.setEndWire(wireJ);
    }

    @Test
    public void testThreeGateLoopCircuit() {
        List<CircuitSolution> solutions = new ArrayList<CircuitSolution>();
        solutions.add(new CircuitSolution(wireHashMap.get('f'), wireHashMap.get('g'), wireHashMap.get('i'), wireHashMap.get('j')));
        solutions.add(new CircuitSolution(wireHashMap.get('f'), wireHashMap.get('h'), wireHashMap.get('i'), wireHashMap.get('j')));
        assertEquals(CircuitSolver.solve(threeGateLoopCircuit), solutions);
    }

    private void setUpNoSolutionLoopCircuitWithSameComponents() {
        noSolutionLoopCircuit = Circuit.getNewInstance();
        noSolutionLoopCircuit.addComponents(wireHashMap.get('k'), wireHashMap.get('l'), wireHashMap.get('m'),
                wireHashMap.get('n'), wireHashMap.get('o'), wireHashMap.get('v'), gateHashMap.get('x'), gateHashMap.get('y'));
        noSolutionLoopCircuit.addLinearConnection(wireHashMap.get('k'), wireHashMap.get('l'), gateHashMap.get('x'),
                wireHashMap.get('n'), wireHashMap.get('v'));
        noSolutionLoopCircuit.addLinearConnection(wireHashMap.get('k'), wireHashMap.get('m'), gateHashMap.get('y'),
                wireHashMap.get('o'), wireHashMap.get('v'));
        noSolutionLoopCircuit.setStartWire(wireHashMap.get('k'));
        noSolutionLoopCircuit.setEndWire(wireHashMap.get('r'));
    }

    @Test
    public void testNoSolutionLoopCircuit() {
        List<CircuitSolution> solutions = new ArrayList<CircuitSolution>();
        assertEquals(solutions, CircuitSolver.solve(noSolutionLoopCircuit));

    }
}