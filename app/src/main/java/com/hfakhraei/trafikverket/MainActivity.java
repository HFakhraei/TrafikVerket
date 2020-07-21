package com.hfakhraei.trafikverket;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hfakhraei.trafikverket.dto.response.OccasionResponse;
import com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionIntentService;

import java.util.ArrayList;
import java.util.List;

import static com.hfakhraei.trafikverket.service.NotificationService.showNotification;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionIntentService.FAILED_CODE;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionIntentService.PENDING_RESULT_EXTRA;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionIntentService.REQUEST_EXTRA;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionIntentService.RESPONSE_RESULT_EXTRA;
import static com.hfakhraei.trafikverket.service.RetrieveAvailableOccasionIntentService.SUCCESSFUL_CODE;

public class MainActivity extends AppCompatActivity {
    private static final int SERVICE_REQUEST_CODE = 0;
    private Button btnExecute;
    private ListView listView;
    private TextView txtCounter;
    private ArrayAdapter adapter;

    private List<String> mobileArray = new ArrayList<String>();
    private Integer counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnExecute = findViewById(R.id.btnExecute);
        txtCounter = findViewById(R.id.txtCounter);

        adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, mobileArray);

        listView = findViewById(R.id.lstView);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SERVICE_REQUEST_CODE) {
            String s = (--counter).toString();
            txtCounter.setText(s);
            switch (resultCode) {
                case SUCCESSFUL_CODE:
                    processResponse(data);
                    break;
                case FAILED_CODE:
                    processError(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void processResponse(Intent data) {
        OccasionResponse occasionResponse =
                (OccasionResponse) data.getSerializableExtra(RESPONSE_RESULT_EXTRA);
        String city = occasionResponse.getData().get(0).getOccasions().get(0).getLocationName();
        String date = occasionResponse.getData().get(0).getOccasions().get(0).getDuration().getStart();
        mobileArray.add(String.format("%s -> %s", city, date));
        adapter.notifyDataSetChanged();
    }

    private void processError(Intent data) {
        int locationId = data.getIntExtra(RESPONSE_RESULT_EXTRA, 0);
        mobileArray.add(String.format("Error: %d", locationId));
        adapter.notifyDataSetChanged();
    }

    public void btnExecuteOnClick(View view) {
        if (view.getId() != R.id.btnExecute) {
            return;
        }
        mobileArray.clear();
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
        PendingIntent pendingResult = createPendingResult(
                SERVICE_REQUEST_CODE, new Intent(), 0);
        Intent intent = new Intent(getApplicationContext(), RetrieveAvailableOccasionIntentService.class);
        intent.putExtra(REQUEST_EXTRA, locationId);
        intent.putExtra(PENDING_RESULT_EXTRA, pendingResult);
        startService(intent);
        String s = (++counter).toString();
        txtCounter.setText(s);
    }
}