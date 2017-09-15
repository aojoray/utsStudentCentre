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
import com.mad.utsstudcentre.R;
import com.mad.utsstudcentre.Util.SaveSharedPreference;

import static com.mad.utsstudcentre.Controller.CentreFragment.CENTRE_TYPE;
import static com.mad.utsstudcentre.Controller.CentreFragment.EST_TIME;
import static com.mad.utsstudcentre.Controller.CentreFragment.FINAL_TYPE;
import static com.mad.utsstudcentre.Controller.CentreFragment.REF_NUMBER;
import static com.mad.utsstudcentre.Controller.LoginActivity.USERNAME;

/**
 * MainActivity is a launching activity after the login.
 * User can either make a new booking or view their existing booking details
 */
public class MainActivity extends AppCompatActivity implements CancelDialogue.CancelDialogueListener {

    private static final String TAG = "MainActivity_TAG";
    private static final int BOOKING_REQUEST = 1111;

    // Initial data field
    private String mUserSid;
    private TextView mUserSidTv;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialise();
    }

    private void updateTime() {
    }

    private void initialise() {
        if (SaveSharedPreference.getUserName(MainActivity.this).length() == 0) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            mUserSid = getIntent().getStringExtra(USERNAME);
            mUserSidTv = (TextView) findViewById(R.id.userSidTv);
            mUserSidTv.setText(" " + mUserSid);


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
            mRefNumTv.setText(data.getStringExtra(REF_NUMBER));
            mBookedSidTv.setText(mUserSid);
//            mBookedUserNameTv.setText(mUserSid);
            mBookedTypeTv.setText(data.getStringExtra(FINAL_TYPE));
            mBookedCentreTv.setText(data.getStringExtra(CENTRE_TYPE));

            // set the estimated time of waiting
            time = data.getIntExtra(EST_TIME, 0);
            mBookedEstTv.setText(time + " min");

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

    public synchronized void startup() {
        if (mThread == null) {
            mThread = new OperationThread();
            mThread.start();
        }
    }

    @Override
    public void onCancelConfirmClick(DialogFragment dlg) {
        setContentView(R.layout.activity_main);
        mLayout = (LinearLayout) findViewById(R.id.main_layout);
        Snackbar.make(mLayout, R.string.booking_cancel_msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        initialise();
    }

//    public int getDelay() {
//        return mDelay;
//    }

    public synchronized void shutdown() {
        if (mThread != null) {
            mThread.shutdown();
        }
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
