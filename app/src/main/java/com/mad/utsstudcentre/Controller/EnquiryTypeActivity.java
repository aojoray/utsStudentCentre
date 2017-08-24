package com.mad.utsstudcentre.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_type);

        mEnqBtn01 = (Button) findViewById(R.id.enqBtn01);
        mEnqBtn02 = (Button) findViewById(R.id.enqBtn02);
        mEnqBtn03 = (Button) findViewById(R.id.enqBtn03);
        mEnqBtn04 = (Button) findViewById(R.id.enqBtn04);
        mEnqBtn05 = (Button) findViewById(R.id.enqBtn05);
        mEnqBtn06 = (Button) findViewById(R.id.enqBtn06);
        mEnqBtn07 = (Button) findViewById(R.id.enqBtn07);
        mEnqBtn08 = (Button) findViewById(R.id.enqBtn08);


    }
}
