package data.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Resident {
    private String name;
    private String id;
    private String phoneNumber;
    private String houseAddress;
    private String emailAddress;
    private LocalDateTime dateRegistered = LocalDateTime.now();
    private boolean isEnabled;

    public String getDateRegistered() {
        return dateRegistered.format(DateTimeFormatter.ofPattern("EEE dd MMM yyyy hh:mm a"));
    }

}