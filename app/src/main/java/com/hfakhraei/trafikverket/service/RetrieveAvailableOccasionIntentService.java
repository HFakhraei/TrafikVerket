package com.hfakhraei.trafikverket.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.hfakhraei.trafikverket.BuildConfig;
import com.hfakhraei.trafikverket.dto.response.OccasionResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static com.hfakhraei.trafikverket.service.NotificationService.showNotification;

public class RetrieveAvailableOccasionIntentService extends IntentService {
    public static final String REQUEST_EXTRA = "request_extra";
    public static final String RESPONSE_RESULT_EXTRA = "response_extra";
    public static final String PENDING_RESULT_EXTRA = "pending_result_extra";
    public static final int SUCCESSFUL_CODE = 0;
    public static final int FAILED_CODE = 1;
    public static final String RETRIEVE_AVAILABLE_OCCASION_INTENT_SERVICE = "RetrieveAvailableOccasionIntentService";

    private Ringtone r;

    public RetrieveAvailableOccasionIntentService() {
        super("RAC_IntentService_" + System.currentTimeMillis());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        PendingIntent reply = intent.getParcelableExtra(PENDING_RESULT_EXTRA);
        try {
           int locationId = intent.getIntExtra(REQUEST_EXTRA, 0);
           try {
                OccasionResponse occasionResponse = OccasionApiService.getInstance().callApi(locationId);
                Intent result = new Intent();
                result.putExtra(RESPONSE_RESULT_EXTRA, occasionResponse);
                if (reply != null) {
                    reply.send(this, SUCCESSFUL_CODE, result);
                } else {
                    checkResponse(this, occasionResponse);
                }
               Log.i(RETRIEVE_AVAILABLE_OCCASION_INTENT_SERVICE,
                       String.format("call api successful for locationId %d", locationId));
            } catch (Exception e) {
                e.printStackTrace();

               String message = String.format("Failed calling API with message %s",e.getMessage());
               showNotification(this, message);

               Log.i(RETRIEVE_AVAILABLE_OCCASION_INTENT_SERVICE,
                       String.format("call api failed for locationId %d", locationId));

               if (reply != null) {
                    Intent result = new Intent();
                    result.putExtra(RESPONSE_RESULT_EXTRA, locationId);
                    reply.send(this, FAILED_CODE, result);
                }
            }
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    private void checkResponse(Context context, OccasionResponse occasionResponse) {
        String city = occasionResponse.getData().get(0).getOccasions().get(0).getLocationName();
        LocalDateTime date = LocalDateTime.parse(
                occasionResponse.getData().get(0).getOccasions().get(0).getDuration().getStart(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]"));
        long days = ChronoUnit.DAYS.between(LocalDateTime.now(), date);
        Log.i(RETRIEVE_AVAILABLE_OCCASION_INTENT_SERVICE,
                String.format("check response %s : %s", city, date.toString()));
        if (days <= BuildConfig.NOTIFICATION_MAX_DAYS) {
            Log.i(RETRIEVE_AVAILABLE_OCCASION_INTENT_SERVICE,
                    String.format("Less than 10 days -> %s : %s", city, date.toString()));
            playAlarm(context);
            showNotification(context, city, date);
        }
    }

    private void playAlarm(Context context) {
        if (r != null && r.isPlaying()) {
            return;
        }

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(context, notification);
        r.play();
        (new Handler()).postDelayed(this::stopAlarm, 15000);
    }

    private void stopAlarm() {
        if (r != null)
            r.stop();
    }
}
