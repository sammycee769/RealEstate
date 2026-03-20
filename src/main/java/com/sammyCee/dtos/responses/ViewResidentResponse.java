package com.sammyCee.dtos.responses;

import lombok.Data;

@Data
public class ViewResidentResponse {
    private String name;
    private String phoneNumber;
    private String houseAddress;
    private String email;
    private boolean isEnabled;
}