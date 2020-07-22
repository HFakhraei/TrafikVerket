package com.hfakhraei.trafikverket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService;

import java.time.LocalDateTime;

import static androidx.core.content.ContextCompat.startForegroundService;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService.REQUEST_EXTRA;

public class M05SchedulerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = String.format("Start M05 background tasks at %s", LocalDateTime.now().toString());
        Log.i(BuildConfig.LOG_TAG, message);
        retrieveAvailableOccasion(context);
        retrieveAvailableOccasion(context, 1000140);//Stockholm
        message = String.format("Stop background tasks at %s", LocalDateTime.now().toString());
        Log.i(BuildConfig.LOG_TAG, message);
    }

    private void retrieveAvailableOccasion(Context context) {
        startForegroundService(context, new Intent(context, RetrieveAvailableOccasionService.class));
    }

    private void retrieveAvailableOccasion(Context context, int locationId) {
        String message = String.format("Start M05 background service for id %d", locationId);
        Log.i(BuildConfig.LOG_TAG, message);
        Intent intent = new Intent(context, RetrieveAvailableOccasionService.class);
        intent.putExtra(REQUEST_EXTRA, locationId);
        context.startService(intent);
    }
}
