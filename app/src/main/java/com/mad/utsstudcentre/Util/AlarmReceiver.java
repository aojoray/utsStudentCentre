package com.mad.utsstudcentre.Util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.mad.utsstudcentre.Controller.FinalConfirmActivity;
import com.mad.utsstudcentre.Controller.MainActivity;
import com.mad.utsstudcentre.R;

import static com.mad.utsstudcentre.Controller.MainActivity.CANCEL;
import static com.mad.utsstudcentre.Controller.MainActivity.CONFIRM;

/**
 * Alarm BroadcastReceiver associating alarm service with broadcast receiver.
 * Alarm service will be invoked on scheduled time even the user turns off the app.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver_TAG";

    // Push Notification builder
    private NotificationCompat.Builder mNotificationBuilder;
    private NotificationManager mNotificationManager;
    private Context mContext;

    /**
     * If the estimated time reaches to 10 minutes, the alarm receiver will send the push notification.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "AlarmReceiver");
        mContext=context;
        setPushNotification();

    }

    /**
     * setPushNotification set the view of notification then calls sendNotification
     */
    public void setPushNotification() {
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(mContext.getString(R.string.notification_title))
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX)
//                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mContext.getString(R.string.notification_text)))
                .setContentText((mContext.getString(R.string.notification_text)));
        Intent answerIntent = new Intent(mContext, FinalConfirmActivity.class);
//        answerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
//        answerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                IntentCompat.FLAG_ACTIVITY_CLEAR_TASK |
//                IntentCompat.FLAG_ACTIVITY_TASK_ON_HOME);
        answerIntent.setAction(CONFIRM);
        PendingIntent pendingIntentYes = PendingIntent.getActivity(mContext, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotificationBuilder.addAction(R.drawable.ic_action_check, "Confirm", pendingIntentYes);
        answerIntent.setAction(CANCEL);
        PendingIntent pendingIntentNo = PendingIntent.getActivity(mContext, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotificationBuilder.addAction(R.drawable.ic_action_cancel, "Cancel", pendingIntentNo);
        sendNotification();
    }

    /**
     * sendNotification sends the notification asking user either to confirm or cancelAlarm the booking.
     */
    private void sendNotification() {
        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotificationBuilder.setContentIntent(contentIntent);
        Notification notification = mNotificationBuilder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        mNotificationManager.notify(1001, notification);
        Log.d(TAG, "Notification sent!!! ");
    }
}
