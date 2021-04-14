package com.example.softwarepatterns;

public class User{
    String email;
    String name;
    String address;
    Boolean admin;


    public User(){
        this.email = null;
        this.name = null;
        this.address = null;
        this.admin = null;
    }

    public User(String email, String name, String address,Boolean admin){
        this.email = email;
        this.name = name;
        this.address = address;
        this.admin = admin;
    }

    public User(String email){
        this.email = email;
        this.name = "";
        this.address = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAdress(String address) {
        this.address = address;
    }

}
