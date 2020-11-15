package com.projectvehicle.models;

import com.google.gson.annotations.SerializedName;

public class MyProfilePOJO {

    @SerializedName("image")
    private String image;


    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;


    public MyProfilePOJO(String image, String name, String email) {
        this.setImage(image);
        this.setName(name);
        this.setEmail(email);

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
