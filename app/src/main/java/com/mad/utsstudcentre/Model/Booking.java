package com.mad.utsstudcentre.Model;

/**
 * Created by noche on 5/10/2017.
 */

public class Booking {

    public final String ENQTYPE01 = "My Subject Enrolment";
    public final String ENQTYPE02 = "My Study Plan";
    public final String ENQTYPE03 = "My UTS Document";
    public final String ENQTYPE04 = "General Enquiry";
    public final String ENQTYPE05 = "My Class Timetable";
    public final String ENQTYPE06 = "Exams and Assessment";
    public final String ENQTYPE07 = "My graduation";
    public final String ENQTYPE08 = "IT Support";

    private String bookingId;
    private String reference;
    private String enqType;
    private String date;
    private Student student;
    private StudentCentre centre;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getEnqType() {
        return enqType;
    }

    public void setEnqType(String enqType) {
        this.enqType = enqType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentCentre getCentre() {
        return centre;
    }

    public void setCentre(StudentCentre centre) {
        this.centre = centre;
    }
}
