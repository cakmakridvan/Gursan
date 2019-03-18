package com.rotamobile.gursan.ui.bottom_navigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rotamobile.gursan.R;

@SuppressLint("ValidFragment")
public class FlagMessages extends Fragment {

    public FlagMessages() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flag_mesaj, container, false);
    }
}
