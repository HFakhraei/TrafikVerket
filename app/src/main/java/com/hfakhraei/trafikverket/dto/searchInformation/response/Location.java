
package com.hfakhraei.trafikverket.dto.searchInformation.response;

import java.util.List;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Location {

    private Address address;
    private Coordinates coordinates;
    private List<ExaminationCategory> examinationCategories;
    private long id;
    private Location location;
    private String name;

}
