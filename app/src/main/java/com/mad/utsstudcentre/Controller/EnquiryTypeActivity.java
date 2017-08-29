package com.mad.utsstudcentre.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mad.utsstudcentre.R;

public class EnquiryTypeActivity extends AppCompatActivity {

    private Button mEnqBtn01;
    private Button mEnqBtn02;
    private Button mEnqBtn03;
    private Button mEnqBtn04;
    private Button mEnqBtn05;
    private Button mEnqBtn06;
    private Button mEnqBtn07;
    private Button mEnqBtn08;
    private ImageButton mHelpBtn;

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

        //Set OnclickListeners for buttons
        // My subject enrolment btn
        mEnqBtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "My subject enrolment", Toast.LENGTH_SHORT).show();
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

            }
        });


    }
}
