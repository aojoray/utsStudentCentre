package com.mad.utsstudcentre.Controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.utsstudcentre.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubEnqFragment extends Fragment {


    public SubEnqFragment() {
        // Required empty public constructor
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
        return v;
    }

}
