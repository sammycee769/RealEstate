package com.sammyCee.dtos.responses;

import lombok.Data;

@Data
public class GenerateEntryCodeResponse {
    private String code;
    private String residentName;
    private String codeType;
    private String validTill;
    private String destination;

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getResidentName() {
//        return residentName;
//    }
//
//    public void setResidentName(String residentName) {
//        this.residentName = residentName;
//    }
//
//    public String getCodeType() {
//        return codeType;
//    }
//
//    public void setCodeType(String codeType) {
//        this.codeType = codeType;
//    }
//
//    public String getValidTill() {
//        return validTill;
//    }
//
//    public void setValidTill(String validTill) {
//        this.validTill = validTill;
//    }
//
//    public String getDestination() {
//        return destination;
//    }
//
//    public void setDestination(String destination) {
//        this.destination = destination;
//    }
}
