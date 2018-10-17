package com.example.arrow.radiodetect.chart;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.table.TableData;
import com.example.arrow.radiodetect.MainActivity;
import com.example.arrow.radiodetect.R;
import com.example.arrow.radiodetect.bean.RadioInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyTable {

    private SmartTable topNTable ;
    private View rootView ;
    private List<Column> columns ;
    private List<RadioInfo> radioInfoList ;
    private TableData<RadioInfo> tableData ;
    private String tableName ;
    private Handler tableDataHandler ;
    public MyTable() {
    }

    public MyTable(View view) {
        rootView = view ;
        topNTable = rootView.findViewById(R.id.topN_table);
        columns = new ArrayList<>() ;
        radioInfoList = new ArrayList<>() ;
        tableName = "top N" ;
        topNTable.getConfig().setMinTableWidth(1400) ;
    }

    //设置列名
    public void setColumns(){
        Column<Integer> pciColumn = new Column<Integer>("ID","pcid") ;
        Column<Float> rsrpColumn = new Column<Float>("RSRP","rsrp") ;
        Column<Float> rssiColumn = new Column<Float>("RSSI","rssi") ;
        Column<Float> rsrqColumn = new Column<Float>("RSRQ","rsrq") ;

        columns.add(pciColumn) ;
        columns.add(rsrpColumn) ;
        columns.add(rsrqColumn) ;
        columns.add(rssiColumn) ;
    }

    //获取表格数据，实际应该是从内存区域或者数据库去读，这里我们先写死以作测试
    public void getRadioInfo(){
        //这里我先随便写死一波数据
        radioInfoList.add(new RadioInfo(1,-22,-32,-80)) ;
        radioInfoList.add(new RadioInfo(1,-22,-32,-80)) ;
        radioInfoList.add(new RadioInfo(1,-22,-32,-80)) ;

        //这里添加动态的数据

    }

    public void setData(){
        tableData = new TableData<RadioInfo>(tableName,radioInfoList,columns) ;
        //topNTable.setTableData( tableData);

        //这里一边接收新的数据，一边更新表格
        //用handler更新视图
        tableDataHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //首先更新radiaoInfoList
                radioInfoList.set(0,new RadioInfo(msg.arg1,-22,-32,-80)) ;
                tableData.setT(radioInfoList);
                topNTable.setTableData(tableData) ;
            }
        };
        //我们开启一个新的线程来模拟发送数据
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.arg1 = (int)(Math.random()*100) ;
                Log.d("in subthread", String.valueOf(message.arg1)) ;
                tableDataHandler.sendMessage(message) ;
            }
        },0,10000);
    }

    public void setListener(final Activity activity){
        tableData.setOnItemClickListener(new TableData.OnItemClickListener() {
            @Override
            public void onClick(Column column, String value, Object o, int col, int row) {
                //这里显示点击后的弹窗内容
                View popView = activity.getLayoutInflater().inflate(R.layout.chart_popup,null) ;
                TopNPopUpWindow topNPopUpWindow = new TopNPopUpWindow(popView ,LayoutInflater.from(activity).inflate(R.layout.activity_main, null),700,500,20,20) ;
                topNPopUpWindow.show();
            }
        });

    }



}
