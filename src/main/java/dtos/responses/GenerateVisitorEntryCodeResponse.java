package dtos.responses;

import lombok.Data;

@Data
public class GenerateVisitorEntryCodeResponse {
    private String code;
    private String visitorName;
    private String validTill;
    private String codeType;

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
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
//    public String getValidTill() {
//        return validTill;
//    }
//
//    public void setValidTill(String validTill) {
//        this.validTill = validTill;
//    }
//
//    public String getCodeType() {
//        return codeType;
//    }
//
//    public void setCodeType(String codeType) {
//        this.codeType = codeType;
//    }
}
