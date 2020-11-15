package com.projectvehicle.models;

import com.google.gson.annotations.SerializedName;

public class CarsModel {

    @SerializedName("image")
    private String image;


    @SerializedName("make")
    private String make;

    @SerializedName("model")
    private String model;

    @SerializedName("color")
    private String color;

    @SerializedName("number_plate")
    private String number_plate;

    @SerializedName("cid")
    private String cid;

    @SerializedName("email")
    private String email;

    @SerializedName("status")
    private String status;

    @SerializedName("c_status")
    private String c_status;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String number_plate) {
        this.number_plate = number_plate;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getC_status() {
        return c_status;
    }

    public void setC_status(String c_status) {
        this.c_status = c_status;
    }
}
