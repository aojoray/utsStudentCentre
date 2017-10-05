package com.mad.utsstudcentre.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.Editor;

import com.mad.utsstudcentre.Dialogue.ConfirmDialogue;
import com.mad.utsstudcentre.Model.StudentCentre;

/**
 * Created by HP on 7/09/2017.
 */

public class SaveSharedPreference {

    static final String PREF_USERNAME = "username";
    static final String PREF_FIRSTNAME = "firstname";
    static final String PREF_REFNUMBER = "refnumber";
    static final String PREF_STUDENTNAME = "studentname";
    static final String PREF_STUDENTCENTRE = "studentcentre";
    static final String PREF_ENQUIRYTYPE = "enquirytype";

    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    //Set and get student username to be stored in the save shared preference
    public static void setUserName(Context ctx, String userName){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USERNAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USERNAME, "");
    }

    //Set and get student firstname to be stored in the save shared preference
    public static void setFirstName(Context ctx, String firstName){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_FIRSTNAME, firstName);
        editor.commit();
    }

    public static String getFirstName(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_FIRSTNAME, "");
    }

    public static void clearUserName(Context ctx){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

    public static void setRefNumber(Context ctx, String refNumber){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_REFNUMBER, refNumber);
        editor.commit();
    }

    public static void setBookingDetails(Context ctx, String refNumber,
                                         String studentName, String enquiryType,
                                         String studentCentre){
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_REFNUMBER, refNumber);
        editor.putString(PREF_STUDENTNAME, studentName);
        editor.putString(PREF_STUDENTCENTRE, studentCentre);
        editor.putString(PREF_ENQUIRYTYPE, enquiryType);
        editor.commit();
    }

    public static String getRefNumber(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_REFNUMBER, "");
    }

    public static String getStudentName(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_STUDENTNAME, "");
    }

    public static String getStudentCentre(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_STUDENTCENTRE, "");
    }

    public static String getEnquiryType(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_ENQUIRYTYPE, "");
    }

}