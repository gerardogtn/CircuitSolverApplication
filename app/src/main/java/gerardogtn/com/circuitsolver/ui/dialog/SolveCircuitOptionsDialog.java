package gerardogtn.com.circuitsolver.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import gerardogtn.com.circuitsolver.R;
import gerardogtn.com.circuitsolver.data.model.Circuit;
import gerardogtn.com.circuitsolver.util.exception.CircuitComponentNotFoundException;

/**
 * Created by gerardogtn on 2/18/16.
 */
public class SolveCircuitOptionsDialog extends DialogFragment {

    public static final String TAG = "SolveCircuitDialog";

    private View.OnClickListener mOnSolveCircuitClickListener;
    private DialogInterface.OnShowListener mOnShowListener;

    @Bind(R.id.txt_start_wire)
    EditText mStartWireEditText;

    @Bind(R.id.txt_end_wire)
    EditText mEndWireEditText;

    @Bind(R.id.txt_solution_file_name)
    EditText mFileNameEditText;

    private static OnCircuitSolveListener sCircuitSolveListener;

    public static SolveCircuitOptionsDialog newInstance(OnCircuitSolveListener onCircuitSolveListener) {
        sCircuitSolveListener = onCircuitSolveListener;
        return new SolveCircuitOptionsDialog();
    }

    public static void setOnCircuitSolveListener(OnCircuitSolveListener listener) {
        sCircuitSolveListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_solve_circuit_options, null);
        ButterKnife.bind(this, v);
        setUpOnSolveCircuitClickListener();

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(v)
                .setTitle(context.getString(R.string.add_circuit_component))
                .setPositiveButton(android.R.string.ok, null)
                .setNeutralButton(android.R.string.cancel, null)
                .create();
        setUpOnShowListener(dialog);
        dialog.setOnShowListener(mOnShowListener);

        return dialog;
    }

    private void setUpOnSolveCircuitClickListener() {
        mOnSolveCircuitClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (areEntriesValid()) {
                    solveCircuit();
                }
            }
        };
    }

    private boolean areEntriesValid() {
        boolean output = true;
        if (!isFileNameValid()) {
            mFileNameEditText.setError(getActivity().getString(R.string.file_name_between_3_and_23));
            output = false;
        } else if (!isLabelWire(mStartWireEditText.getText().toString())){
            mStartWireEditText.setError(getActivity().getString(R.string.label_is_not_wire));
            output = false;
        } else if (!isLabelWire(mEndWireEditText.getText().toString())){
            mEndWireEditText.setError(getActivity().getString(R.string.label_is_not_wire));
            output = false;
        }
        return output;
    }

    private boolean isFileNameValid() {
        int nameLength = mFileNameEditText.getText().toString().length();
        return nameLength > 2 && nameLength < 23;
    }

    // TODO: Change set wire for method that just checks if is wire.
    private boolean isLabelWire(String wireLabel) {
        try {
            Circuit.getInstance().setStartWire(wireLabel);
            return true;
        } catch (CircuitComponentNotFoundException e) {
            return false;
        }
    }

    private void setUpOnShowListener(final AlertDialog alertDialog) {
        mOnShowListener = new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setOnClickListener(mOnSolveCircuitClickListener);
            }
        };
    }

    private void solveCircuit() {
        if (sCircuitSolveListener != null) {
            Circuit.getInstance().setStartWire(mStartWireEditText.getText().toString());
            Circuit.getInstance().setEndWire(mEndWireEditText.getText().toString());

            sCircuitSolveListener.
                    writeCircuitSolutionsToFileWithName(mFileNameEditText.getText().toString());

        } else {
            // TODO: Use event bus to comunicate error sending and process error code in MainActivity.
        }
        dismiss();
    }

    public interface OnCircuitSolveListener {
        void writeCircuitSolutionsToFileWithName(String name);
    }
}
