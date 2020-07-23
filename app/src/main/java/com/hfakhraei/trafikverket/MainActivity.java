package com.hfakhraei.trafikverket;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hfakhraei.trafikverket.dto.ExamPlacesConfig;
import com.hfakhraei.trafikverket.service.AlarmPlayerService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int WAKE_LOCK_PERMISSION_CODE = 100;
    private static final int ACCESS_NETWORK_STATE_PERMISSION_CODE = 101;
    private static final int INTERNET_PERMISSION_CODE = 102;
    private static final int ACCESS_NOTIFICATION_POLICY_PERMISSION_CODE = 104;

    private static final int SERVICE_REQUEST_CODE = 0;
    private ListView listView;
    private CustomAdapter adapter;

    private List<ExamPlacesConfig> occasionResponses = new ArrayList<>();

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

    private void refreshListView() {
        occasionResponses.clear();
        List<ExamPlacesConfig> latest = AppConfiguration.getSelectedExamPlaces();
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