
package com.hfakhraei.trafikverket.dto.searchInformation.response;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Address {

    private Object careOf;
    private String city;
    private String streetAddress1;
    private Object streetAddress2;
    private String zipCode;

}
