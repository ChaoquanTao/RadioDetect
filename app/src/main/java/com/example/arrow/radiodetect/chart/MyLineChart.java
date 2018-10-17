package com.example.arrow.radiodetect.chart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.arrow.radiodetect.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.Timer;
import java.util.TimerTask;

public class MyLineChart{
    private View context ;
    private LineChart lineChart ;
    private LineData lineData ;
    private XAxis xAxis ;
    private YAxis leftAxis ;
    private YAxis rightAxis ;
    private Handler lineChartHandler ;

    public MyLineChart(View context) {
        this.context = context ;
        lineChart = (context).findViewById(R.id.linechart) ;
        Log.d("===", String.valueOf(lineChart == null)) ;
        xAxis = lineChart.getXAxis() ;
        leftAxis = lineChart.getAxisLeft() ;
        rightAxis = lineChart.getAxisRight() ;
        lineData = new LineData() ;


    }

    public void configLineChart(){
        //可点击
        lineChart.setTouchEnabled(true);

        //可拖拽
        lineChart.setDragEnabled(false);

        //可缩放
        lineChart.setScaleEnabled(false);

        //背景网格线
        lineChart.setDrawGridBackground(true);

        //图表背景颜色
        lineChart.setBackgroundColor(Color.LTGRAY);

        //向图表添加一个空的数据，后期填充
        lineChart.setData(lineData);

        //坐标轴设置
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(true);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);

        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100f);

        rightAxis.setEnabled(false);
    }

    public void getSignal(){
        //模拟接收数据
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.arg1 = (int)(Math.random()*100) ;
//                Log.d("++++++++++++++++++++++", String.valueOf(message.arg1)) ;
                lineChartHandler.sendMessage(message) ;
            }
        },0,5000);
    }

    public void updateChart(){
        lineChartHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //handle message and update chart here
                lineData = lineChart.getData() ;
                LineDataSet lineDataSet = (LineDataSet) lineData.getDataSetByIndex(0);
                if(lineDataSet==null){
                    lineDataSet = createLineDataSet();
                    lineData.addDataSet(lineDataSet);
                }

                Log.d("====", String.valueOf(lineDataSet.getEntryCount())) ;
                lineData.addEntry(new Entry(lineDataSet.getEntryCount(),msg.arg1),0);
                Log.d("=======================", String.valueOf(lineDataSet.getEntryCount())) ;
                lineData.notifyDataChanged();
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();

                lineChart.setVisibleXRangeMaximum(20);
                lineChart.moveViewToX(lineData.getXMax() - 10);
            }
        } ;
    }

    private LineDataSet createLineDataSet(){
        LineDataSet set = new LineDataSet(null, "动态添加的数据");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        // 折线的颜色
        set.setColor(ColorTemplate.getHoloBlue());

//        set.setCircleColor(Color.WHITE);
//        set.setLineWidth(10f);
//        set.setFillAlpha(128);
//        set.setFillColor(ColorTemplate.getHoloBlue());
//        set.setHighLightColor(Color.GREEN);
//        set.setValueTextColor(Color.WHITE);
//        set.setValueTextSize(10f);
//        set.setDrawValues(true);
        return set;
    }

}
