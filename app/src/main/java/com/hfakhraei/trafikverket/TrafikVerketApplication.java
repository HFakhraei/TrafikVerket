package com.hfakhraei.trafikverket;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hfakhraei.trafikverket.service.AlarmPlayerService;

import static com.hfakhraei.trafikverket.service.NotificationService.showNotification;

public class TrafikVerketApplication extends Application {
    private AlarmManager am;

    @Override
    public void onCreate() {
        super.onCreate();
        startAllServices();
        setScheduler();
    }

    @Override
    public void onTerminate() {
        stopAllServices();
        super.onTerminate();
    }

    private void startAllServices() {
    }

    private void stopAllServices() {
        stopService(new Intent(getApplicationContext(), AlarmPlayerService.class));
    }

    private void setScheduler() {
        if (am != null && am.getNextAlarmClock().getTriggerTime() > 0) {
            Log.i(BuildConfig.LOG_TAG, "Alarm Manager configuration exist");
            return;
        }

        configScheduler(BuildConfig.SCHEDULER_INTERVAL_30, M30SchedulerReceiver.class);
        configScheduler(BuildConfig.SCHEDULER_INTERVAL_10, M10SchedulerReceiver.class);
        configScheduler(BuildConfig.SCHEDULER_INTERVAL_05, M05SchedulerReceiver.class);
        //Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
        showNotification(getApplicationContext(), "Alarm Manager Configured " + BuildConfig.APP_MODE);
        Log.i(BuildConfig.LOG_TAG, "Alarm Manager Configured " + BuildConfig.APP_MODE);
    }

    private void configScheduler(long millis, Class type) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, type);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        assert am != null;
        am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + millis, millis, pi);
    }

}
