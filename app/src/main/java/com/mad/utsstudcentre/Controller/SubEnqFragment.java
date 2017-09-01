package com.mad.utsstudcentre.Controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.mad.utsstudcentre.R;

import butterknife.ButterKnife;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
import static com.mad.utsstudcentre.Controller.EnquiryTypeActivity.HELP_ENQUIRY_FRAGMENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubEnqFragment extends Fragment {

    public static final String CODE_SUB_ENQ = "SubEnqFragment";
    private static final String TYPE = "Enq_Type";
    private Button mEnqBtn01;
    private Button mEnqBtn02;
    private Button mEnqBtn03;
    private Button mEnqBtn04;
    private Button mEnqBtn05;
    private Button mEnqBtn06;
    private Button mEnqBtn07;
    private Button mEnqBtn08;
    private ImageButton mHelpBtn;


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

        return v;
    }
}
