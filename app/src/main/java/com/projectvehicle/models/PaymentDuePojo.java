package com.projectvehicle.models;

import com.google.gson.annotations.SerializedName;

public class PaymentDuePojo {
    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
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

    public PaymentDuePojo(String id, String title, String description, String number_plate, String fee, String status, String sname, String lat, String lng) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.number_plate = number_plate;
        this.fee = fee;
        this.status = status;
        this.sname = sname;
        this.lat = lat;
        this.lng = lng;

    }

    @SerializedName("pic")
    private String pic;

    @SerializedName("email")
    private String email;

    @SerializedName("image")
    private String image;

    @SerializedName("aname")
    private String aname;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("fee")
    private String fee;

    @SerializedName("number_plate")
    private String number_plate;

    @SerializedName("status")
    private String status;

    @SerializedName("sname")
    private String sname;

    @SerializedName("lat")
    private String lat;

    @SerializedName("lng")
    private String lng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String number_plate) {
        this.number_plate = number_plate;
    }


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }
}
