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

import static com.mad.utsstudcentre.Controller.CentreFragment.CENTRE_TYPE;
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
    private TextView mRefNumTv;
    private TextView mBookedSidTv;
    private TextView mBookedUserNameTv;
    private TextView mBookedTypeTv;
    private TextView mBookedCentreTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialise();
    }

    private void initialise() {
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

            mCancelBtn = (Button) findViewById(R.id.cancelBtn_main);

            // setText with booking information
            mRefNumTv.setText(data.getStringExtra(REF_NUMBER));
            mBookedSidTv.setText(mUserSid);
//            mBookedUserNameTv.setText(mUserSid);
            mBookedTypeTv.setText(data.getStringExtra(FINAL_TYPE));
            mBookedCentreTv.setText(data.getStringExtra(CENTRE_TYPE));

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
     * Handles booking cancellation
     * Initialise the view
     *
     * @param dlg
     */
    @Override
    public void onCancelConfirmClick(DialogFragment dlg) {
        setContentView(R.layout.activity_main);
        mLayout = (LinearLayout) findViewById(R.id.main_layout);
        Snackbar.make(mLayout, R.string.booking_cancel_msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        initialise();
    }
}
