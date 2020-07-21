
package com.hfakhraei.trafikverket.dto.response;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class OccasionResponse implements Serializable {

    private List<Datum> data;
    private long status;
    private String url;

}
