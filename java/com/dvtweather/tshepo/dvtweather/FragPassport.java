package com.dvtweather.tshepo.dvtweather;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Tshepo on 07/08/2017.
 */

public class FragPassport extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_passport, container, false);

        return view;
    }


    /**
     * fragment variable declaration.
     */
    private LinearLayout relativeLayout;
    private View view;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        relativeLayout = (LinearLayout) view.findViewById(R.id.frag_passport);
        relativeLayout.setBackgroundColor(Color.WHITE);


        //getActivity().setTitle("Menu 1");

    }
}


