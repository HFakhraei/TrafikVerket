package com.hfakhraei.trafikverket.service;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.hfakhraei.trafikverket.BuildConfig;

public class AlarmPlayerService extends Service {
    private Ringtone r;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (r != null && r.isPlaying()) {
            return super.onStartCommand(intent, flags, startId);
        }

        r.play();
        Log.i(BuildConfig.LOG_TAG, "Start Alarm Player");
        (new Handler()).postDelayed(this::stopAlarm, 15000);
        return super.onStartCommand(intent, flags, startId);
    }

    private void stopAlarm() {
        if (r != null && r.isPlaying())
            r.stop();
        Log.i(BuildConfig.LOG_TAG, "Stop Alarm Player");
    }
}
