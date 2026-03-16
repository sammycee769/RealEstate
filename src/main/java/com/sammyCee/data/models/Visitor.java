package com.sammyCee.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Visitor {
    private String id;
    private String name;
    private String purposeOfComing;
    private String phoneNumber;

//    public String getPurposeOfComing() {
//        return purposeOfComing;
//    }
//
//    public void setPurposeOfComing(String purposeOfComing) {
//        this.purposeOfComing = purposeOfComing;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
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
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
}