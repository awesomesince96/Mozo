package com.example.mozo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "username",
        "gender",
        "email",
        "dob",
        "age",
        "status",
        "lat",
        "lng",
        "last_active",
        "min_age",
        "max_age",
        "max_range",
        "interested_gender",
        "pagination",
        "que",
        "image_url"
})

public class User implements Serializable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("username")
    private String username;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("email")
    private String email;

    @JsonProperty("dob")
    private String dob;

    @JsonProperty("age")
    private String age;

    @JsonProperty("status")
    private String status;

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("lng")
    private String lng;

    @JsonProperty("last_active")
    private String last_active;

    @JsonProperty("min_age")
    private String min_age;

    @JsonProperty("max_age")
    private String max_age;

    @JsonProperty("max_range")
    private String max_range;

    @JsonProperty("interested_gender")
    private String interested_gender;

    @JsonProperty("pagination")
    private String pagination;

    @JsonProperty("que")
    private ArrayList<String> que;

    @JsonProperty("image_url")
    private ArrayList<String> image_url;

    public User() {
    }

    public User(String name){
        this.name = name;
    }

    public User(String name, String username, String gender, String email, String dob, String age, String status, String lat, String lng, String last_active, String min_age, String max_age, String max_range, String interested_gender, String pagination, ArrayList<String> que, ArrayList<String> image_url) {
        this.name = name;
        this.username = username;
        this.gender = gender;
        this.email = email;
        this.dob = dob;
        this.age = age;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
        this.last_active = last_active;
        this.min_age = min_age;
        this.max_age = max_age;
        this.max_range = max_range;
        this.interested_gender = interested_gender;
        this.pagination = pagination;
        this.que = que;
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLast_active() {
        return last_active;
    }

    public void setLast_active(String last_active) {
        this.last_active = last_active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getdob() {
        return dob;
    }

    public void setdob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getInterested_gender() {
        return interested_gender;
    }

    public void setInterested_gender(String interested_gender) {
        this.interested_gender = interested_gender;
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

    public String getMin_age() {
        return min_age;
    }

    public void setMin_age(String min_age) {
        this.min_age = min_age;
    }

    public String getMax_age() {
        return max_age;
    }

    public void setMax_age(String max_age) {
        this.max_age = max_age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMax_range() {
        return max_range;
    }

    public void setMax_range(String max_range) {
        this.max_range = max_range;
    }

    public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public ArrayList<String> getQue() {
        return que;
    }

    public void setQue(ArrayList<String> que) {
        this.que = que;
    }

    public ArrayList<String> getImage_url() {
        return image_url;
    }

    public void setImage_url(ArrayList<String> image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", age='" + age + '\'' +
                ", status='" + status + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", last_active='" + last_active + '\'' +
                ", min_age='" + min_age + '\'' +
                ", max_age='" + max_age + '\'' +
                ", max_range='" + max_range + '\'' +
                ", interested_gender='" + interested_gender + '\'' +
                ", pagination='" + pagination + '\'' +
                ", que=" + que +
                ", image_url=" + image_url +
                '}';
    }
}
