package com.example.bashabari;

public class ownerInfo {
    private String Address ,Name , Nid_no, Password, Phone_no;

    public ownerInfo(String address, String name, String nid_no, String password, String phone_no) {
        Name = name;
        Address = address;
        Nid_no = nid_no;
        Phone_no = phone_no;
        Password = password;
    }

    public ownerInfo(){

    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public String getNid_no() {
        return Nid_no;
    }

    public void setNid_no(String nid_no) {
        Nid_no = nid_no;
    }



    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone_no() {
        return Phone_no;
    }

    public void setPhone_no(String phone_no) {
        Phone_no = phone_no;
    }
}
