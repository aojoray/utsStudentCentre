package com.mad.utsstudcentre.Util;

/**
 * Created by HP on 24/08/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    public static String getStudentId(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Values.SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Values.SHARED_PREFERENCES_USERID_TAG, null);
    }

    public static void deleteSessionStudentId(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Values.SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE);
        sharedpreferences.edit().remove(Values.SHARED_PREFERENCES_USERID_TAG).apply();
    }

    public static void registerSession(Context context, String id){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Values.SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Values.SHARED_PREFERENCES_USERID_TAG, id);
        editor.commit();
    }

    public static void registerSessionStatus(Context context, String id){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Values.SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Values.SHARED_PREFERENCES_USERSTATUS_TAG, id);
        editor.commit();
    }

    public static String getUserStatus(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Values.SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Values.SHARED_PREFERENCES_USERSTATUS_TAG, null);
    }

    public static void deleteSessionStatus(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(Values.SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE);
        sharedpreferences.edit().remove(Values.SHARED_PREFERENCES_USERSTATUS_TAG).apply();
    }
}

