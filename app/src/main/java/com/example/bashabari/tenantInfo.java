package com.example.bashabari;

public class tenantInfo {
    private String Address ,Name , Nid_no, Owner, Password, Phone_no;

    public tenantInfo() {
    }

    public tenantInfo(String address, String name, String nid_no, String owner, String password, String phone_no) {
        Address = address;
        Name = name;
        Nid_no = nid_no;
        Owner = owner;
        Password = password;
        Phone_no = phone_no;
    }

    public String getAddress() { return Address; }

    public void setAddress(String address) { Address = address; }

    public String getName() { return Name; }

    public void setName(String name) { Name = name; }

    public String getNid_no() { return Nid_no; }

    public void setNid_no(String nid_no) { Nid_no = nid_no; }

    public String getOwner() { return Owner; }

    public void setOwner(String owner) { Owner = owner; }

    public String getPassword() { return Password; }

    public void setPassword(String password) { Password = password; }

    public String getPhone_no() { return Phone_no; }

    public void setPhone_no(String phone_no) { Phone_no = phone_no; }
}
