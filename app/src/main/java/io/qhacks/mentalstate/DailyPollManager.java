package io.qhacks.mentalstate;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;

import java.util.Calendar;

public class DailyPollManager extends BroadcastReceiver {

    public final static int NOTIFICATION_ID = 20417;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent responseIntent = new Intent(context, DailyPollResponseReceiver.class);
        responseIntent.putExtra("type", 1);
        PendingIntent pig = PendingIntent.getBroadcast(context, 0, responseIntent, 0);
        responseIntent.putExtra("type", 0);
        PendingIntent pib = PendingIntent.getBroadcast(context, 0, responseIntent, 0);

        Notification.Action goodAction = new Notification.Action.Builder(R.mipmap.ic_launcher, "Good", pig).build();
        Notification.Action badAction = new Notification.Action.Builder(R.mipmap.ic_launcher, "Bad", pib).build();

        Notification.Builder nb = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.notif_title))
                .setContentText(context.getString(R.string.notif_content))
                .addAction(goodAction)
                .addAction(badAction);
        Notification notif = nb.build();

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID, notif);
    }

    public static void enableNotifications(Context context, int hour){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getIntent(context));
    }

    public static void disableNotifications(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(getIntent(context));
    }

    private static PendingIntent getIntent(Context context){
        Intent notify = new Intent(context, DailyPollManager.class);
        return PendingIntent.getBroadcast(context, 0, notify, 0);
    }
}
