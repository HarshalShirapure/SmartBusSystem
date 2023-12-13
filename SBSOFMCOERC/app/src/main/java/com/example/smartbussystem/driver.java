package com.example.smartbussystem;

public class driver {
    public String busid,name,EmailID,Location_Current,driver_Problem;
    public driver() {
    }

    public driver(String busid, String name,String EmailID,String Location_Current) {
        this.busid = busid;
        this.name = name;
        this.EmailID=EmailID;
        this.Location_Current=Location_Current;


    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getLocation_Current() {
        return Location_Current;
    }

    public void setLocation_Current(String location_Current) {
        Location_Current = location_Current;
    }

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getdriver_Problem() {
        return driver_Problem;
    }

    public void setdriver_Problem(String driver_Problem_) {
        this.driver_Problem = driver_Problem_;
    }

}
