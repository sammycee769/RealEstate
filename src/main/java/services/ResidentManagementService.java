package services;

import data.models.Resident;
import data.repositories.ResidentRepo;
import data.repositories.Residents;
import dtos.requests.OnboardResidentRequest;
import dtos.responses.OnboardResidentResponse;
import exceptions.ResidentManagementServiceException;


import java.util.List;

import static utils.Mapper.map;

public class ResidentManagementService {
    private ResidentRepo residentRepo = new Residents();

    public OnboardResidentResponse onboardResidents(OnboardResidentRequest onboardResidentRequest) {
        validateResidentDuplicates(onboardResidentRequest.getPhoneNumber(),onboardResidentRequest.getEmail());
        Resident resident= map(onboardResidentRequest);
        residentRepo.save(resident);
        resident.setEnabled(true);

        return  map(resident);
    }
    public void disableResident(String phoneNumber){
        Resident resident = validateResidentExists(phoneNumber);
        resident.setEnabled(false);
    }
    public List<Resident> viewAllResidents() {
        return residentRepo.findAll();
    }
    public  void deleteResident(String phoneNumber) {
        Resident resident= validateResidentExists(phoneNumber);
        residentRepo.delete(resident);
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
