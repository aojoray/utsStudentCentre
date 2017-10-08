package com.mad.utsstudcentre.Controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mad.utsstudcentre.Dialogue.CancelDialogue;
import com.mad.utsstudcentre.Model.Booking;
import com.mad.utsstudcentre.Model.Student;
import com.mad.utsstudcentre.Model.StudentCentre;
import com.mad.utsstudcentre.R;
import com.mad.utsstudcentre.Util.AlarmReceiver;
import com.mad.utsstudcentre.Util.SaveSharedPreference;

import java.util.Calendar;
import java.util.Date;

import static com.mad.utsstudcentre.Controller.FinalConfirmActivity.NOTIFICATION;

/**
 * MainActivity is a launching activity after the login.
 * User can either make a new booking or view their existing booking details
 */
public class MainActivity extends AppCompatActivity implements CancelDialogue.CancelDialogueListener {

    private static final String TAG = "MainActivity_TAG";
    private static final int BOOKING_REQUEST = 1111;
    private static final String FLAG = "Instance flag";
    public static final String CONFIRM = "Confirm booking";
    public static final String CANCEL = "Cancel Booking_old";
    private static final String REF_NUMBER = "refNumber";
    public static final String BOOKING_PREFERENCE = "Booking SharedPreferences";
    public static final String BOOKING_MODEL = "Current booking";
    private static final String CENTRE_MODEL = "Current Centre";
    private static final String STUDENT_MODEL = "Current Student";
    protected static final String BOOKING = "isBookingExist";
    protected static final String BOOKING_FINAL = "isFinalBookingExist";
    public static final String CURRENT_TIME = "Time onPause";
    private static final String EST_TIME = "est onPause";

    private DatabaseReference mDatabase;
    private SharedPreferences sharedpreferences;

    // Models
    private static Student sStudent;
    private static Booking sBooking;
    private static StudentCentre sCentre;

    // Initial data field
    private String mUserSName;
    private String mUserSid;
    private TextView mUserSNameTv;
    private LinearLayout mLayout;

    // Push Notification builder
//    private NotificationCompat.Builder mNotificationBuilder;
//    private NotificationManager mNotificationManager;
    private PendingIntent pendingIntent;

    // Data filed after confirming a booking
    private TextView mBookedSidTv;
    private TextView mBookedUserNameTv;
    private TextView mBookedTypeTv;
    private TextView mBookedCentreTv;
    private TextView mBookedEstTv;
    private Button mCancelBtn;

    // private TextView mBookedWaitingTv;
    public int time =0;
    private int mDelay = 1;//60; // The initial delay between operate() calls is 60 seconds (1 minute).
    private TextView mRefNumTv;
    private CounterThread mThread;

    // getters for static objects Booking_old and Student Centre
    public static Booking getBooking() {
        return sBooking;
    }

    public static StudentCentre getCentre() {
        return sCentre;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences(BOOKING_PREFERENCE, Context.MODE_PRIVATE);

        Log.d(TAG, "onCreate");
        // if no booking exists
        if (!sharedpreferences.getBoolean(BOOKING, false)) {
            Log.d(TAG, "false!");
            // check if final confirmation left
            if (!sharedpreferences.getBoolean(BOOKING_FINAL, false)){
                Log.d(TAG, "Final booking false!");
                setContentView(R.layout.activity_main);
                sStudent = new Student();
                sBooking = new Booking();
                sCentre = new StudentCentre();
                sBooking.setStudent(sStudent);
                sBooking.setCentre(sCentre);
                initialise();
            } else {
                // requires final confirmation of booking
                setFinalView();
            }
        } else {
            // if booking exists
            Log.d(TAG, "true!");
            restore();
        }
    }

