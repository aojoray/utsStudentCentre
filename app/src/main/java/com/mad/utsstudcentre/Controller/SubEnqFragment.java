package com.mad.utsstudcentre.Controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mad.utsstudcentre.R;

import butterknife.ButterKnife;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.*;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.HELP_ENQUIRY_FRAGMENT;

/**
 * SubEnqFragment is loaded when the parent enquiry has subtypes.
 * This class will generate options dynamically.
 */
public class SubEnqFragment extends Fragment {

    public static final String CODE_SUB_ENQ = "SubEnqFragment";
    private static final String TYPE = "Enq_Type";
    private ImageButton mHelpBtn;
    private LinearLayout mSubEnqBtnLayout;
    private String[] mItems;
    private int mType;


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

        // Connects help button and set OnClickListener
        mHelpBtn= (ImageButton) v.findViewById(R.id.sub_enqHelpBtn);
        mHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // instantiate the fragment and commit to open
                HelpEnqFragment helpEnqFragment = HelpEnqFragment.newInstance(CODE_SUB_ENQ, getArguments().getInt(TYPE));

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(null).setTransition(TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.container, helpEnqFragment, HELP_ENQUIRY_FRAGMENT).commit();
            }
        });

        // Populate Buttons dynamically
        mSubEnqBtnLayout= (LinearLayout) v.findViewById(R.id.sub_enq_btn_layout);
        // type of Parent Enquiry
        mType = getArguments().getInt(TYPE);

        switch(mType){
            case TYPE_SUbJ_ENROL:
                mItems = getResources().getStringArray(R.array.sub_enrol_title_01);
                for(String item : mItems){
                    // Dynamically generated views
                    final Button subEnqBtn = (Button) inflater.inflate(R.layout.attribute_enq_btn, null);

                    // set Text and Style
                    subEnqBtn.setText(item);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    );
                    params.setMargins(20, 20, 20, 20);
                    subEnqBtn.setLayoutParams(params);

                    //setOnclickListener
                    subEnqBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onOptionClick(v, subEnqBtn.getText());
                        }
                    });

                    // Add views to the layout
                    mSubEnqBtnLayout.addView(subEnqBtn);
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

        return v;
    }

    /**
     * Handles the onclick event of each sub-enquiries.
     * @param v
     * @param text
     */
    private void onOptionClick(View v, CharSequence text) {
        Toast.makeText(getContext(), "selected: " + text, Toast.LENGTH_SHORT).show();
    }

}
