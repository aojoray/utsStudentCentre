package com.mad.utsstudcentre.Controller;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mad.utsstudcentre.Dialogue.CancelDialogue;
import com.mad.utsstudcentre.R;

import static com.mad.utsstudcentre.Controller.MainActivity.CONFIRM;

/**
 * FinalConfirmActivity responses to the user's action toward the notification.
 * If user confirm the booking finally, this activity will display the final confirmation screen for staff.
 *  Otherwise, will ask user whether they want to cancel the booking.
 */
public class FinalConfirmActivity extends AppCompatActivity implements CancelDialogue.CancelDialogueListener{

    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    protected static final String NOTIFICATION = "Notification";
    private TextView mAnsTv;
    private Button mConfBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Closing the notification
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(getIntent().getIntExtra(NOTIFICATION_ID, 1001));

        // Set the view according to the user's response
        if (getIntent().getAction() == CONFIRM) {
            setContentView(R.layout.activity_final_confirm);
            mAnsTv = (TextView) findViewById(R.id.ansTv);
            mAnsTv.setText(getIntent().getAction());
            mConfBtn = (Button) findViewById(R.id.final_conf_btn);
            mConfBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setContentView(R.layout.activity_main);

                }
            });
        } else {
            setContentView(R.layout.activity_main_booked);
            CancelDialogue cancelDialogue = new CancelDialogue();
            cancelDialogue.show(getSupportFragmentManager(), "CancelDialogue");
        }
    }

    @Override
    public void onCancelConfirmClick(DialogFragment dlg) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        setResult(RESULT_OK, intent);
        startActivity(intent);
        finish();
    }
}
