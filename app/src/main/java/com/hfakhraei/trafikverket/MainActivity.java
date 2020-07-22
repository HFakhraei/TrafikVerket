package com.hfakhraei.trafikverket;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hfakhraei.trafikverket.dto.response.OccasionResponse;
import com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.hfakhraei.trafikverket.service.NotificationService.showNotification;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService.PENDING_RESULT_EXTRA;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionService.REQUEST_EXTRA;

public class MainActivity extends AppCompatActivity {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm");
    private static final int SERVICE_REQUEST_CODE = 0;
    private Button btnExecute;
    private ListView listView;
    private TextView txtCounter;
    private TextView txtLastUpdate;
    private CustomAdapter adapter;

    private List<OccasionResponse> occasionResponses = new ArrayList<>();
    private Integer counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnExecute = findViewById(R.id.btnExecute);
        txtCounter = findViewById(R.id.txtCounter);
        txtLastUpdate = findViewById(R.id.txtLastUpdate);

        adapter = new CustomAdapter(occasionResponses, this);
        listView = findViewById(R.id.lstView);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }

    private void refreshListView() {
        occasionResponses.clear();
        txtLastUpdate.setText(RetrieveAvailableOccasionService.getLastUpdate().format(FORMATTER));
        List<OccasionResponse> latest = RetrieveAvailableOccasionService.getLatest();
        occasionResponses.addAll(latest);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SERVICE_REQUEST_CODE) {
            String s = (--counter).toString();
            txtCounter.setText(s);
            refreshListView();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void btnExecuteOnClick(View view) {
        if (view.getId() != R.id.btnExecute) {
            return;
        }
        occasionResponses.clear();
        txtCounter.setText("0");
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
        String s = (++counter).toString();
        txtCounter.setText(s);
    }
}