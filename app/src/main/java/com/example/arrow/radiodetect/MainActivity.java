package com.example.arrow.radiodetect;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMapLoadedListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.example.arrow.radiodetect.chart.MyBarChart;
import com.example.arrow.radiodetect.chart.MyLineChart;
import com.example.arrow.radiodetect.chart.MyTable;
import com.example.arrow.radiodetect.fragment.ChartFragment;
import com.example.arrow.radiodetect.fragment.MapFragment;

public class MainActivity extends AppCompatActivity implements AMap.OnMapLoadedListener{
    private MapFragment mapFragment = new MapFragment() ;
    private ChartFragment chartFragment  = new ChartFragment();

    private Bundle msavedInstanceState ;

    private MapView mMapView  ;
    private AMap aMap ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.msavedInstanceState = savedInstanceState ;
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,chartFragment).commit() ;
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,mapFragment).commit() ;


    }

    @Override
    protected void onStart() {
        super.onStart();

//        MyLineChart myLineChart = new MyLineChart(this) ;
//        myLineChart.configLineChart();
//        //注意以下两个函数是有先后关系的
//        myLineChart.updateChart();
//        myLineChart.getSignal();

        //初始化表格
        MyTable myTable = new MyTable(this.getWindow().getDecorView()) ;
        myTable.setColumns();
        myTable.getRadioInfo();
        myTable.setData();
        myTable.setListener(MainActivity.this);

        //初始化柱状图
        MyBarChart myBarChart = new MyBarChart(this) ;
        myBarChart.configBarChart();
        myBarChart.updateChart();
        myBarChart.getSignal();

        mMapView = findViewById(R.id.map) ;
        mMapView.onCreate(msavedInstanceState);
        aMap = mMapView.getMap() ;
        onMapLoaded();

    }
    //复写其他生命周期的各方法
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    //这里回调似乎没有被调用，我手动调用了
    @Override
    public void onMapLoaded() {
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(32.0415,118.788055)));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction() ;
        hideAllFragments(ft) ;
        switch (item.getItemId()){
            case R.id.item_mapSwitch:
                //进行fragment切换
                Log.d("----","map switch clicked") ;
                if(mapFragment == null){
                    Log.d("****","map fragment is null") ;
                    mapFragment = new MapFragment() ;
                    ft.add(R.id.frameLayout,mapFragment) ;
                }
                else
                    ft.show(mapFragment) ;
                break;
                //return true;
            case R.id.item_chartSwitch:
                //进行fragment切换
                Log.d("++++","chart switch clicked") ;
                if(chartFragment == null){
                    Log.d("****","chart fragment is null") ;
                    chartFragment = new ChartFragment() ;
                    ft.add(R.id.frameLayout,chartFragment) ;
                }
                else
                    ft.show(chartFragment) ;

                //return true ;
                break;

        }
        ft.commit();
        return super.onOptionsItemSelected(item);


    }

    public void hideAllFragments(FragmentTransaction ft){
        if(mapFragment !=null) {
            Log.d("@@@@","hide mapFragment") ;
            ft.hide(mapFragment) ;
        }
        if(chartFragment!=null) {
            Log.d("@@@@","hide chartFragment") ;
            ft.hide(chartFragment) ;
        }
    }
}
