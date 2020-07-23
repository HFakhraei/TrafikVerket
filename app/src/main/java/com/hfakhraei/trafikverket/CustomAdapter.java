package com.hfakhraei.trafikverket;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hfakhraei.trafikverket.dto.ExamPlacesConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private List<ExamPlacesConfig> occasionResponses;
    private Context context;

    public CustomAdapter(List<ExamPlacesConfig> examPlacesConfigs, Context context) {
        this.occasionResponses = examPlacesConfigs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return occasionResponses.size();
    }

    @Override
    public Object getItem(int position) {
        return occasionResponses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_listview, parent, false);
        }
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtDate = convertView.findViewById(R.id.txtDate);
        TextView txtTime = convertView.findViewById(R.id.txtTime);
        try {
            ExamPlacesConfig examPlacesConfig = occasionResponses.get(position);
            String city = examPlacesConfig.getLocationName();
            txtName.setText(city);
            txtDate.setText(examPlacesConfig.getFirstAvailableTimeToString());
            txtTime.setText(examPlacesConfig.getLastCheckTimeToString());
            return convertView;
        } catch (Exception e) {
            Log.e(BuildConfig.LOG_TAG, e.getMessage(), e);
        }
        return convertView;
    }
}
