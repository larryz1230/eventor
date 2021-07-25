package com.example.eventor;

public class Eventt {
    private String ename, edesc, stime, etime, sday, eday;
    private int id, numjoined;
    public boolean ispublic;

    public Eventt (String ename, String edesc, String stime, String etime, String sday, String eday, int id, int numjoined, boolean ispublic){
        this.ename = ename;
        this.edesc = edesc;
        this.stime = stime;
        this.etime = etime;
        this.sday = sday;
        this.eday = eday;
        this.id = id;
        this.numjoined = numjoined;
        this.ispublic = ispublic;
    }

    public int getId() {
        return id;
    }

    public int getNumjoined() {
        return numjoined;
    }

    public String getEday() {
        return eday;
    }

    public String getEdesc() {
        return edesc;
    }

    public String getEname() {
        return ename;
    }

    public String getEtime() {
        return etime;
    }

    public String getSday() {
        return sday;
    }

    public String getStime() {
        return stime;
    }
}
