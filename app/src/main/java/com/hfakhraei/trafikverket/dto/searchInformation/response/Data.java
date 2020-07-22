
package com.hfakhraei.trafikverket.dto.searchInformation.response;

import java.util.List;

@lombok.Data
@SuppressWarnings("unused")
public class Data {

    private Boolean canBookLicence;
    private Object examinationTypeId;
    private List<ExaminationType> examinationTypes;
    private List<Object> hindranceMessage;
    private Object languageId;
    private List<Language> languages;
    private List<LicenceCategory> licenceCategories;
    private long licenceId;
    private List<Licence> licences;
    private Object locationId;
    private List<Location> locations;
    private long occasionChoiceId;
    private List<OccasionChoice> occasionChoices;
    private Object providerLogoPath;
    private Boolean showExaminationType;
    private Boolean showLanguage;
    private Boolean showOccasionChoices;
    private Boolean showTachographType;
    private Boolean showVehicleType;
    private long tachographTypeId;
    private List<TachographType> tachographTypes;
    private long timeIntervalId;
    private List<TimeInterval> timeIntervals;
    private Object vehicleTypeId;
    private List<VehicleType> vehicleTypes;

}
