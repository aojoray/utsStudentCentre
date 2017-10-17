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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.utsstudcentre.R;

import butterknife.ButterKnife;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.CODE_ENQ_TYPE;
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
    private FrameLayout mFrame;
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

    private String[] mEnqList;
    private String[] temp;
    private static final String[] EMPTY_ARRAY = new String[0];

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
        View v = inflater.inflate(R.layout.fragment_sub_enq, container, false);
        if(v!=null){
            ButterKnife.bind(this, v);
        }

        mType = getArguments().getInt(TYPE);

        switch(mType){
            case TYPE_SUbJ_ENROL:
                mEnqList = new String[getResources().getStringArray(R.array.sub_enrol_title_01).length + 1];
                temp = getResources().getStringArray(R.array.sub_enrol_title_01);
                System.arraycopy(temp, 0, mEnqList, 0, mEnqList.length - 1);
                mEnqList[mEnqList.length - 1] = "help";
                break;
            case TYPE_STUDY_PLAN:
            case TYPE_UTS_DOC:
            case TYPE_GENERAL:
            case TYPE_TIMETABLE:
            case TYPE_ASSESSMENT:
            case TYPE_GRADUATION:
            case TYPE_IT_SUPPORT:
            default:
                mEnqList = EMPTY_ARRAY;
                temp = EMPTY_ARRAY;
                System.arraycopy(temp, 0, mEnqList, 0, mEnqList.length);
        }

        // Sending list data to GridView
        GridView gridView = (GridView) v.findViewById(R.id.subEnqGv);
        SubenqAdapter enqTypeAdapter = new SubenqAdapter(this.getContext(), mEnqList);
        gridView.setAdapter(enqTypeAdapter);

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

    private class SubenqAdapter extends BaseAdapter {

        private final Context mContext;
        private final String[] mEnquiryTypes;

        private SubenqAdapter(Context context, String[] enquiryTypes) {
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
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final String type = mEnquiryTypes[position];
            if (position == mEnquiryTypes.length - 1) {
                // Inflate Item's layout
                if (convertView == null) {
                    final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                    convertView = layoutInflater.inflate(R.layout.item_enquiry_help, null);
                }

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // instantiate the fragment and commit to open
                        HelpEnqFragment helpEnqFragment = HelpEnqFragment.newInstance(CODE_SUB_ENQ);

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

                    mType = getArguments().getInt(TYPE);

                    switch(mType){
                        case TYPE_SUbJ_ENROL:
                            mItems = getResources().getStringArray(R.array.sub_enrol_title_01);
                            for(int i = 0; i < mItems.length ; i++){

                                // index of the item in the array
                                final int index =  1001 + i;

                                //setOnclickListener
                                typeCv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onOptionClick(v, type, index);
                                    }
                                });
                            }
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
                }


            }

            return convertView;
        }
    }

}
