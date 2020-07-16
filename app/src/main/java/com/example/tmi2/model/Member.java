package com.example.tmi2.model;

public class Member {
    //고객 정보 ( email, password ) 객체
    private String email;
    private String password;
    private String dept;
    private String dest;
    private String date;
    private int numppl;
    private String selectedSeats;

    public Member() {
    }

    public Member(String email, String password, String dept, String dest,
                  String date, int numppl, String selectedSeats) {
        this.email = email;
        this.password = password;
        this.dept = dept;
        this.dest= dest;
        this.date = date;
        this.numppl = numppl;
        this.selectedSeats = selectedSeats;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getDept() {
        return dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDest() { return dest; }
    public void setDest(String dest) { this.dest = dest; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getNumppl() { return numppl; }
    public void setNumppl(int numppl) { this.numppl = numppl; }

    public String getSelectedSeats() { return selectedSeats ;}
    public void setSelectedSeats(String selectedSeats) { this.selectedSeats = selectedSeats; }


}