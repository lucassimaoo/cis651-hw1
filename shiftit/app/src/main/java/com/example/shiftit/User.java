package com.example.shiftit;

import com.google.firebase.database.ServerValue;

import java.util.List;

public class User {

    private String uid;
    private String name;
    private String email;
    private String phone;
    private List<String> hospitals;
    private String profession;
    private Object timestamp = ServerValue.TIMESTAMP;

    public User(String uid, String name, String email, String phone, List<String> hospitals, String profession) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.hospitals = hospitals;
        this.profession = profession;
    }

    public User() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<String> hospitals) {
        this.hospitals = hospitals;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Object getTimestamp() {
        return timestamp;
    }
}
