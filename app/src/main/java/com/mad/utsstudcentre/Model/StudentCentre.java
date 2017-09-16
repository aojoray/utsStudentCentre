package com.mad.utsstudcentre.Model;

/**
 * Created by MoonSSeol on 16/09/2017.
 */

public class StudentCentre {



    private int mID;
    private String mName;
    private int mEstTime;

    public static final String CENTRE_01 = "Building 5";
    public static final String CENTRE_02 = "Building 10";
    public static final int CENTRE_01_ID = 10;
    public static final int CENTRE_02_ID = 20;

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getEstTime() {
        return mEstTime;
    }

    public void setEstTime(int estTime) {
        this.mEstTime = estTime;
    }
}
