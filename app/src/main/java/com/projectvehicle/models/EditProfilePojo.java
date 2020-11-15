package com.projectvehicle.models;

import com.google.gson.annotations.SerializedName;

public class EditProfilePojo {
    @SerializedName("first_name")
    private String first_name ;

    @SerializedName("last_name")
    private String last_name ;

    @SerializedName("phonenumber")
    private String phonenumber ;

    @SerializedName("pwd")
    private String pwd ;

    @SerializedName("emailid")
    private String emailid ;

    @SerializedName("id")
    private String id ;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
