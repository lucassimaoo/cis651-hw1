package com.example.shiftit;

import com.google.firebase.database.ServerValue;

import java.util.Date;

public class Shift {

    private String uid;
    private String id;
    private Date date;
    private Integer hours;
    private String hospital;
    private String takerUid;
    private String profession;
    private Object timestamp = ServerValue.TIMESTAMP;

    public Shift(String uid, Date date, Integer hours, String hospital, String profession) {
        this.uid = uid;
        this.date = date;
        this.hours = hours;
        this.hospital = hospital;
        this.profession = profession;
    }

    public Shift() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public String getTakerUid() {
        return takerUid;
    }

    public void setTakerUid(String takerUid) {
        this.takerUid = takerUid;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
