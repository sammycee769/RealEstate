package dtos.responses;

import lombok.Data;

@Data
public class GenerateExitCodeResponse {
    private String code;
    private String passType;
    private String validTill;

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getPassType() {
//        return passType;
//    }
//
//    public void setPassType(String passType) {
//        this.passType = passType;
//    }
//
//    public String getValidTill() {
//        return validTill;
//    }
//
//    public void setValidTill(String validTill) {
//        this.validTill = validTill;
//    }
}
