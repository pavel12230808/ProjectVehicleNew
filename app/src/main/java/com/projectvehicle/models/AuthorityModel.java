package com.projectvehicle.models;

import com.google.gson.annotations.SerializedName;

public class AuthorityModel {
    @SerializedName("name")
    private String name ;

    @SerializedName("emailid")
    private String emailid ;

    @SerializedName("phno")
    private String phno ;

    @SerializedName("username")
    private String username ;

    @SerializedName("photo")
    private String photo ;

    @SerializedName("pwd")
    private String pwd ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
