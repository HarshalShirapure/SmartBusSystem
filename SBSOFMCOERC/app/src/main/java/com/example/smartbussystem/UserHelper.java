package com.example.smartbussystem;

public class UserHelper {
    String Fullname,Mobileno1,Mobileno2,Email,pw,repw,pickup_point,yearbranch,total_instalment,Total_Fees,BusNumber;



    public UserHelper() {
    }

    public  UserHelper(String fullname, String mobileno1, String mobileno2, String email, String pw, String repw,String yearbranch,String pickup_point,String total_instalment,String total_Fees,String BusNumber) {
        Fullname = fullname;
        Mobileno1 = mobileno1;
        Mobileno2 = mobileno2;
        Email = email;
        this.pw = pw;
        this.repw = repw;
        this.yearbranch=yearbranch;
        this.pickup_point=pickup_point;
        this.total_instalment=total_instalment;
        this.Total_Fees=total_Fees;
        this.BusNumber=BusNumber;
        }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getMobileno1() {
        return Mobileno1;
    }

    public void setMobileno1(String mobileno1) {
        Mobileno1 = mobileno1;
    }

    public String getMobileno2() {
        return Mobileno2;
    }

    public void setMobileno2(String mobileno2) {
        Mobileno2 = mobileno2;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getRepw() {
        return repw;
    }

    public void setRepw(String repw) {
        this.repw = repw;
    }

    public String getPickup_point() {
        return pickup_point;
    }

    public void setPickup_point(String pickup_point) {
        this.pickup_point = pickup_point;
    }

    public String getYearbranch() {
        return yearbranch;
    }

    public void setYearbranch(String yearbranch) {
        this.yearbranch = yearbranch;
    }

    public String getTotal_instalment() {
        return total_instalment;
    }

    public void setTotal_instalment(String total_instalment) {
        this.total_instalment = total_instalment;
    }

    public String getTotal_Fees() {
        return Total_Fees;
    }

    public void setTotal_Fees(String total_Fees) {
        Total_Fees = total_Fees;
    }

    public String getBusNumber() {
        return BusNumber;
    }

    public void setBusNumber(String Bus_Number) {
        BusNumber = Bus_Number;
    }
}
