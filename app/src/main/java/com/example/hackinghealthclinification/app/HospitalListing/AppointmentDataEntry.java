package com.example.hackinghealthclinification.app.HospitalListing;

import java.util.Date;

/**
 * Created by miantorno on 2/22/14.
 */
public class AppointmentDataEntry {

    private String name;
    private String userid;
    private String address;
    private String date;
    private String doctor;
    private String hospital;
    private String phone;


    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private AppointmentDataEntry() { }

    AppointmentDataEntry (String Name, String UserID, String address, Date date, String doctor, String hospital, int phone, String status) {
        Name = name;
        UserID = userid;
        address = address;
        date = date;
        doctor = doctor;
        hospital = hospital;
        phone = phone;
    }

    public String getname() {
        return name;
    }

    public String getuserid() {
        return userid;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getHospital() {
        return hospital;
    }

    public String getPhone() {
        return phone;
    }


}
