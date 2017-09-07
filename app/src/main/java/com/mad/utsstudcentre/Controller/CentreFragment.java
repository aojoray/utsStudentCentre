package com.mad.utsstudcentre.Controller;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.utsstudcentre.Dialogue.ConfirmDialogue;
import com.mad.utsstudcentre.R;

import butterknife.ButterKnife;

/**
 * CentreFragment is loaded when user selects the Enquiry/sub-enquiry type.
 * This will let user knows the current wait list for each centre to help user to decide the
 * centre they wants to visit.
 */
public class CentreFragment extends Fragment {


    private static final String FINAL_TYPE = "Final Type";
    public static final int CENTRE_01 = 10;
    public static final int CENTRE_02 = 20;
    private String mFinalType;

    // UI elements
    private LinearLayout mCentre01Layout;
    private LinearLayout mCentre02Layout;
    private TextView mFinalTypeTv;
    private TextView mEst_01Tv;
    private TextView mWait_01Tv;
    private TextView mEst_02Tv;
    private TextView mWait_02Tv;

    public CentreFragment() {
        // Required empty public constructor
    }


    public static CentreFragment newInstance(CharSequence finalType) {
        Bundle args = new Bundle();
        args.putString(FINAL_TYPE, String.valueOf(finalType));
        CentreFragment fragment = new CentreFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_centre, container, false);
        if(v!=null){
            ButterKnife.bind(this, v);
        }
        // setting the final type
        mFinalType=getArguments().getString(FINAL_TYPE);

        // Binding the UI elements with controller
        mFinalTypeTv = (TextView) v.findViewById(R.id.final_type_tv);
        mEst_01Tv = (TextView) v.findViewById(R.id.est_bd5_tv);
        mEst_02Tv = (TextView) v.findViewById(R.id.est_bd10_tv);
        mWait_01Tv = (TextView) v.findViewById(R.id.waiting_bd5_tv);
        mWait_02Tv = (TextView) v.findViewById(R.id.waiting_bd10_tv);

        //TODO: setText method required --> need a method to generate number for either est.time or wait
        mFinalTypeTv.setText(mFinalType);

        mCentre01Layout = (LinearLayout) v.findViewById(R.id.centre_01_layout);
        mCentre02Layout = (LinearLayout) v.findViewById(R.id.centre_02_layout);

        // Set OnClickListener to the layout (used as buttons)
        mCentre01Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialogue cfmDialogue = new ConfirmDialogue();
                cfmDialogue.show(getFragmentManager(), "ConfirmDialogue");

                //TODO: need to call a method / Asynctask etc. to generate the ref. number (need to pass Enq. type)
            }
        });

        mCentre02Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }

    @Override
    public void onStart() {
        CentreInfoGenerator async = new CentreInfoGenerator(mFinalType);
        super.onStart();
    }

    /**
     * generate random number for Ref. number and Waiting list of each Student Centre
     */
    private class CentreInfoGenerator extends AsyncTask<String, String, String> {

        private String mType;


        public CentreInfoGenerator(String type){
            mType = type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
