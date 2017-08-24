package com.mad.utsstudcentre.Model;


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
    public final String ENQTYPE01 = "My Subject Enrolment";
    public final String ENQTYPE02 = "My Study Plan";
    public final String ENQTYPE03 = "My UTS Document";
    public final String ENQTYPE04 = "General Enquiry";
    public final String ENQTYPE05 = "My Class Timetable";
    public final String ENQTYPE06 = "Exams and Assessment";
    public final String ENQTYPE07 = "My graduation";
    public final String ENQTYPE08 = "IT Support";


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
