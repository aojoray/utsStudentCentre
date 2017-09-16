package com.mad.utsstudcentre.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mad.utsstudcentre.Dialogue.ConfirmDialogue;
import com.mad.utsstudcentre.R;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

/**
 * EnquiryTypeActivity is the activity launched first when user make a new booking.
 * User can select the enquiry type or get some help about the possible options
 * Some Enquiries have subenquiry types.
 * In that case, SunEnqFragment will be loaded.
 */
public class EnquiryTypeActivity extends AppCompatActivity implements ConfirmDialogue.ConfDialogListener {

    private static final String SUB_ENQUIRY_FRAGMENT = "Sub-enquiry fragment";
    private static final String TAG = "EnquiryTypeActivity_TAG";
    public static final String HELP_ENQUIRY_FRAGMENT = "help enquiry fragment";
    public static final String CODE_ENQ_TYPE = "EnquiryTypeActivity";
    private Button mEnqBtn01;
    private Button mEnqBtn02;
    private Button mEnqBtn03;
    private Button mEnqBtn04;
    private Button mEnqBtn05;
    private Button mEnqBtn06;
    private Button mEnqBtn07;
    private Button mEnqBtn08;
    private ImageButton mHelpBtn;
    private FrameLayout mFrame;
    public static final int TYPE_SUbJ_ENROL = 1;
    public static final int TYPE_STUDY_PLAN = 2;
    public static final int TYPE_UTS_DOC = 3;
    public static final int TYPE_GENERAL = 4;
    public static final int TYPE_TIMETABLE = 5;
    public static final int TYPE_ASSESSMENT = 6;
    public static final int TYPE_GRADUATION = 7;
    public static final int TYPE_IT_SUPPORT = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_type);


        // Connect buttons to controller
        mEnqBtn01 = (Button) findViewById(R.id.enqBtn01);
        mEnqBtn02 = (Button) findViewById(R.id.enqBtn02);
        mEnqBtn03 = (Button) findViewById(R.id.enqBtn03);
        mEnqBtn04 = (Button) findViewById(R.id.enqBtn04);
        mEnqBtn05 = (Button) findViewById(R.id.enqBtn05);
        mEnqBtn06 = (Button) findViewById(R.id.enqBtn06);
        mEnqBtn07 = (Button) findViewById(R.id.enqBtn07);
        mEnqBtn08 = (Button) findViewById(R.id.enqBtn08);
        mHelpBtn = (ImageButton) findViewById(R.id.enqHelpBtn);
        // Connect Fragment container
        mFrame = (FrameLayout) findViewById(R.id.container);

        //Set OnclickListeners for buttons
        // My subject enrolment btn
        mEnqBtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "My subject enrolment", Toast.LENGTH_SHORT).show();
                // let FrameLayout for Fragment visible
                mFrame.setVisibility(View.VISIBLE);
                // instantiate the fragment and commit to open
                SubEnqFragment subEnqFragment = SubEnqFragment.newInstance(TYPE_SUbJ_ENROL);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(null).setTransition(TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.container, subEnqFragment, SUB_ENQUIRY_FRAGMENT).commit();

            }
        });

        // My Study plan btn
        mEnqBtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "My Study plan", Toast.LENGTH_SHORT).show();

            }
        });

        //My UTS documents btn
        mEnqBtn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "My UTS documents", Toast.LENGTH_SHORT).show();

            }
        });

        // General Enquiry btn
        mEnqBtn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "General Enquiry", Toast.LENGTH_SHORT).show();

            }
        });

        // My class timetable btn
        mEnqBtn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "My class timetable", Toast.LENGTH_SHORT).show();

            }
        });

        //Exams and assessment btn
        mEnqBtn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Exams and assessment", Toast.LENGTH_SHORT).show();

            }
        });

        // My Graduation btn
        mEnqBtn07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "My Graduation", Toast.LENGTH_SHORT).show();

            }
        });

        // IT support btn
        mEnqBtn08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "IT support", Toast.LENGTH_SHORT).show();

            }
        });

        // Help btn providing descriptions for available options
        mHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT).show();
                // let FrameLayout for Fragment visible
                mFrame.setVisibility(View.VISIBLE);
                // instantiate the fragment and commit to open
                HelpEnqFragment helpEnqFragment = HelpEnqFragment.newInstance(CODE_ENQ_TYPE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(null).setTransition(TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.container, helpEnqFragment, HELP_ENQUIRY_FRAGMENT).commit();

            }
        });


    }

    /**
     * Overridden OnBackPressed
     * changes the visibility of FrameLayout for Fragments
     */
    @Override
    public void onBackPressed() {

        Log.d(TAG, "COUNT: "+getSupportFragmentManager().getBackStackEntryCount());
        if(getSupportFragmentManager().getBackStackEntryCount() == 1){
            mFrame.setVisibility(View.GONE);
        }
        super.onBackPressed();
    }

    /**
     * If the booking is confirmed, this callback message will finish the intent and pass the reference number to MainActivity
     * @param dlg
     */
    @Override
    public void onOkayClick(DialogFragment dlg) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
