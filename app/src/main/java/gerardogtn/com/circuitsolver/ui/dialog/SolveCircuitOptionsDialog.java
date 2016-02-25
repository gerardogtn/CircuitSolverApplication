package gerardogtn.com.circuitsolver.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import gerardogtn.com.circuitsolver.R;

/**
 * Created by gerardogtn on 2/18/16.
 */
public class SolveCircuitOptionsDialog extends DialogFragment {

    private View.OnClickListener mOnSolveCircuitClickListener;
    private DialogInterface.OnShowListener mOnShowListener;
    private EditText mFileNameEditText;

    private static OnCircuitSolveListener sCircuitSolveListener;

    public static SolveCircuitOptionsDialog newInstance(OnCircuitSolveListener onCircuitSolveListener) {
        sCircuitSolveListener = onCircuitSolveListener;
        return new SolveCircuitOptionsDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_solve_circuit_options, null);
        setUpOnSolveCircuitClickListener();
        mFileNameEditText = (EditText) v.findViewById(R.id.txt_file_name);

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
                if (isLabelEntryValid()) {
                    solveCircuit();
                } else {
                    indicateEntryIsInvalid();
                }
            }
        };
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

    private boolean isLabelEntryValid() {
        String label = mFileNameEditText.getText().toString();
        return label.length() > 3 && label.length() < 23;
    }

    private void solveCircuit() {
        dismiss();
        sCircuitSolveListener.writeCircuitSolutionsToFileWithName(mFileNameEditText.getText().toString());
    }


    private void indicateEntryIsInvalid() {
        mFileNameEditText.setError(getActivity().getString(R.string.file_name_between_3_and_23));
    }

    public interface OnCircuitSolveListener {
        void writeCircuitSolutionsToFileWithName(String name);
    }
}
