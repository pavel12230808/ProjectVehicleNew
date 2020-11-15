package com.projectvehicle.models;

import com.google.gson.annotations.SerializedName;

public class StationDetailsPOJO {

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public StationDetailsPOJO(String station_name, String lat, String lng) {
        this.station_name = station_name;
        this.lat = lat;
        this.lng = lng;
    }

    @SerializedName("station_name")
    private String station_name;


    @SerializedName("lat")
    private String lat;

    @SerializedName("lng")
    private String lng;



}
