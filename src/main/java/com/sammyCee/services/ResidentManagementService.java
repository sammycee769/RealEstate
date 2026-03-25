package com.sammyCee.services;

import com.sammyCee.data.models.Resident;
import com.sammyCee.data.repositories.ResidentRepo;
import com.sammyCee.dtos.requests.OnboardResidentRequest;
import com.sammyCee.dtos.responses.OnboardResidentResponse;
import com.sammyCee.dtos.responses.ViewResidentResponse;
import com.sammyCee.exceptions.ResidentManagementServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import static com.sammyCee.utils.Mapper.map;
import static com.sammyCee.utils.Mapper.mapViewResident;

@Service
public class ResidentManagementService {
    @Autowired
    private ResidentRepo residentRepo ;

    public OnboardResidentResponse onboardResidents(OnboardResidentRequest onboardResidentRequest) {
        validateResidentDuplicates(onboardResidentRequest.getPhoneNumber(),onboardResidentRequest.getEmail());
        Resident resident= map(onboardResidentRequest);
        resident.setEnabled(true);
        residentRepo.save(resident);

        return  map(resident);
    }
    public void disableResident(String phoneNumber){
        Resident resident = validateResidentExists(phoneNumber);
        resident.setEnabled(false);
        residentRepo.save(resident);
    }
    public void enableResident(String phoneNumber){
        Resident resident = validateResidentExists(phoneNumber);
        validateIsEnabled(resident);
        resident.setEnabled(true);
        residentRepo.save(resident);
    }



    public List<ViewResidentResponse> viewAllResidents() {
        List<ViewResidentResponse> viewResidentResponses = new ArrayList<>();
        for (Resident resident : residentRepo.findAll()) {
            viewResidentResponses.add(mapViewResident(resident));
        }
        return viewResidentResponses;
    }
    public  void deleteResident(String phoneNumber) {
        Resident resident= validateResidentExists(phoneNumber);
        residentRepo.delete(resident);
    }

    private static void validateIsEnabled(Resident resident) {
        if(resident.isEnabled()){
            throw new ResidentManagementServiceException("Resident is enabled");
        }
    }

    private Resident validateResidentExists(String phoneNumber ){
        Resident existing = residentRepo.findByPhoneNumber(phoneNumber);
        if(existing == null){
            throw new ResidentManagementServiceException("Resident not found");
        }
        return existing;
    }
    private void validateResidentDuplicates(String phoneNumber,String email) {
        Resident existing = residentRepo.findByEmail(email);
        Resident exist = residentRepo.findByPhoneNumber(phoneNumber);
        if(existing != null || exist != null){
            throw new ResidentManagementServiceException("Resident already exists");
        }
    }

}
