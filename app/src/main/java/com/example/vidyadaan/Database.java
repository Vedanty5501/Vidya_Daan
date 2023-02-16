package com.example.vidyadaan;

public class Database {
    String name,dob,email,phone,pass,id,reward,pin;


    public Database() {
    }

    public Database(String name,String id, String dob, String email, String phone,String pin, String pass,String reward) {
        this.name = name;
        this.dob = dob;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.pin = pin;
        this.pass = pass;
        this.reward = reward;
    }



    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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
