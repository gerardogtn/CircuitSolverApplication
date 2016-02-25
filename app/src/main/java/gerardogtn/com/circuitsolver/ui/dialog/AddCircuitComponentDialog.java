package gerardogtn.com.circuitsolver.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import gerardogtn.com.circuitsolver.R;
import gerardogtn.com.circuitsolver.ui.activity.MainActivity;

/**
 * Created by gerardogtn on 2/17/16.
 */
public class AddCircuitComponentDialog extends DialogFragment {

    public static final String TAG = "CircuitComponentDialog";

    private View.OnClickListener mOnAddCircuitComponentListener;
    private DialogInterface.OnShowListener mOnShowListener;
    private EditText mLabelEditText;
    private AppCompatSpinner mSpinner;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_add_circuit_component, null);
        setUpOnAddCircuitComponentListener();
        mLabelEditText = (EditText) v.findViewById(R.id.txt_label);
        mSpinner = (AppCompatSpinner) v.findViewById(R.id.spnr_component_type);

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

    private void setUpOnAddCircuitComponentListener() {
        mOnAddCircuitComponentListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLabelEntryValid()) {
                    addCircuitComponent();
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
                        .setOnClickListener(mOnAddCircuitComponentListener);
            }
        };
    }

    private boolean isLabelEntryValid() {
        String label = mLabelEditText.getText().toString();
        return label.length() == 1;
    }

    private void addCircuitComponent() {
        MainActivity activity = (MainActivity) getActivity();
        String label = mLabelEditText.getText().toString();
        String selectedSpinnerItem = mSpinner.getSelectedItem().toString();
        if (selectedSpinnerItem.equals(activity.getString(R.string.gate))) {
            activity.addCircuitGate(label);
        } else if (selectedSpinnerItem.equals(activity.getString(R.string.wire))) {
            activity.addCircuitWire(label);
        }
        dismiss();
    }

    private void indicateEntryIsInvalid() {
        mLabelEditText.setError(getActivity().getString(R.string.one_digit_entry));
    }
}
