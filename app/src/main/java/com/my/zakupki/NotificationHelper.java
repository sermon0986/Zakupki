package com.my.zakupki;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import com.my.zakupki.Activities.MainActivity;

public class NotificationHelper {

    public static void SendNotification(Context context, String title, String text, String bigText, int position, boolean sound)
    {
        int ID=(int)System.currentTimeMillis();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(bigText));
        mBuilder.setSmallIcon(R.drawable.notify);
        mBuilder.setTicker(title);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(text);
        mBuilder.setColor(context.getResources().getColor(R.color.colorAccent));
        mBuilder.setGroup("Zakupki");

        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.putExtra("FromNotify", true);
        resultIntent.putExtra("Position", position);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, ID, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(false);

        if (sound)
            mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
        else
            mBuilder.setDefaults(Notification.DEFAULT_LIGHTS);

        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(ID, mBuilder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            mNotifyMgr.notify(0, getSummaryNotification(context));
    }

    private static Notification getSummaryNotification(Context context)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("").setBigContentTitle(""));
        mBuilder.setSmallIcon(R.drawable.notify);
        mBuilder.setColor(context.getResources().getColor(R.color.colorAccent));
        mBuilder.setGroup("Zakupki");
        mBuilder.setGroupSummary(true);
        return mBuilder.build();
    }

}
