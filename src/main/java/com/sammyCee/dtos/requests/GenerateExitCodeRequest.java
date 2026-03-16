package com.sammyCee.dtos.requests;

import lombok.Data;

import java.time.LocalTime;

@Data
public class GenerateExitCodeRequest {
    private String gatePassId;
    private LocalTime validTill;

//    public String getGatePassId() {
//        return gatePassId;
//    }
//
//    public void setGatePassId(String gatePassId) {
//        this.gatePassId = gatePassId;
//    }
//
//    public LocalTime getValidTill() {
//        return validTill;
//    }
//
//    public void setValidTill(LocalTime validTill) {
//        this.validTill = validTill;
//    }
}
