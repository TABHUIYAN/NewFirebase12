package com.example.bhuiyan.newfirebase12;

/**
 * Created by BHUIYAN on 28/03/2018.
 */

public class Trip {
    private String start;

    private String end;

    private String date;

    private String comments;
    public Trip() {

    }
    public Trip(String start, String end, String date, String comments) {
        this.start = start;
        this.end = end;
        this.date = date;
        this.comments = comments;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
