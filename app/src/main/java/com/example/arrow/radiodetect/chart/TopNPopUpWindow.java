package com.example.arrow.radiodetect.chart;

import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

public class TopNPopUpWindow extends MyPopUpWindow {

    public TopNPopUpWindow(View containView,View rootView, int w, int h, int x, int y) {
        super(containView,rootView, w, h, x, y);
        getPopupWindow().setOutsideTouchable(true);
    }

    @Override
    public void show() {
        getPopupWindow().showAtLocation(getRootView(),Gravity.CENTER,getX(),getY());

        MyLineChart myLineChart = new MyLineChart(getContainView()) ;
        myLineChart.configLineChart();
        //注意以下两个函数是有先后关系的
        myLineChart.updateChart();
        myLineChart.getSignal();
    }
}
