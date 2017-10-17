package com.mad.utsstudcentre.Dialogue;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mad.utsstudcentre.Controller.MainActivity;
import com.mad.utsstudcentre.Model.Booking;
import com.mad.utsstudcentre.Model.Student;
import com.mad.utsstudcentre.R;

import static com.mad.utsstudcentre.Controller.MainActivity.BOOKING_MODEL;
import static com.mad.utsstudcentre.Controller.MainActivity.BOOKING_PREFERENCE;

/**
 * CancelDialogue asking user whether they really want to cancelAlarm the booking
 */
public class CancelDialogue extends DialogFragment {

    private CancelDialogueListener mHost;
    private DatabaseReference mDatabase;

    private Booking mBooking;
    private Student mStudent;
    private SharedPreferences sharedpreferences;
    private TextView mSidTv;
    private TextView mNameTv;
    private TextView mTypeTv;
    private TextView mCentreTv;

    /**
     * Building the dialogue
     * Connect view and set the title / buttons
     * @param savedInstanceState
     * @return
     */

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        sharedpreferences = getActivity().getSharedPreferences(BOOKING_PREFERENCE, Context.MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        mBooking = gson.fromJson(sharedpreferences.getString(BOOKING_MODEL, ""), Booking.class);
        mStudent = mBooking.getStudent();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Create the custom layout using the LayoutInflater class
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_confirm_dialogue, null);
        mSidTv = (TextView) v.findViewById(R.id.dia_sidTv);
        mNameTv = (TextView) v.findViewById(R.id.dia_nameTv);
        mTypeTv = (TextView) v.findViewById(R.id.dia_typeTv);
        mCentreTv = (TextView) v.findViewById(R.id.dia_centreTv);

        // populate fields with Booking_old and Student objects
        final Booking booking = MainActivity.getBooking();
        final Student student =  booking.getStudent();
        mSidTv.setText(student.getsId());
        mNameTv.setText(student.getPrefferedName());
        mTypeTv.setText(booking.getEnqType());
        mCentreTv.setText(booking.getCentre().getCenterName());

        // Build the dialog
        builder.setTitle(R.string.cancel_dialog_title)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference futureBooking = mDatabase.child("futureBooking")
                                .child(mBooking.getEnqType()).child(mStudent.getsId());
                        futureBooking.removeValue();

                        final String waitingNum;
                        if(booking.getCentre().getCenterName().equals("Building 5"))
                            waitingNum = "waitingPeople05";
                        else
                            waitingNum = "waitingPeople10";

                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int currentWaitingNum = dataSnapshot.child("studentCentre")
                                        .child(waitingNum).getValue(Integer.class);
                                currentWaitingNum -= 1;

                                DatabaseReference updatedReference = dataSnapshot.getRef().child("studentCentre")
                                        .child(waitingNum);
                                updatedReference.setValue(currentWaitingNum);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        mHost.onCancelConfirmClick(CancelDialogue.this);
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
//        void onNotCancelClick(DialogFragment dig);
    }
}
