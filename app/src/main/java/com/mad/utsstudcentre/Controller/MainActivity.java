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
import android.widget.Toast;

import com.mad.utsstudcentre.Dialogue.CancelDialogue;
import com.mad.utsstudcentre.Dialogue.ConfirmDialogue;
import com.mad.utsstudcentre.R;

import org.w3c.dom.Text;

import static com.mad.utsstudcentre.Controller.LoginActivity.DATABDL;
import static com.mad.utsstudcentre.Controller.LoginActivity.USERNAME;

/**
 * MainActivity is a launching activity after the login.
 * User can either make a new booking or view their existing booking details
 */
public class MainActivity extends AppCompatActivity implements CancelDialogue.CancelDialogueListener {

    private static final String TAG = "MainActivity_TAG";
    private static final int BOOKING_REQUEST = 1001;
    private String mUserName;
    private TextView mUserNameTv;
    private Button mCancelBtn;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = getIntent().getStringExtra(USERNAME);
        mUserNameTv = (TextView) findViewById(R.id.userNameTv);
        mUserNameTv.setText(" " + mUserName);


        Button newBookingBtn = (Button) findViewById(R.id.new_booking_btn);
        newBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(v.getContext(), EnquiryTypeActivity.class));
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
            mCancelBtn = (Button) findViewById(R.id.cancelBtn_main);

            mCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CancelDialogue cancelDialogue = new CancelDialogue();
                    cancelDialogue.show(getSupportFragmentManager(), "CancelDialogue");
                }
            });

        }
    }

    @Override
    public void onCancelConfirmClick(DialogFragment dlg) {
        setContentView(R.layout.activity_main);
        mLayout = (LinearLayout) findViewById(R.id.main_layout);
        Snackbar.make(mLayout, R.string.booking_cancel_msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
