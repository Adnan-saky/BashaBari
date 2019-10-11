package com.example.bashabari;

public class noticeInfo {
    private String Date, Name, Phone_no, Text;

    public noticeInfo(String date, String name, String phone_no, String text) {
        Date = date;
        Name = name;
        Phone_no = phone_no;
        Text = text;
    }

    public noticeInfo() {
    }

    public String getDate() { return Date; }

    public void setDate(String date) { Date = date; }

    public String getName() { return Name; }

    public void setName(String name) { Name = name; }

    public String getPhone_no() { return Phone_no; }

    public void setPhone_no(String phone_no) { Phone_no = phone_no; }

    public String getText() { return Text; }

    public void setText(String text) { Text = text; }
}
