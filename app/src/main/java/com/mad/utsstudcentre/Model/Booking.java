package com.mad.utsstudcentre.Model;

/**
 * Created by noche on 24/08/2017.
 */

public class Booking {

    private long mBookingID;
    private String mEnquiryType;
    private String mStudentCentre;
    private String mDate;
    private long mStudentID;
    private String mStudentName;
    public long getBookingID() {
        return mBookingID;
    }

    private boolean mIsProcessed;

    public final String CENTRE01 = "Building 10";
    public final String CENTRE02 = "Building 05";


    public void setBookingID(long mBookingID) {
        this.mBookingID = mBookingID;
    }

    public String getEnquiryType() {
        return mEnquiryType;
    }

    public void setEnquiryType(String mEnquiryType) {
        this.mEnquiryType = mEnquiryType;
    }

    public String getStudentCentre() {
        return mStudentCentre;
    }

    public void setStudentCentre(String mStudentCentre) {
        this.mStudentCentre = mStudentCentre;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public long getStudentID() {
        return mStudentID;
    }

    public void setStudentID(long mStudentID) {
        this.mStudentID = mStudentID;
    }

    public String getStudentName() {
        return mStudentName;
    }

    public void setStudentName(String mStudentName) {
        this.mStudentName = mStudentName;
    }

    public boolean isIsProcessed() {
        return mIsProcessed;
    }

    public void setIsProcessed(boolean mIsProcessed) {
        this.mIsProcessed = mIsProcessed;
    }
}
