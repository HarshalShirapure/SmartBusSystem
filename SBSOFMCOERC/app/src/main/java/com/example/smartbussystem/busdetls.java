package com.example.smartbussystem;

public class busdetls {
    public String dname,dno,root;

    public busdetls()
    {

    }
    public busdetls(String dname, String dno, String root) {
        this.dname = dname;
        this.dno = dno;
        this.root = root;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDno() {
        return dno;
    }

    public void setDno(String dno) {
        this.dno = dno;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
