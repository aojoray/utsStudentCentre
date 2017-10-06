package com.mad.utsstudcentre.Controller;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mad.utsstudcentre.Dialogue.CancelDialogue;
import com.mad.utsstudcentre.Model.Booking;
import com.mad.utsstudcentre.R;

import static com.mad.utsstudcentre.Controller.CentreFragment.EST_TIME;
import static com.mad.utsstudcentre.Controller.MainActivity.BOOKING_FINAL;
import static com.mad.utsstudcentre.Controller.MainActivity.BOOKING_MODEL;
import static com.mad.utsstudcentre.Controller.MainActivity.BOOKING_PREFERENCE;
import static com.mad.utsstudcentre.Controller.MainActivity.CANCEL;
import static com.mad.utsstudcentre.Controller.MainActivity.CURRENT_TIME;

/**
 * FinalConfirmActivity responses to the user's action toward the notification.
 * If user confirm the booking finally, this activity will display the final confirmation screen for staff.
 * Otherwise, will ask user whether they want to cancelAlarm the booking.
 */
public class FinalConfirmActivity extends AppCompatActivity implements CancelDialogue.CancelDialogueListener {

    private static final String TAG = "FinalConfirmAct_TAG";
    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    protected static final String NOTIFICATION = "Notification";
    protected static final String CANCEL_PUSH = "Cancel from Push";

    // Final Confirmation View
    private TextView mRefNumTv;
    private Button mConfBtn;
    private Booking mBooking;

    // Data filed after confirming a booking
    private TextView mBookedSidTv;
    private TextView mBookedUserNameTv;
    private TextView mBookedTypeTv;
    private TextView mBookedCentreTv;
    private TextView mBookedEstTv;
    private Button mCancelBtn;


    // Counter for end activity: 10 minutes later, will destroy itself regardless user's input
    private CounterThread mThread;
    private int time; //timer
    private int mDelay = 1; // //60; // The initial delay between operate() calls is 60 seconds (1 minute).

    private SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_confirm);
        // Closing the notification
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(getIntent().getIntExtra(NOTIFICATION_ID, 1001));

        sharedpreferences = getSharedPreferences(BOOKING_PREFERENCE, Context.MODE_PRIVATE);
        Gson gson = new GsonBuilder().create();
        mBooking = gson.fromJson(sharedpreferences.getString(BOOKING_MODEL, ""), Booking.class);
        mRefNumTv = (TextView) findViewById(R.id.refNumTv);
        mRefNumTv.setText(mBooking.getReference());
        mConfBtn = (Button) findViewById(R.id.final_conf_btn);
        // Confirm button click will clear all the booking information and launch mainActivity
        mConfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRecord();
                launchMain();
            }
        });

        // Set the view according to the user's response
        if (getIntent().getAction() == CANCEL) {
            Log.d(TAG, "Opened from Cancel");
            CancelDialogue cancelDialogue = new CancelDialogue();
            cancelDialogue.show(getSupportFragmentManager(), "CancelDialogue");
        } else {

            // if this was restored before time is up
            if (sharedpreferences.getBoolean(BOOKING_FINAL, false)) {
                Log.d(TAG, "Booking Final is true");
                long difference = System.currentTimeMillis() - sharedpreferences.getLong(CURRENT_TIME, 0);
                Log.d(TAG, "Difference/1000 == " + difference / 1000);
                // TODO: Replace 1000 with 60000 after testing
                time = (int) (sharedpreferences.getInt(EST_TIME, 0) - (difference / 1000)); //1000 for sec 60000 for min

                // if the estimated time is already under 0, set the time to 0 and finish the activity.
                // Otherwise, run thread for counting down.
                if (time <= 0) {
                    time = 0;
                    clearRecord();
                    expiredActivity();
                } else {
                    // start counter thread
                    startup();
                }
            } else {
                Log.d(TAG, "Booking Final is false");
                // Operate the thread to count down
                time = 10;
                startup();
            }
        }
    }

    /**
     * Handles the cancelAlarm confirm event
     * @param dlg
     */
    @Override
    public void onCancelConfirmClick(DialogFragment dlg) {
        clearRecord();
        launchMain();
    }

    /**
     * Finish activity after 10 minutes of inactivity
     */
    private void expiredActivity() {
        clearRecord();
        Toast.makeText(getApplicationContext(), "Your Booking is expired", Toast.LENGTH_LONG).show();
        launchMain();
    }

    /**
     * Clear booking record and launch MainActivity
     */
    private void launchMain(){
        shutdown();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void clearRecord() {
        // Clearing current booking information from sharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        shutdown();
    }

    /**
     * To restore activity
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (time > 0) {
            Log.d(TAG, "Final onPause");
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putLong(CURRENT_TIME, System.currentTimeMillis());
            editor.putInt(EST_TIME, time);
            editor.putBoolean(BOOKING_FINAL, true);
            editor.commit();
            shutdown();
        }
    }


    /**
     * Start the new thread for counter
     */
    public synchronized void startup() {

        if (mThread == null) {
            mThread = new CounterThread();
            new Thread(mThread).start();
        }
        Log.d(TAG, "startup");
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
     * CounterThread is used for counting the remained sTime and change the view accordingly
     * The interval is manipulated by mDelay (if mDelay = 6 -> interval = 60 seconds)
     */
    private class CounterThread extends Thread {
        private boolean running = true;

        @Override
        public void run() {
            if (time > 0) {
                while (running) {
                    try {
                        Thread.sleep(mDelay * 1000); // delay * 1000 milliseconds
                        Log.d(TAG, "Count down at Final: " + time);

                        time--;

                        if (time <= 0) {
                            FinalConfirmActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    expiredActivity();
                                }
                            });
                            shutdown();
                        }


                    } catch (InterruptedException e) {
                        // Happens when requested to shut down
                    }
                }
            }
            shutdown();
        }

        public void shutdown() {
            running = false;
            Log.d(TAG, "Final Thread shutdown");
            interrupt();
        }
    }

}
