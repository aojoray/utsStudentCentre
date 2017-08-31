package com.mad.utsstudcentre.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mad.utsstudcentre.R;

import org.w3c.dom.Text;

import static com.mad.utsstudcentre.Controller.LoginActivity.DATABDL;
import static com.mad.utsstudcentre.Controller.LoginActivity.USERNAME;

/**
 * Created by HP on 24/08/2017.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_TAG";
    private static final int BOOKING_REQUEST = 1001;
    private String mUserName;
    private TextView mUserNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = getIntent().getStringExtra(USERNAME);
        mUserNameTv = (TextView) findViewById(R.id.userNameTv);
        mUserNameTv.setText(" "+mUserName);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Log.d(TAG, "RESULT OKAY! ");
        }
    }
}
