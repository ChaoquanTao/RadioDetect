package com.example.arrow.radiodetect.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arrow.radiodetect.R;

public class ChartFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chart_fragment_detail,container,false) ;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("==========","fragment paused+++++") ;
    }
}
