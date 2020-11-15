package com.projectvehicle.models;

import com.google.gson.annotations.SerializedName;

public class ComplaintModel {

    @SerializedName("title")
    private String title;


    @SerializedName("problem")
    private String problem;

    @SerializedName("email")
    private String email;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
