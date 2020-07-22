package com.hfakhraei.trafikverket.dto.occasionSearch.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingSession {
    private String socialSecurityNumber;
    private Integer licenceId;
    private Integer bookingModeId;
    private Boolean ignoreDebt;
    private Boolean ignoreBookingHindrance;
    private List<String> excludeExaminationCategories;
    private Integer rescheduleTypeId;
    private Boolean paymentIsActive;
    private String paymentReference;
    private String paymentUrl;
}
