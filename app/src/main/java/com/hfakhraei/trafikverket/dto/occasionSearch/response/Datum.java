
package com.hfakhraei.trafikverket.dto.occasionSearch.response;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Datum implements Serializable {

    private String cost;
    private List<Occasion> occasions;

}
