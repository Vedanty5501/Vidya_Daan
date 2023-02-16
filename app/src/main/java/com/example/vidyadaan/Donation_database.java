package com.example.vidyadaan;

public class Donation_database {

    String request_id,donor_id,ngo_id,donation_id;

    public Donation_database() {
    }

    public Donation_database(String request_id, String donor_id, String ngo_id, String donation_id) {
        this.request_id = request_id;
        this.donor_id = donor_id;
        this.ngo_id = ngo_id;
        this.donation_id = donation_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getDonor_id() {
        return donor_id;
    }

    public void setDonor_id(String donor_id) {
        this.donor_id = donor_id;
    }

    public String getNgo_id() {
        return ngo_id;
    }

    public void setNgo_id(String ngo_id) {
        this.ngo_id = ngo_id;
    }

    public String getDonation_id() {
        return donation_id;
    }

    public void setDonation_id(String donation_id) {
        this.donation_id = donation_id;
    }
}
