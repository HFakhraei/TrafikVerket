
package com.hfakhraei.trafikverket.dto.searchInformation.response;

import java.util.List;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Language {

    private long id;
    private List<Object> locationIds;
    private String name;

}
