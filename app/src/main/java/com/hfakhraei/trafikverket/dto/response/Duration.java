
package com.hfakhraei.trafikverket.dto.response;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Duration implements Serializable {

    private String end;
    private String start;

}
