package com.wqy.daily;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.wqy.daily.interfaces.Remindable;
import com.wqy.daily.model.Bigday;
import com.wqy.daily.model.Memo;
import com.wqy.daily.model.Reminder;


/**
 * Created by wqy on 17-2-20.
 */

public class ReminderUtils {
    public static final String TAG = ReminderUtils.class.getSimpleName();

    public static void scheduleReminder(Context context, Reminder reminder) {
        PendingIntent pi = getPi(context, reminder);
        if (pi != null) {
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, reminder.getTime(), pi);
        }
    }

    public static void cancelReminder(Context context, Reminder reminder) {
        PendingIntent pi = getPi(context, reminder);
        if (pi != null) {
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pi);
        }
    }

    public static PendingIntent getPi(Context context, Reminder reminder) {
        Intent nIntent = new Intent(context, NotificationPublisher.class);
        // notification id
        nIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, reminder.getId());
        nIntent.putExtra(NotificationPublisher.NOTIFICATION,
                getNotification(context, reminder.getIntent(), reminder.getTitle(), reminder.getInfo()));
        // alarm id
        PendingIntent pi = PendingIntent.getBroadcast(context, reminder.getId(), nIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }

    public static Notification getNotification(Context context, Intent intent, String title, String content) {
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pi)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }

    public static Reminder getReminder(Context context, Remindable remindable, Intent intent) {
        Reminder reminder = new Reminder();
        reminder.setId(remindable.getRemindId());
        reminder.setTime(remindable.getRemindTime().getTime());
        reminder.setIntent(intent);
        reminder.setTitle(remindable.getTitle());
        reminder.setInfo(context.getString(R.string.reminder_info));
        return reminder;
    }

}
