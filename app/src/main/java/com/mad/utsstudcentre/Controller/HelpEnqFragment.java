package com.mad.utsstudcentre.Controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.utsstudcentre.R;

import butterknife.ButterKnife;

import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.CODE_ENQ_TYPE;
import static com.mad.utsstudcentre.Controller.SubEnqFragment.CODE_SUB_ENQ;

/**
 * HelpEnqFragment is providing Help page to the user.
 * This can be called by both EnquiryTypeActivity and SubEnqFragment
 * Depending on the caller's code, this will generate the view dynamically
 */
public class HelpEnqFragment extends Fragment {

    private static final String CODE = "Caller Code";
    private static final String TYPE = "SubEnq Type";
    private TextView mHelpTitleTv01;
    private TextView mHelpTv01;
    private TextView mHelpTitleTv02;
    private TextView mHelpTv02;
    private TextView mHelpTitleTv03;
    private TextView mHelpTv03;
    private TextView mHelpTitleTv04;
    private TextView mHelpTv04;
    private TextView mHelpTitleTv05;
    private TextView mHelpTv05;
    private TextView mHelpTitleTv06;
    private TextView mHelpTv06;
    private TextView mHelpTitleTv07;
    private TextView mHelpTv07;
    private TextView mHelpTitleTv08;
    private TextView mHelpTv08;
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
    public static HelpEnqFragment newInstance(String code, int type) {
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
        switch(code){
            case CODE_ENQ_TYPE:  //call from EnquiryActivity
//                mHelpTitleTv01= (TextView) v.findViewById(R.id.enq_help_title_1);
//                mHelpTv01 = (TextView) v.findViewById(R.id.enq_help_tv_1);
//                mHelpTitleTv02= (TextView) v.findViewById(R.id.enq_help_title_2);
//                mHelpTv02 = (TextView) v.findViewById(R.id.enq_help_tv_2);
//                mHelpTitleTv03= (TextView) v.findViewById(R.id.enq_help_title_3);
//                mHelpTv03 = (TextView) v.findViewById(R.id.enq_help_tv_3);
//                mHelpTitleTv04= (TextView) v.findViewById(R.id.enq_help_title_4);
//                mHelpTv04 = (TextView) v.findViewById(R.id.enq_help_tv_4);
//                mHelpTitleTv05 = (TextView) v.findViewById(R.id.enq_help_title_5);
//                mHelpTv05 = (TextView) v.findViewById(R.id.enq_help_tv_5);
//                mHelpTitleTv06= (TextView) v.findViewById(R.id.enq_help_title_6);
//                mHelpTv06 = (TextView) v.findViewById(R.id.enq_help_tv_6);
//                mHelpTitleTv07= (TextView) v.findViewById(R.id.enq_help_title_7);
//                mHelpTv07 = (TextView) v.findViewById(R.id.enq_help_tv_7);
//                mHelpTitleTv08= (TextView) v.findViewById(R.id.enq_help_title_8);
//                mHelpTv08 = (TextView) v.findViewById(R.id.enq_help_tv_8);

                mHelpContentLayout.setVisibility(View.VISIBLE);

                break;
            case CODE_SUB_ENQ:   //call from SubEnquiryFragment
                        String[] items = getResources().getStringArray(R.array.sub_enrol_title_01);
                        String[] contents = getResources().getStringArray(R.array.sub_enrol_text_01);
                        for(int i = 0; i < items.length ; i++) {
                            // Dynamically generated views
                            TextView titleTv = new TextView(getActivity());
                            TextView contentTv = new TextView(getActivity());

                            // set Text and Style
                            titleTv.setText(items[i]);
                            titleTv.setTextAppearance(getContext(), R.style.Help_title);
                            contentTv.setText(contents[i]);
                            contentTv.setTextAppearance(getContext(), R.style.Help_content);

                            // Add views to the layout
                            mHelpLayout.addView(titleTv);
                            mHelpLayout.addView(contentTv);
                        }
        }

        return v;
    }
}
