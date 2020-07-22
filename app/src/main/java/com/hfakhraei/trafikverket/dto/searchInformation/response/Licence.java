
package com.hfakhraei.trafikverket.dto.searchInformation.response;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Licence {

    private String category;
    private String description;
    private String icon;
    private long id;
    private String languageKeyDescription;
    private String languageKeyName;
    private String name;
    private long sortOrder;

}
