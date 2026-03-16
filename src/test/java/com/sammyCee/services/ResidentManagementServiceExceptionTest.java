package com.sammyCee.services;

import com.sammyCee.data.repositories.ResidentRepo;
import com.sammyCee.dtos.requests.OnboardResidentRequest;
import com.sammyCee.exceptions.ResidentManagementServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResidentManagementServiceExceptionTest {
    private ResidentManagementService residentService;
    private ResidentRepo residentRepo;
    private OnboardResidentRequest onboardResidentRequest;

    @BeforeEach
    public void setUp(){
        residentService = new ResidentManagementService();
        residentRepo = new Residents();
        residentRepo.deleteAll();
        onboardResidentRequest = new OnboardResidentRequest();
    }

    @Test
    public void RegisterResident_ResidentsCountIsOne(){
        assertEquals(0, residentRepo.getTotal());

        onboardResidentRequest.setName("name");
        onboardResidentRequest.setEmail("email");
        onboardResidentRequest.setPhoneNumber("phoneNumber");
        onboardResidentRequest.setHouseAddress("houseAddress");

        residentService.onboardResidents(onboardResidentRequest);

        assertEquals(1, residentRepo.getTotal());
    }

    @Test
    public void RegisterTwoResidentsWithSamePhoneNumber_ExceptionIsThrown(){
        onboardResidentRequest.setName("name");
        onboardResidentRequest.setEmail("email");
        onboardResidentRequest.setPhoneNumber("phoneNumber");
        onboardResidentRequest.setHouseAddress("houseAddress");

        residentService.onboardResidents(onboardResidentRequest);

        OnboardResidentRequest onboardResidentRequest2 = new OnboardResidentRequest();
        onboardResidentRequest2.setName("name2");
        onboardResidentRequest2.setPhoneNumber("phoneNumber");
        onboardResidentRequest2.setHouseAddress("houseAddress");
        onboardResidentRequest2.setEmail("email2");

        assertThrows(ResidentManagementServiceException.class,
                () -> residentService.onboardResidents(onboardResidentRequest2));

        assertEquals(1, residentRepo.getTotal());
    }

    @Test
    public void RegisterTwoResidentsWithSameEmail_ExceptionIsThrown(){
        onboardResidentRequest.setName("name");
        onboardResidentRequest.setEmail("email");
        onboardResidentRequest.setPhoneNumber("phoneNumber");
        onboardResidentRequest.setHouseAddress("houseAddress");

        residentService.onboardResidents(onboardResidentRequest);

        OnboardResidentRequest onboardResidentRequest2 = new OnboardResidentRequest();
        onboardResidentRequest2.setName("name2");
        onboardResidentRequest2.setPhoneNumber("phoneNumber2");
        onboardResidentRequest2.setHouseAddress("houseAddress");
        onboardResidentRequest2.setEmail("email");

        assertThrows(ResidentManagementServiceException.class,
                () -> residentService.onboardResidents(onboardResidentRequest2));

        assertEquals(1, residentRepo.getTotal());
    }

    @Test
    void onboardResidents(){
        onboardResidentRequest.setName("name");
        onboardResidentRequest.setEmail("email");
        onboardResidentRequest.setPhoneNumber("phoneNumber");
        onboardResidentRequest.setHouseAddress("houseAddress");

        var response = residentService.onboardResidents(onboardResidentRequest);

        assertNotNull(response);

    }
    @Test
    void onboardResident_residentIsEnabled(){
        onboardResidentRequest.setName("name");
        onboardResidentRequest.setEmail("email");
        onboardResidentRequest.setPhoneNumber("phoneNumber");
        onboardResidentRequest.setHouseAddress("houseAddress");

        residentService.onboardResidents(onboardResidentRequest);

        var resident = residentRepo.findByPhoneNumber("phoneNumber");

        assertTrue(resident.isEnabled());
    }
    @Test
    void viewAllResidents_returnsAllResidents(){
        onboardResidentRequest.setName("name");
        onboardResidentRequest.setEmail("email");
        onboardResidentRequest.setPhoneNumber("phoneNumber");
        onboardResidentRequest.setHouseAddress("houseAddress");

        residentService.onboardResidents(onboardResidentRequest);

        OnboardResidentRequest onboardResidentRequest2 = new OnboardResidentRequest();
        onboardResidentRequest2.setName("name2");
        onboardResidentRequest2.setEmail("email2");
        onboardResidentRequest2.setPhoneNumber("phoneNumber2");
        onboardResidentRequest2.setHouseAddress("houseAddress2");

        residentService.onboardResidents(onboardResidentRequest2);

        assertEquals(2, residentService.viewAllResidents().size());
    }
    @Test
    void deleteResident_residentDoesNotExist_exceptionThrown(){
        assertThrows(ResidentManagementServiceException.class,
                () -> residentService.deleteResident("unknownPhone"));
    }
    @Test
    void deleteResident_residentIsDeleted(){
        onboardResidentRequest.setName("name");
        onboardResidentRequest.setEmail("email");
        onboardResidentRequest.setPhoneNumber("phoneNumber");
        onboardResidentRequest.setHouseAddress("houseAddress");

        residentService.onboardResidents(onboardResidentRequest);

        assertEquals(1, residentRepo.getTotal());

        residentService.deleteResident("phoneNumber");

        assertEquals(0, residentRepo.getTotal());
    }
    @Test
    void disableResident_residentDoesNotExist_exceptionThrown(){
        assertThrows(ResidentManagementServiceException.class,
                () -> residentService.disableResident("unknownPhone"));
    }
    @Test
    void disableResident_residentIsDisabled(){
        onboardResidentRequest.setName("name");
        onboardResidentRequest.setEmail("email");
        onboardResidentRequest.setPhoneNumber("phoneNumber");
        onboardResidentRequest.setHouseAddress("houseAddress");

        residentService.onboardResidents(onboardResidentRequest);

        residentService.disableResident("phoneNumber");

        var resident = residentRepo.findByPhoneNumber("phoneNumber");

        assertFalse(resident.isEnabled());
    }

}