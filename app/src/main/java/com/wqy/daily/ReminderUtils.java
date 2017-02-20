package com.wqy.daily;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by wqy on 17-2-20.
 */

public class ReminderUtils {
    public static final String TAG = ReminderUtils.class.getSimpleName();

    public static void scheduleNotification(Context context,
                                            Notification notification,
                                            int notificationId,
                                            long time) {
        Log.d(TAG, "scheduleNotification: " + notificationId);
        PendingIntent pi = getPi(context, notification, notificationId);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, time, pi);
    }

    public static void cancelNotification(Context context,
                                          Notification notification,
                                          int notificationId) {
        Log.d(TAG, "cancelNotification: " + notificationId);
        PendingIntent pi = getPi(context, notification, notificationId);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pi);
    }

    private static PendingIntent getPi(Context context,
                                       Notification notification,
                                       int notificationId) {
        Intent nIntent = new Intent(context, NotificationPublisher.class);
        nIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        nIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pi = PendingIntent.getBroadcast(context, notificationId, nIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }
}
