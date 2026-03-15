package data.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class GatePass {
    private String code;
    private String id;
    private String residentId;
    private String residentPhone;
    private Visitor visitor = new Visitor();
    private LocalDateTime createdAt =  LocalDateTime.now();
    private LocalDateTime expirationDate;
    private boolean isValid = true;
    private LocalTime validTill;
    private Pass passType;
    private boolean activate = false;



//    public boolean isActivate() {
//        return activate;
//    }
//
//    public void setActivate(boolean activate) {
//        this.activate = activate;
//    }
//    public String getResidentPhone() {
//        return residentPhone;
//    }
//
//    public void setResidentPhone(String residentPhone) {
//        this.residentPhone = residentPhone;
//    }
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getResidentId() {
//        return residentId;
//    }
//
//    public void setResidentId(String residentId) {
//        this.residentId = residentId;
//    }
//
//    public Visitor getVisitor() {
//        return visitor;
//    }
//
//    public void setVisitor(Visitor visitor) {
//        this.visitor = visitor;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public LocalDateTime getExpirationDate() {
//        return expirationDate;
//    }
//
//    public void setExpirationDate(LocalDateTime expirationDate) {
//        this.expirationDate = expirationDate;
//    }
//
//    public boolean isValid() {
//        return isValid;
//    }
//
//    public void setValid(boolean valid) {
//        isValid = valid;
//    }
//
//    public LocalTime getValidTill() {
//        return validTill;
//    }
//
//    public void setValidTill(LocalTime validTill) {
//        this.validTill = validTill;
//    }
//
//    public Pass getPassType() {
//        return passType;
//    }
//
//    public void setPassType(Pass passType) {
//        this.passType = passType;
//    }
}


