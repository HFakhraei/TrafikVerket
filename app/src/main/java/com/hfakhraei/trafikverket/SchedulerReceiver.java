package com.hfakhraei.trafikverket;

import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static androidx.core.content.ContextCompat.startForegroundService;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService.REQUEST_EXTRA;

public class SchedulerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = String.format("Start background service at %s", LocalDateTime.now().toString());
        Log.i(BuildConfig.LOG_TAG, message);
        startForegroundService(context, new Intent(context, RetrieveAvailableOccasionService.class));
        retrieveAvailableOccasion(context);
    }

    private void retrieveAvailableOccasion(Context context) {
        AppConfiguration.getSelectedExamPlaces().forEach(item -> {
            if (item.isValidityTimePassed()) {
                String message = String.format(Locale.getDefault()
                        , "Start background service for id %d", item.getLocationId());
                Log.i(BuildConfig.LOG_TAG, message);
                Intent intent = new Intent(context, RetrieveAvailableOccasionService.class);
                intent.putExtra(REQUEST_EXTRA, item.getLocationId());
                context.startService(intent);
            }
        });
    }
}
