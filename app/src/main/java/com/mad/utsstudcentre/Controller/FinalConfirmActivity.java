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

import com.mad.utsstudcentre.Dialogue.CancelDialogue;
import com.mad.utsstudcentre.R;

import static com.mad.utsstudcentre.Controller.MainActivity.BOOKING;
import static com.mad.utsstudcentre.Controller.MainActivity.BOOKING_PREFERENCE;
import static com.mad.utsstudcentre.Controller.MainActivity.CANCEL;

/**
 * FinalConfirmActivity responses to the user's action toward the notification.
 * If user confirm the booking finally, this activity will display the final confirmation screen for staff.
 *  Otherwise, will ask user whether they want to cancel the booking.
 */
public class FinalConfirmActivity extends AppCompatActivity implements CancelDialogue.CancelDialogueListener{

    private static final String TAG = "FinalConfirmAct_TAG";
    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    protected static final String NOTIFICATION = "Notification";
    private TextView mAnsTv;
    private Button mConfBtn;

    // Counter for end activity: 10 minutes later, will destroy itself regardless user's input
    private CounterThread mThread;
    private int time = 10; //10 minutes
    private int mDelay = 1; // //60; // The initial delay between operate() calls is 60 seconds (1 minute).

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Closing the notification
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(getIntent().getIntExtra(NOTIFICATION_ID, 1001));

        // Set the view according to the user's response
        if (getIntent().getAction() == CANCEL) {
            setContentView(R.layout.activity_main_booked);
            CancelDialogue cancelDialogue = new CancelDialogue();
            cancelDialogue.show(getSupportFragmentManager(), "CancelDialogue");

        } else {
            setContentView(R.layout.activity_final_confirm);
            mAnsTv = (TextView) findViewById(R.id.ansTv);
            mAnsTv.setText(getIntent().getAction());
            mConfBtn = (Button) findViewById(R.id.final_conf_btn);
            mConfBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedpreferences = getSharedPreferences(BOOKING_PREFERENCE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(BOOKING, false);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    setResult(RESULT_OK, intent);
                    startActivity(intent);
                    finish();

                }
            });
            // Operate the thread to count down
            startup();
        }
    }

    @Override
    public void onCancelConfirmClick(DialogFragment dlg) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        setResult(RESULT_OK, intent);
        startActivity(intent);
        finish();
    }

    private void endActivity() {
        Toast.makeText(getApplicationContext(), "Your Booking is expired", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
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
                                    endActivity();
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
            Log.d(TAG, "Thread shutdown");
            interrupt();
        }
    }

}
