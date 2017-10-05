package com.mad.utsstudcentre.Model;

/**
 * Created by MoonSSeol on 16/09/2017.
 */

public class StudentCentre {

    public static final String CENTRE_01 = "Building 5";
    public static final String CENTRE_02 = "Building 10";
    public static final int CENTRE_01_ID = 10;
    public static final int CENTRE_02_ID = 20;

    private int centerId;
    private String centerName;
    private int estTime;
    private int waiting;

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public int getEstTime() {
        return estTime;
    }

    public void setEstTime(int estTime) {
        this.estTime = estTime;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting (int waiting) {
        this.waiting = waiting;
    }


//
//
//    private int mID;
//    private String mName;
//    private int mEstTime;
//
//    public static final String CENTRE_01 = "Building 5";
//    public static final String CENTRE_02 = "Building 10";
//    public static final int CENTRE_01_ID = 10;
//    public static final int CENTRE_02_ID = 20;
//
//    public int getID() {
//        return mID;
//    }
//
//    public void setID(int mID) {
//        this.mID = mID;
//    }
//
//    public String getName() {
//        return mName;
//    }
//
//    public void setName(String mName) {
//        this.mName = mName;
//    }
//
//    public int getEstTime() {
//        return mEstTime;
//    }
//
//    public void setEstTime(int estTime) {
//        this.mEstTime = estTime;
//    }
}
