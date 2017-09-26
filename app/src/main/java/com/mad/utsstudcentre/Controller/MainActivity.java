package com.mad.utsstudcentre.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.utsstudcentre.Dialogue.CancelDialogue;
import com.mad.utsstudcentre.Model.Booking;
import com.mad.utsstudcentre.Model.Student;
import com.mad.utsstudcentre.Model.StudentCentre;
import com.mad.utsstudcentre.R;
import com.mad.utsstudcentre.Util.SaveSharedPreference;

import java.util.Date;

import static com.mad.utsstudcentre.Controller.LoginActivity.USERSID;

/**
 * MainActivity is a launching activity after the login.
 * User can either make a new booking or view their existing booking details
 */
public class MainActivity extends AppCompatActivity implements CancelDialogue.CancelDialogueListener {

    private static final String TAG = "MainActivity_TAG";
    private static final int BOOKING_REQUEST = 1111;

    private static Student sStudent;
    private static Booking sBooking;
    private static StudentCentre sCentre;

    // Initial data field
    private String mUserSName;
    private String mUserSid;
    private TextView mUserSNameTv;
    private Button mCancelBtn;
    private LinearLayout mLayout;


    // Data filed after confirming a booking
    private TextView mBookedSidTv;
    private TextView mBookedUserNameTv;
    private TextView mBookedTypeTv;
    private TextView mBookedCentreTv;
    private TextView mBookedEstTv;
    //    private TextView mBookedWaitingTv;
    public int time;
    private int mDelay = 60; // The initial delay between operate() calls is 60 seconds (1 minute).
    private TextView mRefNumTv;
    private OperationThread mThread;

    // getters for static objects
    public static Booking getBooking() {
        return sBooking;
    }

    public static StudentCentre getCentre() {
        return sCentre;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sStudent = new Student();
        sBooking = new Booking();
        sCentre = new StudentCentre();
        sBooking.setStudent(sStudent);
        sBooking.setStudentCentre(sCentre);
        initialise();
    }

    private void initialise() {
        if (SaveSharedPreference.getUserName(MainActivity.this).length() == 0) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else {

            //Populate data stored in the login activity from SaveSharedPreference
            mUserSName = SaveSharedPreference.getFirstName(MainActivity.this);
            mUserSid = SaveSharedPreference.getUserName(MainActivity.this);

            sStudent.setName(mUserSName);
            sStudent.setId(mUserSid);

            mUserSName = SaveSharedPreference.getFirstName(MainActivity.this);
            mUserSNameTv = (TextView) findViewById(R.id.userSidTv);
            mUserSNameTv.setText(" " + mUserSName);

            sBooking.setStudent(sStudent);
            Log.d(TAG, "Name -- > " + getBooking().getStudent().getName());
            sBooking.setStudentCentre(sCentre);


            Button newBookingBtn = (Button) findViewById(R.id.new_booking_btn);
            newBookingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EnquiryTypeActivity.class);
                    startActivityForResult(intent, BOOKING_REQUEST);
                }
            });

            Button logoutBtn = (Button) findViewById(R.id.logout_btn);
            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveSharedPreference.clearUserName(MainActivity.this);
                    Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(logoutIntent);
                    finish();
                }

            });
        }

    }

    /**
     * Handles Booking result
     * Once User confirms the booking, this method will be called.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.d(TAG, "RESULT OKAY! ");
            setContentView(R.layout.activity_main_booked);

            mRefNumTv = (TextView) findViewById(R.id.refNumTv);
            mBookedSidTv = (TextView) findViewById(R.id.booked_sidTv);
            mBookedUserNameTv = (TextView) findViewById(R.id.booked_nameTv);
            mBookedTypeTv = (TextView) findViewById(R.id.booked_enqTypeTv);
            mBookedCentreTv = (TextView) findViewById(R.id.booked_centreTv);
            mBookedEstTv = (TextView) findViewById(R.id.booked_estTv);

            mCancelBtn = (Button) findViewById(R.id.cancelBtn_main);

            // setText with booking information
            mRefNumTv.setText(sBooking.getRefNumber());
            mBookedSidTv.setText(mUserSid);
            mBookedUserNameTv.setText(sBooking.getStudent().getName());
            mBookedTypeTv.setText(sBooking.getEnquiryType());
            mBookedCentreTv.setText(sCentre.getName());

            // set the estimated time of waiting
            time = sCentre.getEstTime();
            mBookedEstTv.setText(time + " min");

            //TODO: booking time may need change in logic
            sBooking.setDate(new Date().toLocaleString());
            Log.d(TAG, "Booking Date: " + sBooking.getDate());

            startup(); // start the Thread for count

            mCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CancelDialogue cancelDialogue = new CancelDialogue();
                    cancelDialogue.show(getSupportFragmentManager(), "CancelDialogue");
                }
            });

        }
    }

    /**
     * Start the new thread for counter
     */
    public synchronized void startup() {
        if (mThread == null) {
            mThread = new OperationThread();
            mThread.start();
        }
    }

    /**
     * Shut down the counter thread
     */
    public synchronized void shutdown() {
        if (mThread != null) {
            mThread.shutdown();
        }
    }

    /**
     * Handles the booking cancel event.
     * Once the user confirms to cancel the booking, this method will be called and initialise the view and fields
     * @param dlg
     */
    @Override
    public void onCancelConfirmClick(DialogFragment dlg) {
        //TODO: thread sleep may be required
        setContentView(R.layout.activity_main);
        mLayout = (LinearLayout) findViewById(R.id.main_layout);
        Snackbar.make(mLayout, R.string.booking_cancel_msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        initialise();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shutdown();
    }

    /**
     * OperationThread is used for counting the remained time and change the view accordingly
     * The interval is manipulated by mDelay (if mDelay = 6 -> interval = 60 seconds)
     */
    private class OperationThread extends Thread {
        private boolean running = true;

        @Override
        public void run() {
            while (time > 0) {
                try {
                    Thread.sleep(mDelay * 1000); // delay * 1000 milliseconds
                    time--;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            mBookedEstTv.setText(time + " min");
                        }
                    });

                } catch (InterruptedException e) {
                    // Happens when requested to shut down
                }
            }
            shutdown();
        }

        public void shutdown() {
            running = false;
            interrupt();
        }
    }

}
