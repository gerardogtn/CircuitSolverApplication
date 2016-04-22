package gerardogtn.com.circuitsolver.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gerardogtn.com.circuitsolver.R;
import gerardogtn.com.circuitsolver.data.database.CircuitSqlRepository;
import gerardogtn.com.circuitsolver.data.model.Circuit;
import gerardogtn.com.circuitsolver.data.model.CircuitSolverPrinter;
import gerardogtn.com.circuitsolver.data.model.Gate;
import gerardogtn.com.circuitsolver.data.model.Wire;
import gerardogtn.com.circuitsolver.ui.dialog.AddCircuitComponentDialog;
import gerardogtn.com.circuitsolver.ui.dialog.SolveCircuitOptionsDialog;
import gerardogtn.com.circuitsolver.ui.view.CircuitView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Bind(R.id.circuit_view)
    CircuitView mCircuitView;

    private MyCircuitSolveListener mCircuitSolveListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCircuitSolveListener = new MyCircuitSolveListener();
        CircuitSqlRepository.getInstance().restoreCircuitFromDatabase();
    }

    public void addCircuitGate(String label) {
        Circuit.getInstance().addComponent(new Gate(label));
        mCircuitView.onAddCircuitComponent();
    }

    public void addCircuitWire(String label) {
        Circuit.getInstance().addComponent(new Wire(label));
        mCircuitView.onAddCircuitComponent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_solve:
                onSolveOptionSelected();
                break;
            case R.id.action_import:
                onImportOptionSelected();
                break;
            case R.id.action_export:
                onExportOptionSelected();
                break;
            case R.id.action_clear:
                onClearOptionSelected();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSolveOptionSelected() {
        SolveCircuitOptionsDialog dialog = new SolveCircuitOptionsDialog();
        dialog.show(getSupportFragmentManager(), SolveCircuitOptionsDialog.TAG);
        SolveCircuitOptionsDialog.setOnCircuitSolveListener(mCircuitSolveListener);
    }

    private void onImportOptionSelected() {

    }

    private void onExportOptionSelected() {
        ExportCircuitTask task = new ExportCircuitTask("circuit");
        task.execute();
    }

    private void onClearOptionSelected() {
        Circuit.getNewInstance();
        mCircuitView.invalidate();
        CircuitSqlRepository.reset();
    }

    @OnClick(R.id.fab_add_circuit_component)
    public void onAddCircuitComponent() {
        AddCircuitComponentDialog dialog = new AddCircuitComponentDialog();
        dialog.show(getSupportFragmentManager(), AddCircuitComponentDialog.TAG);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        CircuitSqlRepository.getInstance().updateDatabaseFromCircuit();
    }

    private class ExportCircuitTask extends AsyncTask<Void, Void, Void> {

        private String mFilePath;

        public ExportCircuitTask(String fileName) {
            this.mFilePath = Environment.getExternalStorageDirectory() + File.separator + fileName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String circuitJson = (new Gson()).toJson(Circuit.getInstance());

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(mFilePath));
                writer.write(circuitJson);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/json");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(mFilePath)));
            startActivity(Intent.createChooser(shareIntent, getString(R.string.action_write_solutions)));
            super.onPostExecute(aVoid);
        }

    }

    // TODO: ADD LOADING INDICATOR.
    private class ExportCircuitSolutionTask extends AsyncTask<Void, Void, Void> {

        private String mFilePath;

        public ExportCircuitSolutionTask(String fileName) {
            mFilePath = Environment.getExternalStorageDirectory() + File.separator + fileName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                FileWriter fileWriter = new FileWriter(mFilePath);
                BufferedWriter writer = new BufferedWriter(fileWriter);
                writer.write(CircuitSolverPrinter.getSolution());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/csv");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(mFilePath)));
            startActivity(Intent.createChooser(shareIntent, getString(R.string.action_write_solutions)));
            super.onPostExecute(aVoid);
        }
    }

    private class MyCircuitSolveListener implements SolveCircuitOptionsDialog.OnCircuitSolveListener {

        @Override
        public void writeCircuitSolutionsToFileWithName(String name) {
            ExportCircuitSolutionTask exportCircuitSolutionTask = new ExportCircuitSolutionTask(name);
            exportCircuitSolutionTask.execute();
        }
    }

}
