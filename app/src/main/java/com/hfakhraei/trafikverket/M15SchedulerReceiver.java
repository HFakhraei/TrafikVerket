package com.hfakhraei.trafikverket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService;

import java.time.LocalDateTime;

import static androidx.core.content.ContextCompat.startForegroundService;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService.REQUEST_EXTRA;


public class M15SchedulerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = String.format("Start M30 background tasks at %s", LocalDateTime.now().toString());
        Log.i(BuildConfig.LOG_TAG, message);
        retrieveAvailableOccasion(context);
        retrieveAvailableOccasion(context, 1000149);//Nyköping
        retrieveAvailableOccasion(context, 1000038);//Västerås
        retrieveAvailableOccasion(context, 1000005);//Eskilstuna
        retrieveAvailableOccasion(context, 1000072);//Köping
        retrieveAvailableOccasion(context, 1000329);//Norrköping
        retrieveAvailableOccasion(context, 1000009);//Linköping
        retrieveAvailableOccasion(context, 1000011);//Motala
        retrieveAvailableOccasion(context, 1000001);//Örebro
        message = String.format("Stop M30 background tasks at %s", LocalDateTime.now().toString());
        Log.i(BuildConfig.LOG_TAG, message);
    }

    private void retrieveAvailableOccasion(Context context) {
        startForegroundService(context, new Intent(context, RetrieveAvailableOccasionService.class));
    }

    private void retrieveAvailableOccasion(Context context, int locationId) {
        String message = String.format("Start background service for id %d", locationId);
        Log.i(BuildConfig.LOG_TAG, message);
        Intent intent = new Intent(context, RetrieveAvailableOccasionService.class);
        intent.putExtra(REQUEST_EXTRA, locationId);
        context.startService(intent);
    }
}
