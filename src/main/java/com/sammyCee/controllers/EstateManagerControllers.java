package com.sammyCee.controllers;

import com.sammyCee.dtos.requests.OnboardResidentRequest;
import com.sammyCee.dtos.responses.ApiResponse;
import com.sammyCee.dtos.responses.OnboardResidentResponse;
import com.sammyCee.dtos.responses.ViewResidentResponse;
import com.sammyCee.exceptions.ResidentManagementServiceException;
import com.sammyCee.services.ResidentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estatemanagement")
public class EstateManagerControllers {
    @Autowired
    private ResidentManagementService residentManagementService;

    @PostMapping("/onboard-resident")
    public ResponseEntity<ApiResponse> onBoardResident(@RequestBody OnboardResidentRequest request) {
        try {
            OnboardResidentResponse residentResponse = residentManagementService.onboardResidents(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "Resident onboarded successfully", residentResponse));
        } catch (ResidentManagementServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PatchMapping("/residents/disable-resident/{residentId}")
    public ResponseEntity<ApiResponse> disableResident( @PathVariable String phoneNumber){
        try{
            residentManagementService.disableResident(phoneNumber.trim());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "Resident disabled successfully", null));
        }catch (ResidentManagementServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false,e.getMessage(),null));
        }
    }

    @PatchMapping("/residents/enable-resident/{residentId}")
    public ResponseEntity<ApiResponse> enableResident(@PathVariable String phoneNumber){
        try{
            residentManagementService.enableResident(phoneNumber.trim());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "Resident enabled successfully", null));
        }catch (ResidentManagementServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false,e.getMessage(),null));
        }
    }

    @DeleteMapping("/residents/delete-resident")
    public ResponseEntity<ApiResponse> deleteResident(@RequestBody String phoneNumber){
        try{
            residentManagementService.deleteResident(phoneNumber.trim());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "Resident deleted successfully", null));
        }catch (ResidentManagementServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false,e.getMessage(),null));
        }
    }

    @GetMapping("/residents")
    public ResponseEntity<ApiResponse> viewAllResidents() {
        try {
            List<ViewResidentResponse> residents = residentManagementService.viewAllResidents();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponse(true, "Residents retrieved successfully", residents));
        } catch (ResidentManagementServiceException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }



}
