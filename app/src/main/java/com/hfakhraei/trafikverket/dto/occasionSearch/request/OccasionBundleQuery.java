package com.hfakhraei.trafikverket.dto.occasionSearch.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OccasionBundleQuery {
    private String startDate;
    private Integer locationId;
    private Integer languageId;
    private Integer tachographTypeId;
    private Integer occasionChoiceId;
    private Integer examinationTypeId;
}
