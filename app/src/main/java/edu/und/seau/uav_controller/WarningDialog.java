package edu.und.seau.uav_controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class WarningDialog extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implements this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it*/
    public interface WarningDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // This instance of the interface delivers action events
    WarningDialogListener mListener;

    // This instantiates the WarningDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify host activity implements the callback interface
        try {
            // Instantiate WarningDialogListener so events can be sent to host
            mListener = (WarningDialogListener) context;
        } catch (ClassCastException e) {
            // Activity does not implement interface, so throw exception
            throw new ClassCastException(getActivity().toString() + " must implement WarningDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Builder conveniently constructs dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_no_location_permissions)
                .setTitle(R.string.title_no_location_permissions)
                .setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Sends positive button event back to host activity
                        mListener.onDialogPositiveClick(WarningDialog.this);
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Sends negative button event back to host activity
                        mListener.onDialogNegativeClick(WarningDialog.this);
                    }
                });
        // Create AlertDialog object and return it
        return builder.create();
    }
}