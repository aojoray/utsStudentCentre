package com.mad.utsstudcentre.Model;


public class Booking {

    private long mBookingID;
    private String mRefNumber;
    private String mEnquiryType;
    private StudentCentre mStudentCentre;
    private String mDate;
    private Student mStudent;
    public long getBookingID() {
        return mBookingID;
    }

    private boolean mIsProcessed;

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

    public StudentCentre getStudentCentre() {
        return mStudentCentre;
    }

    public void setStudentCentre(StudentCentre mStudentCentre) {
        this.mStudentCentre = mStudentCentre;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public boolean isIsProcessed() {
        return mIsProcessed;
    }

    public void setIsProcessed(boolean mIsProcessed) {
        this.mIsProcessed = mIsProcessed;
    }

    public String getRefNumber() {
        return mRefNumber;
    }

    public void setRefNumber(String mRefNumber) {
        this.mRefNumber = mRefNumber;
    }

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        this.mStudent = student;
    }
}
