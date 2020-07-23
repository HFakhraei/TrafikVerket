package com.hfakhraei.trafikverket;

import com.hfakhraei.trafikverket.dto.ExamPlacesConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AppConfiguration {
    private static final List<ExamPlacesConfig> list = new LinkedList<>();
    private static final Map<Integer, ExamPlacesConfig> map = new HashMap<>();

    static {
        addConfig(0, BuildConfig.SCHEDULER_INTERVAL_02, 1000140);

//        addConfig(1, BuildConfig.SCHEDULER_INTERVAL_10, 1000134);
        addConfig(2, BuildConfig.SCHEDULER_INTERVAL_10, 1000326);
        addConfig(3, BuildConfig.SCHEDULER_INTERVAL_10, 1000132);

        addConfig(4, BuildConfig.SCHEDULER_INTERVAL_30, 1000071);
        addConfig(5, BuildConfig.SCHEDULER_INTERVAL_30, 1000149);
        addConfig(6, BuildConfig.SCHEDULER_INTERVAL_30, 1000038);
        addConfig(7, BuildConfig.SCHEDULER_INTERVAL_30, 1000005);
        addConfig(8, BuildConfig.SCHEDULER_INTERVAL_30, 1000072);
        addConfig(9, BuildConfig.SCHEDULER_INTERVAL_30, 1000329);
        addConfig(10, BuildConfig.SCHEDULER_INTERVAL_30, 1000009);
        addConfig(11, BuildConfig.SCHEDULER_INTERVAL_30, 1000011);
        addConfig(12, BuildConfig.SCHEDULER_INTERVAL_30, 1000001);
    }

    private static void addConfig(Integer order, Integer interval, Integer locationId) {
        ExamPlacesConfig examPlacesConfig =
                new ExamPlacesConfig(order, interval, locationId);
        list.add(examPlacesConfig);
        map.put(examPlacesConfig.getLocationId(), examPlacesConfig);
    }

    public static List<ExamPlacesConfig> getSelectedExamPlaces() {
            return list;
    }

    public static void updateExamPlace(Integer locationId, String locationName, String availableDate) {
        ExamPlacesConfig examPlacesConfig = map.get(locationId);
        examPlacesConfig.setLastCheckTime(LocalDateTime.now());
        examPlacesConfig.setLocationName(locationName);
        LocalDateTime date = LocalDateTime.parse(availableDate,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]"));
        examPlacesConfig.setFirstAvailableTime(date);
    }
}
