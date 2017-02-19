package com.wqy.daily;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wqy on 17-2-19.
 */

public class NotificationPublisher extends BroadcastReceiver {

    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    public static final String NOTIFICATION = "NOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        manager.notify(id, notification);
    }
}
