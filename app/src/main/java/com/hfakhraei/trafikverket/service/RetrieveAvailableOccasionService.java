package com.hfakhraei.trafikverket.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.hfakhraei.trafikverket.AppConfiguration;
import com.hfakhraei.trafikverket.BuildConfig;
import com.hfakhraei.trafikverket.dto.occasionSearch.response.OccasionResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static com.hfakhraei.trafikverket.service.NotificationService.showNotification;

public class RetrieveAvailableOccasionService extends IntentService {
    private static boolean isServiceForeground = false;
    public static final String REQUEST_EXTRA = "request_extra";
    public static final String RESPONSE_RESULT_EXTRA = "response_extra";
    public static final String PENDING_RESULT_EXTRA = "pending_result_extra";
    public static final int SUCCESSFUL_CODE = 0;
    public static final int FAILED_CODE = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        isServiceForeground = true;
    }

    @Override
    public void onDestroy() {
        isServiceForeground = false;
        super.onDestroy();
    }

    public static boolean isIsServiceForeground() {
        return isServiceForeground;
    }

    public RetrieveAvailableOccasionService() {
        super("RAC_IntentService_" + System.currentTimeMillis());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        int locationId = intent.getIntExtra(REQUEST_EXTRA, 0);

        if (locationId == 0)
            return;

        PendingIntent reply = intent.getParcelableExtra(PENDING_RESULT_EXTRA);

        try {
            try {
                Log.i(BuildConfig.LOG_TAG,
                        String.format("call api start for locationId %d", locationId));

                OccasionResponse occasionResponse = OccasionApiService.getInstance().callApi(locationId);
                if (allowToProcess(occasionResponse)) {
                    checkResponse(this, occasionResponse);
                    updateExamPlace(locationId, occasionResponse);
                }
                if (reply != null)
                    reply.send(SUCCESSFUL_CODE);

                Log.i(BuildConfig.LOG_TAG,
                        String.format("call api successful for locationId %d", locationId));
            } catch (Exception e) {
                Log.e(BuildConfig.LOG_TAG, e.getMessage(), e);
                Log.i(BuildConfig.LOG_TAG,
                        String.format("call api failed for locationId %d", locationId));

                if (reply != null)
                    reply.send(FAILED_CODE);
            }
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    private void updateExamPlace(int locationId, OccasionResponse occasionResponse) {
        if (allowToProcess(occasionResponse))
            AppConfiguration.updateExamPlace(locationId,
                    occasionResponse.getData().get(0).getOccasions().get(0).getLocationName(),
                    occasionResponse.getData().get(0).getOccasions().get(0).getDuration().getStart());
    }

    private void checkResponse(Context context, OccasionResponse occasionResponse) {
        String city = occasionResponse.getData().get(0).getOccasions().get(0).getLocationName();
        LocalDateTime date = LocalDateTime.parse(
                occasionResponse.getData().get(0).getOccasions().get(0).getDuration().getStart(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]"));
        long days = ChronoUnit.DAYS.between(LocalDateTime.now(), date);
        Log.i(BuildConfig.LOG_TAG,
                String.format("check response %s : %s", city, date.toString()));
        if (days <= BuildConfig.NOTIFICATION_MAX_DAYS) {
            Log.i(BuildConfig.LOG_TAG,
                    String.format("Less than 10 days -> %s : %s", city, date.toString()));
            playAlarm();
            showNotification(context, city, date);
        }
    }

    private boolean allowToProcess(OccasionResponse occasionResponse) {
        if (occasionResponse != null &&
                occasionResponse.getData() != null &&
                occasionResponse.getData().get(0) != null &&
                occasionResponse.getData().get(0).getOccasions() != null &&
                occasionResponse.getData().get(0).getOccasions().get(0) != null) {
            return true;
        }
        Log.e(BuildConfig.LOG_TAG, "occasionResponse may be null");
        return false;
    }

    private void playAlarm() {
        Intent intent = new Intent(getApplicationContext(), AlarmPlayerService.class);
        startService(intent);
    }
}
