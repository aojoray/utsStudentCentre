package com.mad.utsstudcentre.Controller;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mad.utsstudcentre.Dialogue.ConfirmDialogue;
import com.mad.utsstudcentre.Model.Booking;
import com.mad.utsstudcentre.Model.StudentCentre;
import com.mad.utsstudcentre.R;

import java.util.Random;

import butterknife.ButterKnife;

import static com.mad.utsstudcentre.Controller.MainActivity.getCentre;
import static com.mad.utsstudcentre.Model.StudentCentre.CENTRE_01;
import static com.mad.utsstudcentre.Model.StudentCentre.CENTRE_01_ID;

/**
 * CentreFragment is loaded when user selects the Enquiry/sub-enquiry type.
 * This will let user knows the current wait list for each centre to help user to decide the
 * centre they wants to visit.
 */
public class CentreFragment extends Fragment {


    public static final String FINAL_TYPE = "Final Type";
    private static final String TYPE_INDEX = "Final type index";
    public static final String INDEX_TYPE = "IndexType";
    public static final String WAITING_05 = "waitingPeople05";
    public static final String WAITING_10 = "waitingPeople10";
    public static final String REF_NUMBER = "Reference Number";
    public static final String EST_TIME = "Estimated time";
    public static final String CENTRE_TYPE = "Centre type";
    private static final String TAG = "CentreFragment_TAG";
    private Booking mBooking;
    private String mFinalType;
    private int mFinalTypeIndex;
    private String mRefNumber;
    int mEstTime01;
    int mEstTime02;

    // UI elements
    private LinearLayout mCentre01Layout;
    private LinearLayout mCentre02Layout;
    private TextView mFinalTypeTv;
    private TextView mEst_01Tv;
    private TextView mWait_01Tv;
    private TextView mEst_02Tv;
    private TextView mWait_02Tv;

    private DatabaseReference mDatabase;

    public CentreFragment() {
        // Required empty public constructor
    }


    public static CentreFragment newInstance(CharSequence finalType, int index) {
        Bundle args = new Bundle();
        args.putString(FINAL_TYPE, String.valueOf(finalType));
        args.putInt(TYPE_INDEX, index);
        CentreFragment fragment = new CentreFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_centre, container, false);
        if (v != null) {
            ButterKnife.bind(this, v);
        }
        // setting the final type and its index
        mFinalType = getArguments().getString(FINAL_TYPE);
        mFinalTypeIndex = getArguments().getInt(TYPE_INDEX);

        // Binding the UI elements with controller
        mFinalTypeTv = (TextView) v.findViewById(R.id.final_type_tv);
        mEst_01Tv = (TextView) v.findViewById(R.id.est_bd5_tv);
        mEst_02Tv = (TextView) v.findViewById(R.id.est_bd10_tv);
        mWait_01Tv = (TextView) v.findViewById(R.id.waiting_bd5_tv);
        mWait_02Tv = (TextView) v.findViewById(R.id.waiting_bd10_tv);
        initialiseData();

        mFinalTypeTv.setText(mFinalType);

        mCentre01Layout = (LinearLayout) v.findViewById(R.id.centre_01_layout);
        mCentre02Layout = (LinearLayout) v.findViewById(R.id.centre_02_layout);

        // Set OnClickListener to the layout (used as buttons)
        mCentre01Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialogue cfmDialogue = new ConfirmDialogue();

                StudentCentre centre = getCentre();

                centre.setCenterId(CENTRE_01_ID);
                centre.setCenterName(CENTRE_01);
                centre.setEstTime(mEstTime01);
                mBooking.setEnqType(mFinalType);
                mBooking.setCentre(centre);

                Bundle args = new Bundle();
                args.putString(INDEX_TYPE, "" + mFinalTypeIndex);
                args.putString(WAITING_05, "waitingPeople05");
                cfmDialogue.setArguments(args);
                cfmDialogue.show(getFragmentManager(), "ConfirmDialogue");
            }
        });

        mCentre02Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }


    /**
     * initialiseData set the data for each Student Centre.
     * As we don't have access to the centre's database, the information is generated by random object.
     * The estimated time is calculated out of waiting list. (waiting number * 5 min)
     */
    private int wait01;
    private int wait02;
    private void initialiseData() {

        mDatabase = FirebaseDatabase.getInstance().getReference();


//        int wait01 = random.nextInt(20);
//        int wait02 = random.nextInt(20);
        final String index = "" + mFinalTypeIndex;
        Random random = new Random();
        //TODO: Replace after testing
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wait01 = dataSnapshot.child("bookings").child(index)
                        .child("waitingPeople05").getValue(Integer.class);
                wait02 = dataSnapshot.child("bookings").child(index)
                        .child("waitingPeople10").getValue(Integer.class);

                mEstTime01 = wait01 * 5;
                mEstTime02 = wait02 * 5;


                String est01 = "";
                String est02 = "";

                if (mEstTime01 > 60) {
                    est01 = mEstTime01 / 60 + " hour " + mEstTime01 % 60 + " min";
                } else {
                    est01 = mEstTime01 + " min";
                }

                if (mEstTime02 > 60) {
                    est02 = mEstTime02 / 60 + " hour " + mEstTime02 % 60 + " min";
                } else {
                    est02 = mEstTime02 + " min";
                }

                mEst_01Tv.setText(est01);
                mEst_02Tv.setText(est02);
                mWait_01Tv.setText(wait01 + " people");
                mWait_02Tv.setText(wait02 + " people");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        mEstTime01 = wait01 * 5; // 5 minutes average time per booking


    }

    /**
     * setRefNumber after RefNumberAsyncTask generate the reference number
     * @param refNumber
     */
    public void setRefNumber(String refNumber) {
        this.mRefNumber = refNumber;
    }

    /**
     * Overriden onStart method initialise the models and execute AsyncTask to generate Booking_old reference number
     */
    @Override
    public void onStart() {
        mBooking = MainActivity.getBooking();
        RefNumberAsyncTask async = new RefNumberAsyncTask();
        async.execute();
        super.onStart();
    }

    /**
     * generate random number for Reference number according to the enquiry type
     */
    private class RefNumberAsyncTask extends AsyncTask<Nullable, String, String> {

        @Override
        protected String doInBackground(Nullable... params) {
            String refNum = "";
            int num;
            Random random = new Random();

            Log.d(TAG, "Centre Async");
            switch (mFinalTypeIndex) {
                case 1001:
                case 1002:
                case 1003:
                    refNum = "D";
                    num = random.nextInt(9999) + 1;
                    // add leading 0s to the ref.number
                    if (num < 10) {
                        refNum += ("000" + num);
                    } else if (num < 100) {
                        refNum += ("00" + num);
                    } else if (num < 1000) {
                        refNum += ("0" + num);
                    }else {
                        refNum += num;
                    }
                    break;
                default:
                    break;
            }

            mBooking.setReference(refNum);

            return refNum;
        }

        @Override
        protected void onPostExecute(String refNum) {
            setRefNumber(refNum);
            super.onPostExecute(refNum);
        }
    }

}
