package com.example.tmi2.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bus implements Serializable {
    private String busTime;
    private int busNum;

    private static Map<Integer, List<String>> seats;
    private List<String> seat;

    //Constructor
    public Bus() {
    }

    public Bus(HashMap<Integer, List<String>> seats, String busTime, int busNum) {
        this.seats = seats;
        this.busTime = busTime;
        this.busNum = busNum;
    }

//    public String stringTo() {
//        return "Bus : " + busTime +
//    }

    //getter N setter
    public static Map<Integer, List<String>> getSeats() {
        return seats;
    }

    public static void setSeats(Map<Integer, List<String>> seats) {
        Bus.seats = seats;
    }

    public String getBusTime() {
        return busTime;
    }

    public void setBusTime(String busTime) {
        this.busTime = busTime;
    }

    public int getBusNum() {
        return busNum;
    }

    public void setBusNum(int busNum) {
        busNum = busNum;
    }
}
