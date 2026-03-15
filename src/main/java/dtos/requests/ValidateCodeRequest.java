package dtos.requests;

import lombok.Data;

@Data
public class ValidateCodeRequest {
    private String codeType;
    private String code;

//    public String getCodeType() {
//        return codeType;
//    }
//
//    public void setCodeType(String codeType) {
//        this.codeType = codeType;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
}
