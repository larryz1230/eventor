package com.example.eventor;

public class event {
    private String eventname, date, time;

    public event(String eventname, String date, String time){
        this.eventname = eventname;
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getEventname() {
        return eventname;
    }

}
