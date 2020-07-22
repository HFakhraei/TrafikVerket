package com.hfakhraei.trafikverket;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hfakhraei.trafikverket.dto.occasionSearch.response.OccasionResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private List<OccasionResponse> occasionResponses;
    private Context context;

    public CustomAdapter(List<OccasionResponse> occasionResponses, Context context) {
        this.occasionResponses = occasionResponses;
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
        TextView txtName = (TextView)convertView.findViewById(R.id.txtName);
        TextView txtDate = (TextView)convertView.findViewById(R.id.txtDate);
        TextView txtTime = (TextView)convertView.findViewById(R.id.txtTime);
        try {
            OccasionResponse occasionResponse = occasionResponses.get(position);
            String city = occasionResponse.getData().get(0).getOccasions().get(0).getLocationName();
            LocalDateTime date = LocalDateTime.parse(
                    occasionResponse.getData().get(0).getOccasions().get(0).getDuration().getStart(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]"));
            txtName.setText(city);
            txtDate.setText(date.format(FORMATTER));
            txtTime.setText(occasionResponse.getFetchTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            return convertView;
        } catch (Exception e) {
            Log.e(BuildConfig.LOG_TAG, e.getMessage(), e);
        }
        return convertView;
    }
}
