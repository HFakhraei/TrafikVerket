
package com.hfakhraei.trafikverket.dto.searchInformation.response;

import java.util.List;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class LicenceCategory {

    private List<Licence> licences;
    private String name;

}
