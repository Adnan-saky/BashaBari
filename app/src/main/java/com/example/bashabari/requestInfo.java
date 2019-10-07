package com.example.bashabari;

public class requestInfo {
    private String Date, Name, Owner, Phone_no, SolveStat, Text;

    public requestInfo() {
    }

    public requestInfo(String date, String name, String owner, String phone_no, String solveStat, String text) {
        Date = date;
        Name = name;
        Phone_no = phone_no;
        SolveStat = solveStat;
        Text = text;
        Owner = owner;
    }

    public String getDate() { return Date; }

    public void setDate(String date) { Date = date; }

    public String getName() { return Name; }

    public void setName(String name) { Name = name; }

    public String getPhone_no() { return Phone_no; }

    public void setPhone_no(String phone_no) { Phone_no = phone_no; }

    public String getSolveStat() { return SolveStat; }

    public void setSolveStat(String solveStat) { SolveStat = solveStat; }

    public String getText() { return Text; }

    public void setText(String text) { Text = text; }

    public String getOwner() { return Owner; }

    public void setOwner(String owner) { Owner = owner; }
}
