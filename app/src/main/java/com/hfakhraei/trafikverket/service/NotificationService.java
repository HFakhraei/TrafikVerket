package com.hfakhraei.trafikverket.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.hfakhraei.trafikverket.BuildConfig;
import com.hfakhraei.trafikverket.R;

import java.time.LocalDateTime;

public class NotificationService {
    public static void showNotification(Context context, String city, LocalDateTime date) {
        String message = String.format("%s : %s", city, date.toString());
        showNotification(context, message);
    }

    public static void showNotification(Context context, String message) {
        try {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context, "my_channel_01")
                            .setSmallIcon(R.drawable.ic_stat_name)
                            .setContentTitle(BuildConfig.LOG_TAG)
                            .setContentText(message);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mBuilder.setAutoCancel(true);
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            setNotificationChannel(mNotificationManager);
            mNotificationManager.notify(1, mBuilder.build());
            Log.d(BuildConfig.LOG_TAG, "send: "+ message);
        } catch (Exception e) {
            Log.e(BuildConfig.LOG_TAG, "Send massage failed: " + message);
        }
    }

    private static void setNotificationChannel(NotificationManager notificationManager) {
        String channelId = "my_channel_01";
        CharSequence channelName = "My Channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{1000, 2000});
        notificationManager.createNotificationChannel(notificationChannel);
    }
}
