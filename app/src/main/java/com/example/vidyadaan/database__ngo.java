package com.example.vidyadaan;

public class database__ngo {
    String name,id,email,phone,pass,address,pin;

    public database__ngo() {
    }

    public database__ngo(String name, String id, String email, String phone, String pass, String address, String pin) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.pass = pass;
        this.address = address;
        this.pin = pin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
