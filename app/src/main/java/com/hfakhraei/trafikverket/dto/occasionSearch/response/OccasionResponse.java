
package com.hfakhraei.trafikverket.dto.occasionSearch.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class OccasionResponse implements Serializable {

    private List<Datum> data;
    private long status;
    private String url;

    private LocalDateTime fetchTime = LocalDateTime.now();
}
