package dtos.requests;

import lombok.Data;

import java.time.LocalTime;

@Data
public class GenerateVisitorEntryCodeRequest {
    private String residentPhone;
    private String visitorName;
    private String visitorPhone;
    private String purposeOfVisit;
    private LocalTime validTill;

//    public LocalTime getValidTill() {
//        return validTill;
//    }
//
//    public void setValidTill(LocalTime validTill) {
//        this.validTill = validTill;
//    }
//
//    public String getVisitorName() {
//        return visitorName;
//    }
//
//    public void setVisitorName(String visitorName) {
//        this.visitorName = visitorName;
//    }
//
//    public String getResidentPhone() {
//        return residentPhone;
//    }
//
//    public void setResidentPhone(String residentPhone) {
//        this.residentPhone = residentPhone;
//    }
//
//    public String getVisitorPhone() {
//        return visitorPhone;
//    }
//
//    public void setVisitorPhone(String visitorPhone) {
//        this.visitorPhone = visitorPhone;
//    }
//
//    public String getPurposeOfVisit() {
//        return purposeOfVisit;
//    }
//
//    public void setPurposeOfVisit(String purposeOfVisit) {
//        this.purposeOfVisit = purposeOfVisit;
//    }
}
