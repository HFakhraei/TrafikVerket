
package com.hfakhraei.trafikverket.dto.occasionSearch.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Occasion implements Serializable {

    private String cost;
    private String costText;
    private String date;
    private Duration duration;
    private long examinationCategory;
    private Object examinationId;
    private long examinationTypeId;
    private Boolean increasedFee;
    private Object isEducatorBooking;
    private Boolean isLateCancellation;
    private Boolean isOutsideValidDuration;
    private long languageId;
    private long locationId;
    private String locationName;
    private String name;
    private long occasionChoiceId;
    private Object placeAddress;
    private Object properties;
    private long tachographTypeId;
    private String time;
    private long vehicleTypeId;

}
