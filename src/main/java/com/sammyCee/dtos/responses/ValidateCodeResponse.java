package com.sammyCee.dtos.responses;

import lombok.Data;

@Data
public class ValidateCodeResponse {
    private String residentName;
    private String visitorsName;
    private String codeType;
    private boolean isValid;

//    public String getResidentName() {
//        return residentName;
//    }
//
//    public void setResidentName(String residentName) {
//        this.residentName = residentName;
//    }
//
//    public String getVisitorsName() {
//        return visitorsName;
//    }
//
//    public void setVisitorsName(String visitorsName) {
//        this.visitorsName = visitorsName;
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
//    public boolean isValid() {
//        return isValid;
//    }
//
//    public void setValid(boolean valid) {
//        isValid = valid;
//    }
}
