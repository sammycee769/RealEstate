package com.sammyCee.services;

import com.sammyCee.data.models.GatePass;
import com.sammyCee.data.repositories.GatePassRepo;
import com.sammyCee.data.repositories.ResidentRepo;
import com.sammyCee.dtos.requests.*;
import com.sammyCee.dtos.responses.*;
import com.sammyCee.exceptions.GatePassDoesNotExistException;
import com.sammyCee.exceptions.ResidentManagementServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GateAccessServiceTest {
    @Autowired
    private GateAccessService gateAccessService;
    @Autowired
    private ResidentManagementService residentService;
    @Autowired
    private ResidentRepo residentRepo;
    @Autowired
    private GatePassRepo gatePassRepo;

    @BeforeEach
    public void setUp() {
        residentRepo.deleteAll();
        gatePassRepo.deleteAll();
    }

    private void registerResident(String name, String email, String phone, String address) {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName(name);
        request.setEmail(email);
        request.setPhoneNumber(phone);
        request.setHouseAddress(address);
        residentService.onboardResidents(request);
    }

    private GenerateEntryCodeResponse generateEntryCode(String phoneNumber, LocalTime validTill) {
        GenerateEntryCodeRequest request = new GenerateEntryCodeRequest();
        request.setPhoneNumber(phoneNumber);
        request.setValidTill(validTill);
        return gateAccessService.generateResidentEntryCode(request);
    }

    private GenerateVisitorEntryCodeResponse generateVisitorEntryCode(String residentPhone, String visitorName, String visitorPhone, String purposeOfVisit, LocalTime validTill) {
        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setResidentPhone(residentPhone);
        request.setVisitorName(visitorName);
        request.setVisitorPhone(visitorPhone);
        request.setPurposeOfVisit(purposeOfVisit);
        request.setValidTill(validTill);
        return gateAccessService.generateVisitorEntryCode(request);
    }


    @Test
    void generateResidentEntryCode_residentDoesNotExist_exceptionThrown() {
        GenerateEntryCodeRequest request = new GenerateEntryCodeRequest();
        request.setPhoneNumber("unknownPhone");
        request.setValidTill(LocalTime.now().plusHours(1));

        assertThrows(ResidentManagementServiceException.class,
                () -> gateAccessService.generateResidentEntryCode(request));
    }

    @Test
    void generateResidentEntryCode_validResident_codeIsReturned() {
        registerResident("name", "email", "phoneNumber", "houseAddress");

        GenerateEntryCodeResponse response = generateEntryCode("phoneNumber", LocalTime.now().plusHours(1));

        assertNotNull(response.getCode());
        assertFalse(response.getCode().isBlank());
    }

    @Test
    void generateResidentEntryCode_validResident_codeTypeIsEntry() {
        registerResident("name", "email", "phoneNumber", "houseAddress");

        GenerateEntryCodeResponse response = generateEntryCode("phoneNumber", LocalTime.now().plusHours(1));

        assertEquals("ENTRY", response.getCodeType());
    }

    @Test
    void generateResidentEntryCode_validResident_destinationIsHome() {
        registerResident("name", "email", "phoneNumber", "houseAddress");

        GenerateEntryCodeResponse response = generateEntryCode("phoneNumber", LocalTime.now().plusHours(1));

        assertEquals("Home", response.getDestination());
    }

    @Test
    void generateResidentEntryCode_validResident_residentNameIsSet() {
        registerResident("name", "email", "phoneNumber", "houseAddress");

        GenerateEntryCodeResponse response = generateEntryCode("phoneNumber", LocalTime.now().plusHours(1));

        assertEquals("name", response.getResidentName());
    }


    @Test
    void generateVisitorEntryCode_residentDoesNotExist_exceptionThrown() {
        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setResidentPhone("unknownPhone");
        request.setVisitorName("visitorName");
        request.setVisitorPhone("visitorPhone");
        request.setPurposeOfVisit("purpose");
        request.setValidTill(LocalTime.now().plusHours(1));

        assertThrows(ResidentManagementServiceException.class,
                () -> gateAccessService.generateVisitorEntryCode(request));
    }

    @Test
    void generateVisitorEntryCode_validResident_codeIsReturned() {
        registerResident("name", "email", "phoneNumber", "houseAddress");

        GenerateVisitorEntryCodeResponse response = generateVisitorEntryCode(
                "phoneNumber", "visitorName", "visitorPhone", "purpose", LocalTime.now().plusHours(1));

        assertNotNull(response.getCode());
        assertFalse(response.getCode().isBlank());
    }

    @Test
    void generateVisitorEntryCode_validResident_visitorNameIsSet() {
        registerResident("name", "email", "phoneNumber", "houseAddress");

        GenerateVisitorEntryCodeResponse response = generateVisitorEntryCode(
                "phoneNumber", "visitorName", "visitorPhone", "purpose", LocalTime.now().plusHours(1));

        assertEquals("visitorName", response.getVisitorName());
    }

    @Test
    void generateVisitorEntryCode_validResident_codeTypeIsEntry() {
        registerResident("name", "email", "phoneNumber", "houseAddress");

        GenerateVisitorEntryCodeResponse response = generateVisitorEntryCode(
                "phoneNumber", "visitorName", "visitorPhone", "purpose", LocalTime.now().plusHours(1));

        assertEquals("ENTRY", response.getCodeType());
    }


    @Test
    void validateCode_codeDoesNotExist_exceptionThrown() {
        ValidateCodeRequest request = new ValidateCodeRequest();
        request.setCode("WRONG1");
        request.setCodeType("ENTRY");

        assertThrows(GatePassDoesNotExistException.class,
                () -> gateAccessService.validateCode(request));
    }

    @Test
    void validateCode_correctCodeWrongType_exceptionThrown() {
        registerResident("name", "email", "phoneNumber", "houseAddress");
        GenerateEntryCodeResponse entryResponse = generateEntryCode("phoneNumber", LocalTime.now().plusHours(1));

        ValidateCodeRequest request = new ValidateCodeRequest();
        request.setCode(entryResponse.getCode());
        request.setCodeType("EXIT");

        assertThrows(GatePassDoesNotExistException.class,
                () -> gateAccessService.validateCode(request));
    }

    @Test
    void validateCode_codeAlreadyUsed_exceptionThrown() {
        registerResident("name", "email", "phoneNumber", "houseAddress");
        GenerateEntryCodeResponse entryResponse = generateEntryCode("phoneNumber", LocalTime.now().plusHours(1));

        ValidateCodeRequest request = new ValidateCodeRequest();
        request.setCode(entryResponse.getCode());
        request.setCodeType("ENTRY");

        gateAccessService.validateCode(request);

        assertThrows(GatePassDoesNotExistException.class,
                () -> gateAccessService.validateCode(request));
    }

    @Test
    void validateCode_validCodeFutureTime_gatePassIsActivatedAndResponseIsValid() {
        registerResident("name", "email", "phoneNumber", "houseAddress");
        GenerateEntryCodeResponse entryResponse = generateEntryCode("phoneNumber", LocalTime.of(23, 59));

        ValidateCodeRequest request = new ValidateCodeRequest();
        request.setCode(entryResponse.getCode());
        request.setCodeType("ENTRY");

        ValidateCodeResponse response = gateAccessService.validateCode(request);

        assertNotNull(response);
        assertTrue(response.isValid());
        assertEquals("name", response.getResidentName());
        assertEquals("ENTRY", response.getCodeType());

        assertTrue(gatePassRepo.findByCode(entryResponse.getCode()).isActivate());
    }

    @Test
    void validateCode_validCodeExpiredTime_responseIsNotValid() {
        registerResident("name", "email", "phoneNumber", "houseAddress");

        GenerateEntryCodeResponse entryResponse = generateEntryCode("phoneNumber", LocalTime.of(0, 1));

        ValidateCodeRequest request = new ValidateCodeRequest();
        request.setCode(entryResponse.getCode());
        request.setCodeType("ENTRY");

        ValidateCodeResponse response = gateAccessService.validateCode(request);

        assertFalse(response.isValid());
    }


    @Test
    void disableCode_residentDoesNotExist_exceptionThrown() {
        registerResident("name", "email", "phoneNumber", "houseAddress");
        GenerateEntryCodeResponse entryResponse = generateEntryCode("phoneNumber", LocalTime.now().plusHours(1));
        String gatePassId = gatePassRepo.findByCode(entryResponse.getCode()).getId();

        assertThrows(ResidentManagementServiceException.class,
                () -> gateAccessService.disableCode("unknownPhone", gatePassId));
    }

    @Test
    void disableCode_gatePassDoesNotExist_exceptionThrown() {
        registerResident("name", "email", "phoneNumber", "houseAddress");

        assertThrows(GatePassDoesNotExistException.class,
                () -> gateAccessService.disableCode("phoneNumber", "unknownPassId"));
    }

    @Test
    void disableCode_validCall_gatePassIsInvalid() {
        registerResident("name", "email", "phoneNumber", "houseAddress");
        GenerateEntryCodeResponse entryResponse = generateEntryCode("phoneNumber", LocalTime.now().plusHours(1));
        String gatePassId = gatePassRepo.findByCode(entryResponse.getCode()).getId();

        gateAccessService.disableCode("phoneNumber", gatePassId);

        GatePass gatePass = gatePassRepo.findById(gatePassId)
                .orElseThrow(() -> new AssertionError("GatePass not found"));

        assertFalse(gatePass.isValid());
    }


    @Test
    void extendTime_gatePassDoesNotExist_exceptionThrown() {
        assertThrows(GatePassDoesNotExistException.class,
                () -> gateAccessService.extendTime(30, "unknownPassId"));
    }

    @Test
    void extendTime_gatePassIsInvalid_validTillIsNotExtended() {
        registerResident("name", "email", "phoneNumber", "houseAddress");
        GenerateEntryCodeResponse entryResponse = generateEntryCode("phoneNumber", LocalTime.now().plusHours(1));
        String gatePassId = gatePassRepo.findByCode(entryResponse.getCode()).getId();

        gateAccessService.disableCode("phoneNumber", gatePassId);

        GatePass gatePassBefore = gatePassRepo.findById(gatePassId)
                .orElseThrow(() -> new AssertionError("GatePass not found"));
        LocalTime validTillBefore = gatePassBefore.getValidTill();

        gateAccessService.extendTime(30, gatePassId);

        GatePass gatePassAfter = gatePassRepo.findById(gatePassId)
                .orElseThrow(() -> new AssertionError("GatePass not found"));
        LocalTime validTillAfter = gatePassAfter.getValidTill();

        assertEquals(validTillBefore, validTillAfter);
    }

    @Test
    void extendTime_validGatePass_validTillIsExtended() {
        registerResident("name", "email", "phoneNumber", "houseAddress");
        GenerateEntryCodeResponse entryResponse = generateEntryCode("phoneNumber", LocalTime.now().plusHours(1));
        String gatePassId = gatePassRepo.findByCode(entryResponse.getCode()).getId();

        GatePass gatePassBefore = gatePassRepo.findById(gatePassId)
                .orElseThrow(() -> new AssertionError("GatePass not found"));
        LocalTime validTillBefore = gatePassBefore.getValidTill();

        gateAccessService.extendTime(30, gatePassId);

        GatePass gatePassAfter = gatePassRepo.findById(gatePassId)
                .orElseThrow(() -> new AssertionError("GatePass not found"));
        LocalTime validTillAfter = gatePassAfter.getValidTill();
        assertEquals(validTillBefore.plusMinutes(30), validTillAfter);
    }


    @Test
    void generateExitCode_gatePassDoesNotExist_exceptionThrown() {
        GenerateExitCodeRequest request = new GenerateExitCodeRequest();
        request.setGatePassId("unknownPassId");
        request.setValidTill(LocalTime.now().plusHours(1));

        assertThrows(GatePassDoesNotExistException.class,
                () -> gateAccessService.generateExtCode(request));
    }

    @Test
    void generateExitCode_validGatePass_exitCodeIsSet() {
        registerResident("name", "email", "phoneNumber", "houseAddress");
        GenerateEntryCodeResponse entryResponse = generateEntryCode("phoneNumber", LocalTime.now().plusHours(1));
        String gatePassId = gatePassRepo.findByCode(entryResponse.getCode()).getId();

        GenerateExitCodeRequest request = new GenerateExitCodeRequest();
        request.setGatePassId(gatePassId);
        request.setValidTill(LocalTime.now().plusHours(1));

        GenerateExitCodeResponse response = gateAccessService.generateExtCode(request);

        assertNotNull(response.getCode());
        assertFalse(response.getCode().isBlank());
    }

    @Test
    void generateExitCode_validGatePass_passTypeIsExit() {
        registerResident("name", "email", "phoneNumber", "houseAddress");
        GenerateEntryCodeResponse entryResponse = generateEntryCode("phoneNumber", LocalTime.now().plusHours(1));
        String gatePassId = gatePassRepo.findByCode(entryResponse.getCode()).getId();

        GenerateExitCodeRequest request = new GenerateExitCodeRequest();
        request.setGatePassId(gatePassId);
        request.setValidTill(LocalTime.now().plusHours(1));

        GenerateExitCodeResponse response = gateAccessService.generateExtCode(request);

        assertEquals("EXIT", response.getPassType());
    }

    @Test
    void generateResidentEntryCode_residentIsDisabled_exceptionThrown(){
        registerResident("name", "email", "phoneNumber", "houseAddress");
        residentService.disableResident("phoneNumber");

        GenerateEntryCodeRequest request = new GenerateEntryCodeRequest();
        request.setPhoneNumber("phoneNumber");
        request.setValidTill(LocalTime.now().plusHours(1));

        assertThrows(ResidentManagementServiceException.class,
                () -> gateAccessService.generateResidentEntryCode(request));
    }
    @Test
    void generateVisitorEntryCode_residentIsDisabled_exceptionThrown(){
        registerResident("name", "email", "phoneNumber", "houseAddress");
        residentService.disableResident("phoneNumber");

        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setResidentPhone("phoneNumber");
        request.setVisitorName("visitorName");
        request.setVisitorPhone("visitorPhone");
        request.setPurposeOfVisit("purpose");
        request.setValidTill(LocalTime.now().plusHours(1));

        assertThrows(ResidentManagementServiceException.class,
                () -> gateAccessService.generateVisitorEntryCode(request));
    }
}