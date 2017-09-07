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
        mHelpTitleTv01= (TextView) v.findViewById(R.id.enq_help_title_1);
        mHelpTv01 = (TextView) v.findViewById(R.id.enq_help_tv_1);

        // check the caller then dynamically generate view accordingly
        String code = getArguments().getString(CODE);
        switch(code){
            case CODE_ENQ_TYPE:  //call from EnquiryActivity
                mHelpContentLayout.setVisibility(View.VISIBLE);
                mHelpTitleTv01.setText(getString(R.string.general_enquiry));
                mHelpTv01.setText(getString(R.string.help_general));
                break;
            case CODE_SUB_ENQ:   //call from SubEnquiryFragment
                int type = getArguments().getInt(TYPE);
                switch(type){
                    case 1:
                        String[] items = getResources().getStringArray(R.array.sub_enrol_title_01);
                        String[] contents = getResources().getStringArray(R.array.sub_enrol_text_01);
                        for(int i = 0; i < items.length ; i++){
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
                        break;
                    default:
                        break;
                }
                break;
        }

        return v;
    }
}
