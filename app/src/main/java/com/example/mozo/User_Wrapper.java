package com.example.mozo;

import java.util.ArrayList;

public class User_Wrapper {

    private String id;
    private String name;
    private String username;
    private String gender;
    private String email;
    private String last_active;
    private String status;
    private String dob;
    private String age;
    private String interested_gender;
    private String lat;
    private String lng;
    private String min_age;
    private String max_age;
    private String range;
    private ArrayList<String> que;

    public User_Wrapper() {
    }

    public User_Wrapper(String id, String name, String username, String gender, String email, String last_active, String status, String dob, String age, String interested_gender, String lat, String lng, String min_age, String max_age, String range, ArrayList<String> que) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.gender = gender;
        this.email = email;
        this.last_active = last_active;
        this.status = status;
        this.dob = dob;
        this.age = age;
        this.interested_gender = interested_gender;
        this.lat = lat;
        this.lng = lng;
        this.min_age = min_age;
        this.max_age = max_age;
        this.range = range;
        this.que = que;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public ArrayList<String> getQue() {
        return que;
    }

    public void setQue(ArrayList<String> que) {
        this.que = que;
    }

    @Override
    public String toString() {
        return "User_Wrapper{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", last_active='" + last_active + '\'' +
                ", status='" + status + '\'' +
                ", dob='" + dob + '\'' +
                ", age='" + age + '\'' +
                ", interested_gender='" + interested_gender + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", min_age='" + min_age + '\'' +
                ", max_age='" + max_age + '\'' +
                ", range='" + range + '\'' +
                ", que=" + que +
                '}';
    }
}
