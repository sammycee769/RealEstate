package utils;

import data.models.GatePass;
import data.models.Pass;
import data.models.Resident;
import dtos.requests.GenerateEntryCodeRequest;
import dtos.requests.GenerateExitCodeRequest;
import dtos.requests.GenerateVisitorEntryCodeRequest;
import dtos.requests.OnboardResidentRequest;
import dtos.responses.*;

import java.time.LocalTime;

public class Mapper {
    public static Resident map(OnboardResidentRequest onboardResidentRequest) {
        Resident resident = new Resident();
        resident.setName(onboardResidentRequest.getName());
        resident.setEmailAddress(onboardResidentRequest.getEmail());
        resident.setPhoneNumber(onboardResidentRequest.getPhoneNumber());
        resident.setHouseAddress(onboardResidentRequest.getHouseAddress());
        return resident;
    }
    public static OnboardResidentResponse map(Resident resident) {
        OnboardResidentResponse response = new OnboardResidentResponse();

        response.setResidentName(resident.getName());
        response.setResidentId(resident.getId());
        response.setDateRegistered(resident.getDateRegistered().toString());

        return response;
    }
    public static GatePass map(GenerateEntryCodeRequest getEntryCodeRequest) {
        GatePass gatePass = new GatePass();
        gatePass.setResidentPhone(getEntryCodeRequest.getPhoneNumber());
        gatePass.setValidTill(getEntryCodeRequest.getValidTill());
        gatePass.setPassType(Pass.ENTRY);
        return gatePass;
    }
    public static GenerateEntryCodeResponse map(GatePass gatePass, Resident residentValidated) {
        GenerateEntryCodeResponse generateEntryCodeResponse = new GenerateEntryCodeResponse();
        generateEntryCodeResponse.setCode(gatePass.getCode());
        generateEntryCodeResponse.setCodeType(Pass.ENTRY.name());
        generateEntryCodeResponse.setResidentName(residentValidated.getName());
        generateEntryCodeResponse.setDestination("Home");
        generateEntryCodeResponse.setValidTill(gatePass.getValidTill().toString());
        return generateEntryCodeResponse;
    }
    public static GatePass map(GenerateVisitorEntryCodeRequest generateVisitorEntryCodeRequest) {
        GatePass gatePass = new GatePass();
        gatePass.setValidTill(generateVisitorEntryCodeRequest.getValidTill());
        gatePass.setResidentPhone(generateVisitorEntryCodeRequest.getResidentPhone());
        gatePass.setPassType(Pass.ENTRY);
        gatePass.getVisitor().setName(generateVisitorEntryCodeRequest.getVisitorName());
        gatePass.getVisitor().setPhoneNumber(generateVisitorEntryCodeRequest.getVisitorPhone());
        gatePass.getVisitor().setPurposeOfComing(generateVisitorEntryCodeRequest.getPurposeOfVisit());
        return gatePass;
    }
    public static GenerateVisitorEntryCodeResponse map(GatePass gatePass) {
        GenerateVisitorEntryCodeResponse generateVisitorEntryCodeResponse = new GenerateVisitorEntryCodeResponse();
        generateVisitorEntryCodeResponse.setCode(gatePass.getCode());
        generateVisitorEntryCodeResponse.setVisitorName(gatePass.getVisitor().getName());
        generateVisitorEntryCodeResponse.setValidTill(gatePass.getValidTill().toString());
        generateVisitorEntryCodeResponse.setCodeType(Pass.ENTRY.name());

        return generateVisitorEntryCodeResponse;
    }
    public static ValidateCodeResponse mapToValidateCodeResponse(GatePass gatePass, Resident resident) {
        ValidateCodeResponse response = new ValidateCodeResponse();

        response.setResidentName(resident.getName());
        response.setVisitorsName(gatePass.getVisitor().getName());
        response.setCodeType(gatePass.getPassType().toString());

        if (gatePass.getValidTill().isBefore(LocalTime.now())) {
            response.setValid(false);
        } else {
            response.setValid(true);
        }

        return response;
    }
    public static void map(GenerateExitCodeRequest generateExitCodeRequest, GatePass gatePass) {
        gatePass.setValidTill(generateExitCodeRequest.getValidTill());
        gatePass.setPassType(Pass.EXIT);
    }

    public static GenerateExitCodeResponse mapToGenerateExitCode(GatePass gatePass) {
        GenerateExitCodeResponse generateExitCodeResponse = new GenerateExitCodeResponse();
        generateExitCodeResponse.setCode(gatePass.getCode());
        generateExitCodeResponse.setPassType(gatePass.getPassType().toString());
        generateExitCodeResponse.setValidTill(gatePass.getValidTill().toString());
        return generateExitCodeResponse;
    }



}
