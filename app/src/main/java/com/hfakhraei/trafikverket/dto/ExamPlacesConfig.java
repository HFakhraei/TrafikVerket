package com.hfakhraei.trafikverket.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import lombok.Data;

@Data
public class ExamPlacesConfig {
    private Integer order;
    private Integer schedulerPeriod;
    private Integer locationId;
    private String locationName;
    private LocalDateTime firstAvailableTime;
    private LocalDateTime lastCheckTime;

    public ExamPlacesConfig(Integer order, Integer schedulerPeriod, Integer locationId) {
        this.order = order;
        this.schedulerPeriod = schedulerPeriod;
        this.locationId = locationId;
        this.lastCheckTime = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
    }

    public boolean isValidityTimePassed(){
        if (firstAvailableTime == null) {
            firstAvailableTime=LocalDateTime.now().plus(100, ChronoUnit.DAYS);
            return true;
        }
        return lastCheckTime
                .plus(schedulerPeriod, ChronoUnit.MILLIS)
                .compareTo(LocalDateTime.now()) < 0;
    }

    public String getFirstAvailableTimeToString() {
        if (firstAvailableTime == null)
            return "";
        return firstAvailableTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getLastCheckTimeToString() {
        if(lastCheckTime == null)
            return "";
        return lastCheckTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
