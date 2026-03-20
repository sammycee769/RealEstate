package com.sammyCee.services;

import com.sammyCee.data.models.GatePass;
import com.sammyCee.data.models.Resident;
import com.sammyCee.data.repositories.GatePassRepo;
import com.sammyCee.data.repositories.ResidentRepo;
import com.sammyCee.dtos.requests.GenerateEntryCodeRequest;
import com.sammyCee.dtos.requests.GenerateExitCodeRequest;
import com.sammyCee.dtos.requests.GenerateVisitorEntryCodeRequest;
import com.sammyCee.dtos.requests.ValidateCodeRequest;
import com.sammyCee.dtos.responses.GenerateEntryCodeResponse;
import com.sammyCee.dtos.responses.GenerateExitCodeResponse;
import com.sammyCee.dtos.responses.GenerateVisitorEntryCodeResponse;
import com.sammyCee.dtos.responses.ValidateCodeResponse;
import com.sammyCee.exceptions.GatePassDoesNotExistException;
import com.sammyCee.exceptions.ResidentManagementServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.sammyCee.utils.Mapper.*;

@Service
public class GateAccessService {
    @Autowired
    private ResidentRepo residentRepo;
    @Autowired
    private GatePassRepo gatePassRepo ;

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
            throw new GatePassDoesNotExistException("Invalid Gate Pass Code");
        }    if(!existing.isValid()){
            throw new GatePassDoesNotExistException("Gate Pass has expired");
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
            throw new GatePassDoesNotExistException("Invalid Gate Pass Code");
        }
        if(existing.isActivate())
            throw new GatePassDoesNotExistException("Code Already Used");

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
        return gatePassRepo.findById(gatePassId)
                .orElseThrow(() -> new GatePassDoesNotExistException("Gate pass not found"));
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
