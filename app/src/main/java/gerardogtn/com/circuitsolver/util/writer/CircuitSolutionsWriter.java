package gerardogtn.com.circuitsolver.util.writer;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import gerardogtn.com.circuitsolver.data.model.Circuit;
import gerardogtn.com.circuitsolver.data.model.CircuitComponent;
import gerardogtn.com.circuitsolver.data.model.CircuitSolution;
import gerardogtn.com.circuitsolver.data.model.Wire;

/**
 * Created by gerardogtn on 2/18/16.
 */
public class CircuitSolutionsWriter {

    private String mFilePath;
    private BufferedWriter mWriter;
    private LinkedList<Wire> mCircuitWires;
    private LinkedList<CircuitSolution> mCircuitSolutions;

    public CircuitSolutionsWriter(String fileName, LinkedList<CircuitSolution> solutions) {
        mFilePath = Environment.getExternalStorageDirectory() + File.separator + fileName;
        mCircuitWires = new LinkedList<>();
        mCircuitSolutions = solutions;
    }

    public boolean writeSolutionToFile() {
        File file = new File(mFilePath);

        try {
            mWriter = new BufferedWriter(new FileWriter(file));
            writeHeader();
            writeSolutions();
            mWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    private void writeHeader() throws IOException {
        for (CircuitComponent component : Circuit.getInstance().getComponents()) {
            if (component instanceof Wire) {
                mCircuitWires.push((Wire) component);
                mWriter.write(component.getLabel() + " ");
                mWriter.newLine();
            }
        }
    }

    private void writeSolutions() throws IOException {
        List<Wire> solutionWires;
        for (CircuitSolution solution : mCircuitSolutions) {
            solutionWires = solution.getWires();
            for (Wire wire : mCircuitWires) {
                if (solutionWires.contains(wire)) {
                    mWriter.write("1 ");
                } else {
                    mWriter.write("0 ");
                }
            }
            mWriter.newLine();
        }
    }
}
