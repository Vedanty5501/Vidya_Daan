package com.example.vidyadaan;

public class Database_request {
    String id,Request_id,type,description,donation_requests,pin;

    public Database_request(String id,String Request_id, String type, String description, String donation_requests, String pin) {
        this.id = id;
        this.Request_id = Request_id;
        this.type = type;
        this.description = description;
        this.donation_requests = donation_requests;
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Database_request() {
    }

    public String getRequest_id() {
        return Request_id;
    }

    public void setRequest_id(String request_id) {
        Request_id = request_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDonation_requests() {
        return donation_requests;
    }

    public void setDonation_requests(String donation_requests) {
        this.donation_requests = donation_requests;
    }
}
