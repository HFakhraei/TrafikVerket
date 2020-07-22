package com.hfakhraei.trafikverket;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hfakhraei.trafikverket.dto.occasionSearch.response.OccasionResponse;
import com.hfakhraei.trafikverket.service.AlarmPlayerService;
import com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService;

import java.util.ArrayList;
import java.util.List;

import static com.hfakhraei.trafikverket.service.NotificationService.showNotification;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService.PENDING_RESULT_EXTRA;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService.REQUEST_EXTRA;

public class MainActivity extends AppCompatActivity {
    private static final int WAKE_LOCK_PERMISSION_CODE = 100;
    private static final int ACCESS_NETWORK_STATE_PERMISSION_CODE = 101;
    private static final int INTERNET_PERMISSION_CODE = 102;
    private static final int ACCESS_NOTIFICATION_POLICY_PERMISSION_CODE = 104;

    private static final int SERVICE_REQUEST_CODE = 0;
    private ListView listView;
    private CustomAdapter adapter;

    private List<OccasionResponse> occasionResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission(Manifest.permission.WAKE_LOCK, WAKE_LOCK_PERMISSION_CODE);
        checkPermission(Manifest.permission.ACCESS_NOTIFICATION_POLICY, ACCESS_NOTIFICATION_POLICY_PERMISSION_CODE);
        checkPermission(Manifest.permission.INTERNET, INTERNET_PERMISSION_CODE);
        checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, ACCESS_NETWORK_STATE_PERMISSION_CODE);

        setContentView(R.layout.activity_main);

        adapter = new CustomAdapter(occasionResponses, this);
        listView = findViewById(R.id.lstView);
        listView.setAdapter(adapter);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.activity_listview_header, listView, false);
        listView.addHeaderView(header);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }

    private void refreshListView() {
        occasionResponses.clear();
        List<OccasionResponse> latest = RetrieveAvailableOccasionService.getLatest();
        occasionResponses.addAll(latest);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SERVICE_REQUEST_CODE) {
            refreshListView();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void btnExecuteOnClick(View view) {
        if (view.getId() != R.id.btnExecute) {
            return;
        }
        occasionResponses.clear();
        adapter.notifyDataSetChanged();
        retrieveAvailableOccasion(1000140);//Stockholm
        retrieveAvailableOccasion(1000134);//Sollentuna
        retrieveAvailableOccasion(1000326);//Järfälla
        retrieveAvailableOccasion(1000132);//Södertälje
        retrieveAvailableOccasion(1000071);//Uppsala
        retrieveAvailableOccasion(1000149);//Nyköping
        retrieveAvailableOccasion(1000038);//Västerås
        retrieveAvailableOccasion(1000005);//Eskilstuna
        retrieveAvailableOccasion(1000072);//Köping
        retrieveAvailableOccasion(1000329);//Norrköping
        retrieveAvailableOccasion(1000009);//Linköping
        retrieveAvailableOccasion(1000011);//Motala
        retrieveAvailableOccasion(1000001);//Örebro
    }

    private void retrieveAvailableOccasion(int locationId) {
        String message = String.format("Start foreground service for id %d", locationId);
        Log.i(BuildConfig.LOG_TAG, message);
        PendingIntent pendingResult = createPendingResult(
                SERVICE_REQUEST_CODE, new Intent(), 0);
        Intent intent = new Intent(getApplicationContext(), RetrieveAvailableOccasionService.class);
        intent.putExtra(REQUEST_EXTRA, locationId);
        intent.putExtra(PENDING_RESULT_EXTRA, pendingResult);
        startService(intent);
    }

    public void btnRefreshOnClick(View view) {
        if (view.getId() != R.id.btnRefresh) {
            return;
        }
        refreshListView();
    }

    public void btnStopAlarm(View view) {
        AlarmPlayerService.stopAlarm();
    }

    private void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == WAKE_LOCK_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "WAKE_LOCK Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == ACCESS_NOTIFICATION_POLICY_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "ACCESS_NOTIFICATION_POLICY Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == INTERNET_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "INTERNET Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == ACCESS_NETWORK_STATE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "ACCESS_NETWORK_STATE Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}