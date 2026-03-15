package dtos.requests;

import lombok.Data;

import java.time.LocalTime;

@Data
public class GenerateEntryCodeRequest {
    private String phoneNumber;
    private LocalTime validTill;


//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
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
