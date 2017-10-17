package com.mad.utsstudcentre.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private String enq01;
    private String enq02;
    private String enq03;
    private String enq04;
    private String enq05;
    private String enq06;
    private String enq07;
    private String enq08;

    private String[] mEnqList;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_type);
        setupFirebase();

        // Connect Fragment container
        mFrame = (FrameLayout) findViewById(R.id.container);
        mEnqList = new String[8];


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                enq01 = dataSnapshot.child("bookings").child("1").child("bookingName").getValue().toString();
                enq02 = dataSnapshot.child("bookings").child("2").child("bookingName").getValue().toString();
                enq03 = dataSnapshot.child("bookings").child("3").child("bookingName").getValue().toString();
                enq04 = dataSnapshot.child("bookings").child("4").child("bookingName").getValue().toString();
                enq05 = dataSnapshot.child("bookings").child("5").child("bookingName").getValue().toString();
                enq06 = dataSnapshot.child("bookings").child("6").child("bookingName").getValue().toString();
                enq07 = dataSnapshot.child("bookings").child("7").child("bookingName").getValue().toString();
                enq08 = dataSnapshot.child("bookings").child("8").child("bookingName").getValue().toString();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // mEnqList for GridView adapter reads data from String res file = one additional for help
        mEnqList = new String[getResources().getStringArray(R.array.enq_type).length + 1];
        String[] temp = getResources().getStringArray(R.array.enq_type);
        System.arraycopy(temp, 0, mEnqList, 0, mEnqList.length - 1);
        Log.d(TAG, "mEnqList length 2: " + mEnqList.length);
        mEnqList[mEnqList.length - 1] = "help";
        for (String type : mEnqList) {
            Log.d(TAG, "type loop: " + type);
        }

        // Sending list data to GridView
        GridView gridView = (GridView) findViewById(R.id.enqGv);
        EnqTypeAdapter enqTypeAdapter = new EnqTypeAdapter(this, mEnqList);
        gridView.setAdapter(enqTypeAdapter);
    }

    /**
     * Overridden OnBackPressed
     * changes the visibility of FrameLayout for Fragments
     */
    @Override
    public void onBackPressed() {

        Log.d(TAG, "COUNT: " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            mFrame.setVisibility(View.GONE);
        }
        super.onBackPressed();
    }

    /**
     * If the booking is confirmed, this callback message will finish the intent and pass the reference number to MainActivity
     *
     * @param dlg
     */
    @Override
    public void onOkayClick(DialogFragment dlg) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void setupFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mSnapshot = FirebaseDatabase.getInstance().getReference().getVal

    }

    /**
     * Innerclass EnqTypeAdapter for populate enquiry type items
     */
    private class EnqTypeAdapter extends BaseAdapter {

        private final Context mContext;
        private final String[] mEnquiryTypes;

        private EnqTypeAdapter(Context context, String[] enquiryTypes) {
            Log.d(TAG, "Adapter!!");
            this.mContext = context;
            this.mEnquiryTypes = enquiryTypes;
        }

        @Override
        public int getCount() {
            return mEnquiryTypes.length;
        }

        @Override
        public Object getItem(int position) {
            return mEnquiryTypes[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Binds Data
            final String type = mEnquiryTypes[position];
            Log.d(TAG, "Type == " + type);

            Log.d(TAG, "position: " + position);
            Log.d(TAG, "mEnquiryTypes: " + mEnquiryTypes[position]);
            // Link to the view and Set title
            if (position == mEnquiryTypes.length - 1) {
                // Inflate Item's layout
                if (convertView == null) {
                    final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                    convertView = layoutInflater.inflate(R.layout.item_enquiry_help, null);
                }

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // let FrameLayout for Fragment visible
                        mFrame.setVisibility(View.VISIBLE);
                        // instantiate the fragment and commit to open
                        HelpEnqFragment helpEnqFragment = HelpEnqFragment.newInstance(CODE_ENQ_TYPE);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().addToBackStack(null).setTransition(TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.container, helpEnqFragment, HELP_ENQUIRY_FRAGMENT).commit();
                    }
                });
            } else {
                // Inflate Item's layout
                if (convertView == null) {
                     LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                    convertView = layoutInflater.inflate(R.layout.item_enquiry_type, null);
                    CardView typeCv = (CardView) convertView.findViewById(R.id.enqItemCv);
                    TextView typeNameTv = (TextView) convertView.findViewById(R.id.typeNameTv);
                    typeNameTv.setText(type);

                    typeCv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                         let FrameLayout for Fragment visible
                            mFrame.setVisibility(View.VISIBLE);
                            // instantiate the fragment and commit to open
                            SubEnqFragment subEnqFragment = SubEnqFragment.newInstance(position+1);
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().addToBackStack(null).setTransition(TRANSIT_FRAGMENT_FADE)
                                    .replace(R.id.container, subEnqFragment, SUB_ENQUIRY_FRAGMENT).commit();
                        }
                    });
                }


            }


            return convertView;
        }
    }

}
