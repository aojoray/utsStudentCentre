package com.mad.utsstudcentre.Controller;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.utsstudcentre.R;

import butterknife.ButterKnife;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.HELP_ENQUIRY_FRAGMENT;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_ASSESSMENT;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_GENERAL;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_GRADUATION;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_IT_SUPPORT;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_STUDY_PLAN;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_SUbJ_ENROL;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_TIMETABLE;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_UTS_DOC;

/**
 * SubEnqFragment is loaded when the parent enquiry has subtypes.
 * This class will generate options dynamically.
 */
public class SubEnqFragment extends Fragment {

    public static final String CODE_SUB_ENQ = "SubEnqFragment";
    private static final String TYPE = "Enq_Type";
    private static final String CENTRE_FRAGMENT = "Centre fragment";
    private static final String SUB_TYPE = "sub-type code";
    private static final String TAG = "SubEnqFragment_TAG";
    private ImageButton mHelpBtn;
    private LinearLayout mSubEnqBtnLayout;
    private String[] mItems;
    private int mType;
    public static final int SUB_SUbJ_ENR_ASSISTANCE = 1001;
    public static final int SUB_SUbJ_FULL_SUBJ = 1002;
    public static final int SUB_SUbJ_PRE_REQS = 1003;
//    public static final int TYPE_STUDY_PLAN = 2;
//    public static final int TYPE_UTS_DOC = 3;
//    public static final int TYPE_GENERAL = 4;
//    public static final int TYPE_TIMETABLE = 5;
//    public static final int TYPE_ASSESSMENT = 6;
//    public static final int TYPE_GRADUATION = 7;
//    public static final int TYPE_IT_SUPPORT = 8;



    public SubEnqFragment() {
        // Required empty public constructor
    }


    /**
     * New instance of fragment.
     * Type of the parent enquiry will be passed as an argument used for building UI
     * @param type
     * @return
     */
    public static SubEnqFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        SubEnqFragment fragment = new SubEnqFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Creating / Binding to the UI elements
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sub_enq, container, false);
        View v = inflater.inflate(R.layout.fragment_sub_enq, container, false);
        if(v!=null){
            ButterKnife.bind(this, v);
        }
//
//        // Connects help button and set OnClickListener
//        mHelpBtn= (ImageButton) v.findViewById(R.id.sub_enqHelpBtn);
//        mHelpBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // instantiate the fragment and commit to open
//                HelpEnqFragment helpEnqFragment = HelpEnqFragment.newInstance(CODE_SUB_ENQ, getArguments().getInt(TYPE));
//
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().addToBackStack(null).setTransition(TRANSIT_FRAGMENT_FADE)
//                        .replace(R.id.container, helpEnqFragment, HELP_ENQUIRY_FRAGMENT).commit();
//            }
//        });
//
//        // layout where the sub-enquiry types will be placed
//        mSubEnqBtnLayout= (LinearLayout) v.findViewById(R.id.sub_enq_btn_layout);

        // type of Parent Enquiry
        mType = getArguments().getInt(TYPE);

        // mEnqList for GridView adapter reads data from String res file = one additional for help
        mItems = new String[getResources().getStringArray(R.array.enq_type).length + 1];
        String[] temp = getResources().getStringArray(R.array.enq_type);
        System.arraycopy(temp, 0, mItems, 0, mItems.length - 1);
        Log.d(TAG, "mEnqList length 2: " + mItems.length);
        mItems[mItems.length - 1] = "help";

        // Sending list data to GridView
        GridView gridView = (GridView) v.findViewById(R.id.enqGv);
//        GridLayout gridLayout =  v.fi
        SubEnqTypeAdapter subenqTypeAdapter = new SubEnqTypeAdapter(getActivity(), mItems);
        gridView.setAdapter(subenqTypeAdapter);


        // Populate Sub-enquiry type Buttons dynamically
        switch(mType){
            case TYPE_SUbJ_ENROL:
                mItems = getResources().getStringArray(R.array.sub_enrol_title_01);
//                for(int i = 0; i < mItems.length ; i++){
//
//                    // Dynamically generated views
//                    final Button subEnqBtn = (Button) inflater.inflate(R.layout.attribute_enq_btn, null);
//
//                    // set Text and Style
//                    subEnqBtn.setText(mItems[i]);
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT
//                    );
//                    params.setMargins(20, 20, 20, 20);
//                    params.height = 480;
//                    subEnqBtn.setLayoutParams(params);
//
//                    // index of the item in the array
//                    final int index =  1001 + i;
//
//                    //setOnclickListener
//                    subEnqBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            onOptionClick(v, subEnqBtn.getText(), index);
//                        }
//                    });
//
//                    // Add views to the layout
//                    mSubEnqBtnLayout.addView(subEnqBtn);
//                }
                break;
            case TYPE_STUDY_PLAN:
                break;
            case TYPE_UTS_DOC:
                break;
            case TYPE_GENERAL:
                break;
            case TYPE_TIMETABLE:
                break;
            case TYPE_ASSESSMENT:
                break;
            case TYPE_GRADUATION:
                break;
            case TYPE_IT_SUPPORT:
                break;
            default:
                break;
        }

        return v;
    }

    /**
     * Handles the onclick event of each sub-enquiries.
     * @param v
     * @param type
     */
    private void onOptionClick(View v, CharSequence type, int index) {
        Toast.makeText(getContext(), "selected: " + type, Toast.LENGTH_SHORT).show();
        // instantiate the fragment and commit to open
        CentreFragment subEnqFragment = CentreFragment.newInstance(type, index);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).setTransition(TRANSIT_FRAGMENT_FADE)
                .replace(R.id.container, subEnqFragment, CENTRE_FRAGMENT).commit();
    }
    /**
     * Innerclass EnqTypeAdapter for populate enquiry type items
     */
    private class SubEnqTypeAdapter extends BaseAdapter {

        private final Context mContext;
        private final String[] mSubEnquiryTypes;

        private SubEnqTypeAdapter(Context context, String[] enquiryTypes) {
            Log.d(TAG, "Adapter!!");
            this.mContext = context;
            this.mSubEnquiryTypes = enquiryTypes;
        }

        @Override
        public int getCount() {
            return mSubEnquiryTypes.length;
        }

        @Override
        public Object getItem(int position) {
            return mSubEnquiryTypes[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Binds Data
            final String type = mSubEnquiryTypes[position];
            Log.d(TAG, "Type == " + type);

            Log.d(TAG, "position: " + position);
            Log.d(TAG, "mSubEnquiryTypes: " + mSubEnquiryTypes[position]);
            // Link to the view and Set title
            if (position == mSubEnquiryTypes.length - 1) {
                // Inflate Item's layout
                if (convertView == null) {
                    final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                    convertView = layoutInflater.inflate(R.layout.item_enquiry_help, null);
                }

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // let FrameLayout for Fragment visible
//                        mFrame.setVisibility(View.VISIBLE);
                        // instantiate the fragment and commit to open
                        HelpEnqFragment helpEnqFragment = HelpEnqFragment.newInstance(CODE_SUB_ENQ, getArguments().getInt(TYPE));

                        FragmentManager fragmentManager = getFragmentManager();
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

                    // index of the item in the array
//                    final int index =  1001 + i;
                    typeCv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //setOnclickListener
                            onOptionClick(v, type, position);
                        }
                    });
                }
            }
            return convertView;
        }
    }

}
