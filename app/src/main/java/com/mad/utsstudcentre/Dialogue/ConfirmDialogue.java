package com.mad.utsstudcentre.Dialogue;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.utsstudcentre.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmDialogue extends DialogFragment {

    private ConfDialogListener mHost;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Create the custom layout using the LayoutInflater class
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_confirm_dialogue, null);

        // Build the dialog
        builder.setTitle("Do you want to proceed?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHost.onOkayClick(ConfirmDialogue.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setView(v);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        mHost= (ConfDialogListener) context;
        super.onAttach(context);
    }

    public interface ConfDialogListener {
        public void onOkayClick(android.support.v4.app.DialogFragment dlg);
    }
}
