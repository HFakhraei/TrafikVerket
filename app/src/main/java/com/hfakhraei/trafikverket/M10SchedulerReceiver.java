package com.hfakhraei.trafikverket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService;

import java.time.LocalDateTime;

import static androidx.core.content.ContextCompat.startForegroundService;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService.REQUEST_EXTRA;

public class M10SchedulerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = String.format("Start M10 background tasks at %s", LocalDateTime.now().toString());
        Log.i(BuildConfig.LOG_TAG, message);
        retrieveAvailableOccasion(context);
//        retrieveAvailableOccasion(context, 1000134);//Sollentuna
        retrieveAvailableOccasion(context, 1000326);//Järfälla
        retrieveAvailableOccasion(context, 1000132);//Södertälje
        retrieveAvailableOccasion(context, 1000071);//Uppsala
        message = String.format("Stop background tasks at %s", LocalDateTime.now().toString());
        Log.i(BuildConfig.LOG_TAG, message);
    }

    private void retrieveAvailableOccasion(Context context) {
        startForegroundService(context, new Intent(context, RetrieveAvailableOccasionService.class));
    }

    private void retrieveAvailableOccasion(Context context, int locationId) {
        String message = String.format("Start M10 background service for id %d", locationId);
        Log.i(BuildConfig.LOG_TAG, message);
        Intent intent = new Intent(context, RetrieveAvailableOccasionService.class);
        intent.putExtra(REQUEST_EXTRA, locationId);
        context.startService(intent);
    }
}
