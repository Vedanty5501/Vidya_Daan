package com.example.vidyadaan;

public class requestModel {
    private String requestid,requestdesc;

    requestModel(String requestid,String requestdesc){
        this.requestid=requestid;
        this.requestdesc=requestdesc;
    }

    public String getRequestid() {
        return requestid;
    }

    public String getRequestdesc() {
        return requestdesc;
    }
}
