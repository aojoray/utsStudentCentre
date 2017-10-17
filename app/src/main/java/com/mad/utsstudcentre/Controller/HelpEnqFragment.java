package com.mad.utsstudcentre.Controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.utsstudcentre.R;

import butterknife.ButterKnife;

import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.CODE_ENQ_TYPE;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_ASSESSMENT;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_GENERAL;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_GRADUATION;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_IT_SUPPORT;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_STUDY_PLAN;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_SUbJ_ENROL;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_TIMETABLE;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.TYPE_UTS_DOC;

/**
 * HelpEnqFragment is providing Help page to the user.
 * This can be called by both EnquiryTypeActivity and SubEnqFragment
 * Depending on the caller's code, this will generate the view dynamically
 */
public class HelpEnqFragment extends Fragment {

    private static final String CODE = "Caller Code";
    private static final String TYPE = "SubEnq Type";
    private static final String TAG = "Help_TAG";
    private String[] mItems;
    private String[] mContents;
    private LinearLayout mHelpLayout;
    private LinearLayout mHelpContentLayout;

    public HelpEnqFragment() {
        // Required empty public constructor
    }


    /**
     * New instance of fragment from EnquiryTypeActivity
     * @param code
     * @return
     */
    public static HelpEnqFragment newInstance(String code) {
        Bundle args = new Bundle();
        args.putString(CODE, code);
        HelpEnqFragment fragment = new HelpEnqFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * New instance of fragment from SubEnqFragment
     * @param code
     * @param type
     * @return
     */
    public static HelpEnqFragment newInstance(int type, String code) {
        Bundle args = new Bundle();
        args.putString(CODE, code);
        args.putInt(TYPE, type);
        HelpEnqFragment fragment = new HelpEnqFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creating / binding to the view dynamically depending on the caller.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_help_enq, container, false);
        if (v != null) {
            ButterKnife.bind(this, v);
        }

        mHelpLayout = (LinearLayout) v.findViewById(R.id.help_layout);
        mHelpContentLayout = (LinearLayout) v.findViewById(R.id.enq_help_content_layout);

        // check the caller then dynamically generate view accordingly
        String code = getArguments().getString(CODE);
        if (code==CODE_ENQ_TYPE){ //call from EnquiryActivity
            mHelpContentLayout.setVisibility(View.VISIBLE);
        } else { //call from SubEnquiryFragment
            int type = getArguments().getInt(TYPE);
            switch(type){
                    case TYPE_SUbJ_ENROL:
                        mItems = getResources().getStringArray(R.array.sub_title_01);
                        mContents = getResources().getStringArray(R.array.sub_text_01);
                        break;
                    case TYPE_STUDY_PLAN:
                        mItems = getResources().getStringArray(R.array.sub_title_02);
                        mContents = getResources().getStringArray(R.array.sub_text_02);
                        break;
                    case TYPE_UTS_DOC:
                        mItems = getResources().getStringArray(R.array.sub_title_03);
                        mContents = getResources().getStringArray(R.array.sub_text_03);
                        break;
                    case TYPE_GENERAL:
                        mItems = getResources().getStringArray(R.array.sub_title_04);
                        mContents = getResources().getStringArray(R.array.sub_text_04);
                        break;
                    case TYPE_TIMETABLE:
                        mItems = getResources().getStringArray(R.array.sub_title_05);
                        mContents = getResources().getStringArray(R.array.sub_text_05);
                        break;
                    case TYPE_ASSESSMENT:
                        mItems = getResources().getStringArray(R.array.sub_title_06);
                        mContents = getResources().getStringArray(R.array.sub_text_06);
                        break;
                    case TYPE_GRADUATION:
                        mItems = getResources().getStringArray(R.array.sub_title_07);
                        mContents = getResources().getStringArray(R.array.sub_text_07);
                        break;
                    case TYPE_IT_SUPPORT:
                        mItems = getResources().getStringArray(R.array.sub_title_08);
                        mContents = getResources().getStringArray(R.array.sub_text_08);
                        Log.d(TAG, "mitem" + mItems[0]);
                        break;
                    default:
                        break;
            }

            for(int i = 0; i < mItems.length ; i++) {
                // Dynamically generated views
                TextView titleTv = new TextView(getActivity());
                TextView contentTv = new TextView(getActivity());

                // set Text and Style
                titleTv.setText(mItems[i]);
                titleTv.setTextAppearance(getContext(), R.style.Help_title);
                contentTv.setText(mContents[i]);
                contentTv.setTextAppearance(getContext(), R.style.Help_content);

                // Add views to the layout
                mHelpLayout.addView(titleTv);
                mHelpLayout.addView(contentTv);
            }
        }



        return v;
    }
}
