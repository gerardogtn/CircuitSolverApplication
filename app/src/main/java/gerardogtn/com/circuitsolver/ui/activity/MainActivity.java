package gerardogtn.com.circuitsolver.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gerardogtn.com.circuitsolver.R;
import gerardogtn.com.circuitsolver.data.model.Circuit;
import gerardogtn.com.circuitsolver.data.model.Gate;
import gerardogtn.com.circuitsolver.data.model.Wire;
import gerardogtn.com.circuitsolver.ui.dialog.AddCircuitComponentDialog;
import gerardogtn.com.circuitsolver.ui.view.CircuitView;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.circuit_view)
    CircuitView mCircuitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.fab_add_circuit_component)
    public void onAddCircuitComponent() {
        AddCircuitComponentDialog dialog = new AddCircuitComponentDialog();
        dialog.show(getSupportFragmentManager(), AddCircuitComponentDialog.TAG);
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

    }

    private void onImportOptionSelected() {

    }

    private void onExportOptionSelected() {

    }

    private void onClearOptionSelected() {

    }


}
