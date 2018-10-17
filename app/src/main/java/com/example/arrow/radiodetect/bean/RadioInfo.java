package com.example.arrow.radiodetect.bean;

public class RadioInfo {
    private int pcid ;
    private float rsrp ;
    private float rsrq ;
    private float rssi ;

    public RadioInfo(){
    };

    public RadioInfo(int pcid, float rsrp, float rsrq, float rssi) {
        this.pcid = pcid;
        this.rsrp = rsrp;
        this.rsrq = rsrq;
        this.rssi = rssi;
    }
}
