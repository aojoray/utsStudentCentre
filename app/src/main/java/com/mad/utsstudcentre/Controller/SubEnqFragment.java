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

import com.mad.utsstudcentre.R;

import butterknife.ButterKnife;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.*;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.HELP_ENQUIRY_FRAGMENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubEnqFragment extends Fragment {

    public static final String CODE_SUB_ENQ = "SubEnqFragment";
    private static final String TYPE = "Enq_Type";
    private ImageButton mHelpBtn;
    private LinearLayout mSubEnqBtnLayout;
    private String[] mItems;


    public SubEnqFragment() {
        // Required empty public constructor
    }


    public static SubEnqFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        SubEnqFragment fragment = new SubEnqFragment();
        fragment.setArguments(args);

        return fragment;
    }

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
        int type = getArguments().getInt(TYPE);

        switch(type){
            case TYPE_SUbJ_ENROL:
                mItems = getResources().getStringArray(R.array.sub_q_title_01);
                for(String item : mItems){
                    // Dynamically generated views
                    Button subEnqBtn = (Button) inflater.inflate(R.layout.attribute_enq_btn, null);

                    // set Text and Style
                    subEnqBtn.setText(item);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    );
                    params.setMargins(20, 20, 20, 20);
                    subEnqBtn.setLayoutParams(params);

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
}