    private void setBookingView() {
        setContentView(R.layout.activity_main_booked);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRefNumTv = (TextView) findViewById(R.id.refNumTv);
        mBookedSidTv = (TextView) findViewById(R.id.booked_sidTv);
        mBookedUserNameTv = (TextView) findViewById(R.id.booked_nameTv);
        mBookedTypeTv = (TextView) findViewById(R.id.booked_enqTypeTv);
        mBookedCentreTv = (TextView) findViewById(R.id.booked_centreTv);
        mBookedEstTv = (TextView) findViewById(R.id.booked_estTv);

        mCancelBtn = (Button) findViewById(R.id.cancelBtn_main);

        // setText with booking information
        mRefNumTv.setText(sBooking.getReference());
        mBookedSidTv.setText(mUserSid);
        mBookedUserNameTv.setText(sBooking.getStudent().getPrefferedName());
        mBookedTypeTv.setText(sBooking.getEnqType());
        mBookedCentreTv.setText(sCentre.getCenterName());

        // set the estimated time of waiting
        mBookedEstTv.setText(time + " min");

        //TODO: booking time may need change in logic
        sBooking.setDate(new Date().toLocaleString());
        Log.d(TAG, "Booking Date: " + sBooking.getDate());


        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelDialogue cancelDialogue = new CancelDialogue();
                cancelDialogue.show(getSupportFragmentManager(), "CancelDialogue");
            }
        });
    }


    private void setFinalView() {
        Intent finalIntent = new Intent(MainActivity.this, FinalConfirmActivity.class);
        startActivity(finalIntent);
        finish();
    }

    private void initialise() {
        Log.d(TAG, "initialise()");
        if (SaveSharedPreference.getUserName(MainActivity.this).length() == 0) {
            Log.d(TAG, "@initialise: Launch Login");
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        } else {
            //Populate data stored in the login activity from SaveSharedPreference
            Log.d(TAG, "@initialise: Launch MainView");

            mUserSName = SaveSharedPreference.getFirstName(MainActivity.this);
            mUserSid = SaveSharedPreference.getUserName(MainActivity.this);

            sStudent.setPrefferedName(mUserSName);
            sStudent.setsId(mUserSid);

            mUserSName = SaveSharedPreference.getFirstName(MainActivity.this);
            mUserSNameTv = (TextView) findViewById(R.id.userSidTv);
            mUserSNameTv.setText(" " + mUserSName);

            sBooking.setStudent(sStudent);
            Log.d(TAG, "Name -- > " + getBooking().getStudent().getPrefferedName());
            sBooking.setCentre(sCentre);


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
     * Handles Booking_old result
     * Once User confirms the booking, this method will be called.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && !data.getBooleanExtra(NOTIFICATION, false)) {
            Log.d(TAG, "RESULT OKAY! ");

            time = sCentre.getEstTime();

            setBookingView();
            SharedPreferences sharedpreferences = getSharedPreferences(BOOKING_PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            Gson gson = new GsonBuilder().create();

            editor.putBoolean(BOOKING, true);
            editor.putString(BOOKING_MODEL, gson.toJson(sBooking));
            editor.putString(STUDENT_MODEL, gson.toJson(sStudent));
            editor.putString(CENTRE_MODEL, gson.toJson(sCentre));
            editor.commit();

            mCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CancelDialogue cancelDialogue = new CancelDialogue();
                    cancelDialogue.show(getSupportFragmentManager(), "CancelDialogue");
                }
            });

            /* Retrieve a PendingIntent that will perform a broadcast */
            Intent alarmIntent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
            startAlarm();

            startup();   // start the Thread for count

        }
    }

    /**
     * Starting the alarm service.
     */
    public void startAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        //TODO: Change to ((time * 60000) - 600000) for 10 minutes before
        calendar.setTimeInMillis((System.currentTimeMillis() + 5000)); // Alarm set to 5sec (5000) / 1 minute (60000) after for testing

        Log.d(TAG, "current time = " + new Date().toLocaleString());
        Log.d(TAG, "Alarm set at = " + calendar.getTime());

        /* no Repeat */
        // TODO: replace 2nd with --> (Calendar.getInstance().getTimeInMillis() + (time*1000))
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                0, pendingIntent);
    }

    /**
     * Method to cancelAlarm the alarm service
     */
    public void cancelAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
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

        Log.d(TAG, "Thread Name: " + mThread.getId());
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
     * Handles the booking cancelAlarm event.
     * Once the user confirms to cancelAlarm the booking, this method will be called and initialise the view and fields
     *
     * @param dlg
     */
    @Override
    public void onCancelConfirmClick(DialogFragment dlg) {
        //TODO: thread sleep may be required
        setContentView(R.layout.activity_main);
        mLayout = (LinearLayout) findViewById(R.id.main_layout);
        Snackbar.make(mLayout, R.string.booking_cancel_msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        clearRecord();
        cancelAlarm();
        initialise();
    }

    /**
     * clearRecord clears sharedPreference and shutdown the thread when booking is canceled
     */
    private void clearRecord() {
        // Clearing current booking information from sharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        shutdown();
    }

    /**
     * clearBooking sets Booking at sharedPreference to false and shutdown the thread
     */
    private void clearBooking() {
        // Clearing current booking information from sharedPreference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(BOOKING, false);
        editor.commit();
        time =0;
        shutdown();
    }

    private void restore() {
        Log.d(TAG, "Restore! ");
        Gson gson = new GsonBuilder().create();
        sStudent = gson.fromJson(sharedpreferences.getString(STUDENT_MODEL, ""), Student.class);
        sBooking = gson.fromJson(sharedpreferences.getString(BOOKING_MODEL, ""), Booking.class);
        sCentre = gson.fromJson(sharedpreferences.getString(CENTRE_MODEL, ""), StudentCentre.class);
        long difference = System.currentTimeMillis() - sharedpreferences.getLong(CURRENT_TIME, 0);
        Log.d(TAG, "Difference/60000 == " + difference / 1000);
        // TODO: Replace 1000 with 60000 after testing
        time = (int) (sharedpreferences.getInt(EST_TIME, 0) - (difference / 1000)); //1000 for sec 60000 for min

        // if the estimated time is already under 0, set the time to 0. Otherwise, run thread for counting down.
        if (time <= 0) {
            time = 0;
            clearBooking();
            setFinalView();
        } else {
            // start counter thread
            startup();
            setBookingView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (sharedpreferences.getBoolean(BOOKING, false)) {
            Log.d(TAG, "@onPause Booking True");
            Gson gson = new GsonBuilder().create();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putLong(CURRENT_TIME, System.currentTimeMillis());
            editor.putBoolean(BOOKING, true);
            editor.putInt(EST_TIME, time);
            editor.commit();
            shutdown();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    /**
     * CounterThread is used for counting the remained time and change the view accordingly
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
                        Log.d(TAG, "Thread Time @ Loop: " + time);

                        time--;
                        MainActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                mBookedEstTv.setText(time + " min");
                            }
                        });
                        if (time <= 0) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    clearBooking();
                                    setFinalView();
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
            Log.d(TAG, "Main Thread shutdown");
            interrupt();
        }
    }

}
