package com.sammyCee.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Document
@Data
public class Resident {
    private String name;
    private String id;
    private String phoneNumber;
    private String houseAddress;
    private String email;
    private LocalDateTime dateRegistered = LocalDateTime.now();
    private boolean isEnabled;

    public String getDateRegistered() {
        return dateRegistered.format(DateTimeFormatter.ofPattern("EEE dd MMM yyyy hh:mm a"));
    }

}