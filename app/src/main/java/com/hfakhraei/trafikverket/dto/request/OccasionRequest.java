package com.hfakhraei.trafikverket.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OccasionRequest {
    private BookingSession bookingSession;
    private OccasionBundleQuery occasionBundleQuery;
}
