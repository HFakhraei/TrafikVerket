
package com.hfakhraei.trafikverket.dto.searchInformation.request;

import java.util.List;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class BookingSession {

    private long bookingModeId;
    private List<Object> excludeExaminationCategories;
    private Boolean ignoreBookingHindrance;
    private Boolean ignoreDebt;
    private String licenceId;
    private Boolean paymentIsActive;
    private Object paymentReference;
    private Object paymentUrl;
    private long rescheduleTypeId;
    private String socialSecurityNumber;

}
