package services;

import data.models.GatePass;
import data.models.Resident;
import data.repositories.GatePassRepo;
import data.repositories.GatePasses;
import data.repositories.ResidentRepo;
import data.repositories.Residents;
import dtos.requests.GenerateEntryCodeRequest;
import dtos.requests.GenerateExitCodeRequest;
import dtos.requests.GenerateVisitorEntryCodeRequest;
import dtos.requests.ValidateCodeRequest;
import dtos.responses.GenerateEntryCodeResponse;
import dtos.responses.GenerateExitCodeResponse;
import dtos.responses.GenerateVisitorEntryCodeResponse;
import dtos.responses.ValidateCodeResponse;
import exceptions.GatePassDoesNotExist;
import exceptions.ResidentManagementServiceException;

import java.util.Random;

import static utils.Mapper.*;

public class GateAccessService {
    private ResidentRepo residentRepo = new Residents();
    private GatePassRepo gatePassRepo = new GatePasses();

    public String disableCode(String phoneNumber,String gatePassId){
        validateResident(phoneNumber);
        GatePass existing = validateGatePass(gatePassId);
        existing.setValid(false);
        gatePassRepo.save(existing);
        return "Code has been disabled";
    }
    public GenerateEntryCodeResponse generateResidentEntryCode(GenerateEntryCodeRequest getEntryCodeRequest){
        Resident residentValidated = validateResident(getEntryCodeRequest.getPhoneNumber());
        checkResidentEnability(residentValidated);
        GatePass gatePass = map(getEntryCodeRequest);
        gatePass.setCode(generateCode());
        gatePassRepo.save(gatePass);
        return map(gatePass,residentValidated);
    }

    public GenerateVisitorEntryCodeResponse generateVisitorEntryCode(GenerateVisitorEntryCodeRequest generateVisitorEntryCodeRequest){
        Resident residentValidated = validateResident(generateVisitorEntryCodeRequest.getResidentPhone());
        checkResidentEnability(residentValidated);
        GatePass gatePass = map(generateVisitorEntryCodeRequest);
        gatePass.setCode(generateCode());
        gatePassRepo.save(gatePass);
        return map(gatePass);

    }

    public String extendTime(int timeInMinutes, String gatePassId){
        GatePass existing = validateGatePass(gatePassId);
        if(existing.isValid()){
            existing.setValidTill(existing.getValidTill().plusMinutes(timeInMinutes));
            gatePassRepo.save(existing);
        }
        return "Extended successfully";
    }

    public ValidateCodeResponse validateCode(ValidateCodeRequest validateCodeRequest){
        GatePass existing = gatePassRepo.findByCode(validateCodeRequest.getCode());
        if(existing == null){
            throw new GatePassDoesNotExist("Invalid Gate Pass Code");
        }
        confirmCode(validateCodeRequest, existing);
        existing.setActivate(true);
        gatePassRepo.save(existing);
        Resident resident = validateResident(existing.getResidentPhone());
        return mapToValidateCodeResponse(existing, resident);
    }

    public GenerateExitCodeResponse generateExtCode(GenerateExitCodeRequest generateExitCodeRequest){
        GatePass gatePass = validateGatePass(generateExitCodeRequest.getGatePassId());
        gatePass.setCode(generateCode());
        map(generateExitCodeRequest, gatePass);
        gatePassRepo.save(gatePass);
        return mapToGenerateExitCode(gatePass);
    }

    private static void confirmCode(ValidateCodeRequest validateCodeRequest, GatePass existing) {
        if (!existing.getCode().equals(validateCodeRequest.getCode()) || !existing.getPassType().toString().equals(validateCodeRequest.getCodeType())){
            throw new GatePassDoesNotExist("Invalid Gate Pass Code");
        }
        if(existing.isActivate())
            throw new GatePassDoesNotExist("Code Already Used");

    }

    private String generateCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        boolean isUnique = false;

        while (!isUnique) {
            code = new StringBuilder();
            for (int count = 0; count < 6; count++) {
                int index = random.nextInt(characters.length());
                code.append(characters.charAt(index));
            }


            isUnique = gatePassRepo.findByCode(code.toString()) == null;
        }

        return code.toString();
    }
    private GatePass validateGatePass(String gatePassId) {
        GatePass existing = gatePassRepo.findById(gatePassId);
        if(existing == null){
            throw new GatePassDoesNotExist("Gate pass not found");
        }
        return existing;
    }

    private Resident validateResident(String phoneNumber) {
        Resident existingResident = residentRepo.findByPhoneNumber(phoneNumber);
        if(existingResident == null){
            throw new ResidentManagementServiceException("Resident not found");
        }
        return existingResident;
    }

    private void checkResidentEnability(Resident resident){
        if(!resident.isEnabled()){
            throw new ResidentManagementServiceException("Resident is disabled");
        }
    }

}
