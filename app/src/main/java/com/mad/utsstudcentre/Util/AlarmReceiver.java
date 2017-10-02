package com.mad.utsstudcentre.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Alarm BroadcastReceiver associating alarm service with broadcast receiver.
 * Alarm service will be invoked on scheduled time even the user turns off the app.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver_TAG";

    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO: replace the next line of code with actual notification etc.
        Toast.makeText(context, "AlarmReceiver: ALARM ", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "AlarmReceiver");
//        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(2000);

    }
}
