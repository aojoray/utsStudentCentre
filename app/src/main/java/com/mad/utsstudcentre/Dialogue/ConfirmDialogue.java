package com.mad.utsstudcentre.Dialogue;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.mad.utsstudcentre.Controller.CentreFragment;
import com.mad.utsstudcentre.Controller.MainActivity;
import com.mad.utsstudcentre.Model.Booking;
import com.mad.utsstudcentre.Model.Student;
import com.mad.utsstudcentre.R;
import com.mad.utsstudcentre.Util.SaveSharedPreference;

/**
 * ConfirmDialogue shows user the booking details and let user to check the detail again and confirm booking
 */
public class ConfirmDialogue extends DialogFragment {

    private static final String TAG = "ConfirmDialogue_TAG";
    public static final String INDEX_TYPE = "IndexType";
    public static final String WAITING_05 = "waitingPeople05";
    private ConfDialogListener mHost;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String enqIndex = getArguments().getString(INDEX_TYPE);
        final String waitingNum = getArguments().getString(WAITING_05);

        // Create the custom layout using the LayoutInflater class
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_confirm_dialogue, null);

        mSidTv = (TextView) v.findViewById(R.id.dia_sidTv);
        mNameTv = (TextView) v.findViewById(R.id.dia_nameTv);
        mTypeTv = (TextView) v.findViewById(R.id.dia_typeTv);
        mCentreTv = (TextView) v.findViewById(R.id.dia_centreTv);

        // populate fields with Booking and Student objects
        final Booking booking = MainActivity.getBooking();
        final Student student =  booking.getStudent();
        mSidTv.setText(student.getId());
        mNameTv.setText(student.getName());
        mTypeTv.setText(booking.getEnquiryType());
        mCentreTv.setText(booking.getStudentCentre().getName());

        // Build the dialog
        builder.setTitle("Do you want to proceed?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHost.onOkayClick(ConfirmDialogue.this);
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference futureBookingChild = mDatabase.child("futureBooking")
                                .child(booking.getEnquiryType())
                                .child(student.getId());
                        futureBookingChild.child("refNumber").setValue(booking.getRefNumber());
                        futureBookingChild.child("studentId").setValue(student.getId());
                        futureBookingChild.child("studentName").setValue(student.getName());
                        futureBookingChild.child("studentCentre").setValue(booking.getStudentCentre());
                        futureBookingChild.child("bookingConfirmation").setValue("false");

                        //decrease waiting People if the user confirm the booking
                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int currentWaitingNum = dataSnapshot.child("bookings").child(enqIndex)
                                        .child(waitingNum).getValue(Integer.class);
                                currentWaitingNum += 1;

                                DatabaseReference updatedReference = dataSnapshot.getRef().child("bookings")
                                        .child(enqIndex).child(waitingNum);
                                updatedReference.setValue(currentWaitingNum);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        SaveSharedPreference.setBookingDetails(ConfirmDialogue.this.getContext(), booking.getRefNumber(),
                                student.getName(), booking.getEnquiryType(), booking.getStudentCentre().getName());
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

    /**
     * Binds listener to the object
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        mHost= (ConfDialogListener) context;
        super.onAttach(context);
    }

    /**
     * Interface for confirmation dialogue Listener
     */
    public interface ConfDialogListener {
//        void onOkayClick(DialogFragment dlg, String string1, String s, String string, int estTime);
        void onOkayClick(DialogFragment dlg);
    }
}
