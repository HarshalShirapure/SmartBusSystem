package com.example.smartbussystem;

public class driver_problem {
    String bus_num,busdriver,dproblem;

    public driver_problem() {
    }
    public driver_problem(String dproblem) {
        this.dproblem=dproblem;
    }

    public String getBus_num() {
        return bus_num;
    }

    public void setBus_num(String bus_num) {
        this.bus_num = bus_num;
    }

    public String getBusdriver() {
        return busdriver;
    }

    public void setBusdriver(String busdriver) {
        this.busdriver = busdriver;
    }

    public String getDproblem() {
        return dproblem;
    }

    public void setDproblem(String dproblem) {
        this.dproblem = dproblem;
    }
}
