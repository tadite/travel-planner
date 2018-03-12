package edu.nc.travelplanner.dto.travel;

import edu.nc.travelplanner.table.CheckPoint;
import edu.nc.travelplanner.table.PlaceOfResidence;
import edu.nc.travelplanner.table.Travel;

import javax.persistence.Column;

public class TravelPreviewDto {
    private Long travelId;
    private String name;
    private Integer numberOfDays;
    private String cost;
    private Integer fromCountryId;
    private Integer toCountryId;
    private Long fromCityId;
    private Long toCityId;
    private String fromCountryName;
    private String fromCityName;
    private String toCountryName;
    private String toCityName;


    public static TravelPreviewDto fromTravel(Travel travel){
        TravelPreviewDto dto = new TravelPreviewDto();
        dto.setTravelId(travel.getTravelId());
        dto.setName(travel.getName());
        dto.setCost(travel.getCost());
        CheckPoint fromCheckPoint = travel.getFromCheckPoint();
        if (fromCheckPoint!=null){
            PlaceOfResidence placeOfResidence = fromCheckPoint.getPlaceOfResidence();
            if (placeOfResidence!=null){
                dto.setFromCityId(placeOfResidence.getCity()==null?null:placeOfResidence.getCity().getCityId());
                dto.setFromCityName(placeOfResidence.getCity()==null?null:placeOfResidence.getCity().getName());
                dto.setFromCountryId(placeOfResidence.getCountry()==null?null:placeOfResidence.getCountry().getCountryId());
                dto.setFromCountryName(placeOfResidence.getCountry()==null?null:placeOfResidence.getCountry().getName());
            }
        }
        CheckPoint toCheckPoint = travel.getToCheckPoint();
        if (fromCheckPoint!=null){
            PlaceOfResidence placeOfResidence = toCheckPoint.getPlaceOfResidence();
            if (placeOfResidence!=null){
                dto.setToCityId(placeOfResidence.getCity()==null?null:placeOfResidence.getCity().getCityId());
                dto.setToCityName(placeOfResidence.getCity()==null?null:placeOfResidence.getCity().getName());
                dto.setToCountryId(placeOfResidence.getCountry()==null?null:placeOfResidence.getCountry().getCountryId());
                dto.setToCountryName(placeOfResidence.getCountry()==null?null:placeOfResidence.getCountry().getName());
            }
        }
        return dto;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public Integer getFromCountryId() {
        return fromCountryId;
    }

    public void setFromCountryId(Integer fromCountryId) {
        this.fromCountryId = fromCountryId;
    }

    public Integer getToCountryId() {
        return toCountryId;
    }

    public void setToCountryId(Integer toCountryId) {
        this.toCountryId = toCountryId;
    }

    public Long getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(Long fromCityId) {
        this.fromCityId = fromCityId;
    }

    public Long getToCityId() {
        return toCityId;
    }

    public void setToCityId(Long toCityId) {
        this.toCityId = toCityId;
    }

    public long getTravelId() {
        return travelId;
    }

    public void setTravelId(long travelId) {
        this.travelId = travelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getFromCountryName() {
        return fromCountryName;
    }

    public void setFromCountryName(String fromCountryName) {
        this.fromCountryName = fromCountryName;
    }

    public String getFromCityName() {
        return fromCityName;
    }

    public void setFromCityName(String fromCityName) {
        this.fromCityName = fromCityName;
    }

    public String getToCountryName() {
        return toCountryName;
    }

    public void setToCountryName(String toCountryName) {
        this.toCountryName = toCountryName;
    }

    public String getToCityName() {
        return toCityName;
    }

    public void setToCityName(String toCityName) {
        this.toCityName = toCityName;
    }
}
