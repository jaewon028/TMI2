package com.example.tmi2.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Reservation implements Serializable {
    private ArrayList<Integer> seatList;
    private String dept;
    private String dest;
    private String uid;
    private String day;
    private String time;
    private int peopleNum;
    private String key;
    private int fee;
    private String cardNum;
    private String cardPassword;

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardPassword() {
        return cardPassword;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "seatList=" + seatList +
                ", dept='" + dept + '\'' +
                ", dest='" + dest + '\'' +
                ", uid='" + uid + '\'' +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", peopleNum=" + peopleNum +
                ", key='" + key + '\'' +
                ", fee=" + fee +
                '}';
    }

    public Reservation(){

    }

    public Reservation(ArrayList<Integer> seatList, String dept, String dest, String uid, String day, String time, int peopleNum, String key, int fee) {
        this.seatList = seatList;
        this.dept = dept;
        this.dest = dest;
        this.uid = uid;
        this.day = day;
        this.time = time;
        this.peopleNum = peopleNum;
        this.key = key;
        this.fee = fee;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Reservation(ArrayList<Integer> seatList, String dept, String dest, String uid, String day, String time, int peopleNum) {
        this.seatList = seatList;
        this.dept = dept;
        this.dest = dest;
        this.uid = uid;
        this.day = day;
        this.time = time;
        this.peopleNum = peopleNum;
    }

    public ArrayList<Integer> getSeatList() {
        return seatList;
    }

    public void setSeatList(ArrayList<Integer> seatList) {
        this.seatList = seatList;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }
}
