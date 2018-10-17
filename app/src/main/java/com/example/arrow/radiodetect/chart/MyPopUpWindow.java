package com.example.arrow.radiodetect.chart;

import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;


//这里应该写成一个抽象类，供其他类复写
public abstract class MyPopUpWindow {
    private View containView ;
    private View rootView ;
    private PopupWindow popupWindow ;
    private int winWidth ;
    private int winHight ;
    private int x ;
    private int y ;

    public MyPopUpWindow() {
    }

    public MyPopUpWindow(View containView,View rootView, int w, int h, int x, int y ) {
        this.containView = containView;
        this.rootView = rootView ;
        winWidth = w ;
        winHight = h ;
        this.x = x ;
        this.y = y ;
        popupWindow = new PopupWindow(containView,w,h) ;
    }

    //因为不同的popupwindow显示的内容可能是不同的，所以这里我们提供了抽象方法show供复写
    public abstract void show();

    public View getContainView() {
        return containView;
    }

    public View getRootView() {
        return rootView;
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public int getWinWidth() {
        return winWidth;
    }

    public int getWinHight() {
        return winHight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
