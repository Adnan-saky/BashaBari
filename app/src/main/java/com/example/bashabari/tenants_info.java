package com.example.bashabari;

public class tenants_info {

    private String Name, Username, Nid_no, Phone_no, Password;

    public tenants_info(String name, String username, String nid_no, String phone_no, String password) {
        Name = name;
        Username = username;
        Nid_no = nid_no;
        Phone_no = phone_no;
        Password = password;

}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getNid_no() {
        return Nid_no;
    }

    public void setNid_no(String nid_no) {
        Nid_no = nid_no;
    }

    public String getPhone_no() {
        return Phone_no;
    }

    public void setPhone_no(String phone_no) {
        Phone_no = phone_no;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

