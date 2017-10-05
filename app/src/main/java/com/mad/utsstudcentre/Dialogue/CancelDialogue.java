package com.mad.utsstudcentre.Dialogue;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mad.utsstudcentre.Controller.MainActivity;
import com.mad.utsstudcentre.Model.Booking;
import com.mad.utsstudcentre.Model.Student;
import com.mad.utsstudcentre.R;

/**
 * CancelDialogue asking user whether they really want to cancel the booking
 */
public class CancelDialogue extends DialogFragment {

    private CancelDialogueListener mHost;
    private DatabaseReference mDatabase;

    private Booking mBooking;
    private Student mStudent;

    /**
     * Building the dialogue
     * Connect view and set the title / buttons
     * @param savedInstanceState
     * @return
     */

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mBooking = MainActivity.getBooking();
        mStudent = mBooking.getStudent();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Create the custom layout using the LayoutInflater class
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_confirm_dialogue, null);

        // Build the dialog
        builder.setTitle(R.string.cancel_dialog_title)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHost.onCancelConfirmClick(CancelDialogue.this);
                        DatabaseReference futureBooking = mDatabase.child("futureBooking")
                                .child(mBooking.getEnqType()).child(mStudent.getsId());
                        futureBooking.removeValue();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                }
                })
                .setView(v);

        return builder.create();
    }

    /**
     * Binds listener
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        mHost= (CancelDialogueListener) context;
        super.onAttach(context);
    }

    /**
     * Interface for Listener
     */
    public interface CancelDialogueListener {
        void onCancelConfirmClick(DialogFragment dlg);
    }
}
