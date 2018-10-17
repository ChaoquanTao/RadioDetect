package com.example.arrow.radiodetect.chart;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.arrow.radiodetect.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyBarChart {
    private Context context ;
    private BarChart barChart ;
    private Handler barChartHandler ;

    public MyBarChart(Context context) {
        this.context = context;
        this.barChart = ((Activity)context).findViewById(R.id.barchart);
    }

    public void configBarChart(){

    }

    public void getSignal(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                List<BarEntry> barEntries = new ArrayList() ;
                for(int i=0; i<10; i++){
                    barEntries.add(new BarEntry(i, (float) (Math.random()*100))) ;
                }
                Message message = new Message();
                message.obj = barEntries ;
                barChartHandler.sendMessage(message) ;

            }
        },0,5000);
    }

    public void updateChart(){
        barChartHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                BarDataSet barDataSet = new BarDataSet((ArrayList<BarEntry>)msg.obj,"barchart") ;
                BarData barData = new BarData(barDataSet) ;
                barChart.setData(barData);
                barChart.invalidate();
            }
        } ;
    }


}
