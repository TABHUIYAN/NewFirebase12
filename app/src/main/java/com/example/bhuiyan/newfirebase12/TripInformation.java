package com.example.bhuiyan.newfirebase12;

/**
 * Created by BHUIYAN on 27/03/2018.
 */

public class TripInformation {
    public String  start;
    public String end;
    public String date;
   public String comments;

    public TripInformation(){

    }

    public TripInformation(String start, String end,String date,String comments) {
        this.start = start;
        this.end = end;
        this.date=date;
        this.comments=comments;
    }
}
