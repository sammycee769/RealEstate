package com.sammyCee.controllers;

import com.sammyCee.dtos.requests.GenerateEntryCodeRequest;
import com.sammyCee.dtos.requests.GenerateExitCodeRequest;
import com.sammyCee.dtos.requests.GenerateVisitorEntryCodeRequest;
import com.sammyCee.dtos.responses.ApiResponse;
import com.sammyCee.dtos.responses.GenerateEntryCodeResponse;
import com.sammyCee.dtos.responses.GenerateExitCodeResponse;
import com.sammyCee.dtos.responses.GenerateVisitorEntryCodeResponse;
import com.sammyCee.exceptions.GatePassDoesNotExistException;
import com.sammyCee.exceptions.ResidentManagementServiceException;
import com.sammyCee.services.GateAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resident")
public class ResidentControllers {
    @Autowired
    private GateAccessService gateAccessService;

    @PostMapping("/generate-resident-entry-code")
    public ResponseEntity<ApiResponse> generateResidentEntryCode(@RequestBody GenerateEntryCodeRequest generateEntryCodeRequest) {
        try {
            GenerateEntryCodeResponse generateEntryCodeResponse = gateAccessService.generateResidentEntryCode(generateEntryCodeRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Entry code generated successfully", generateEntryCodeResponse));
        } catch (GatePassDoesNotExistException | ResidentManagementServiceException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PostMapping("/generate-visitor-entry-code")
    public ResponseEntity<ApiResponse> generateVisitorEntryCode(@RequestBody GenerateVisitorEntryCodeRequest generateVisitorEntryCodeRequest) {
        try {
            GenerateVisitorEntryCodeResponse generateVisitorEntryCodeResponse = gateAccessService.generateVisitorEntryCode(generateVisitorEntryCodeRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Visitor entry code generated successfully", generateVisitorEntryCodeResponse));
        } catch (GatePassDoesNotExistException | ResidentManagementServiceException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PostMapping("/generate-exit-code")
    public ResponseEntity<ApiResponse> generateResidentExitCode(@RequestBody GenerateExitCodeRequest generateExitCodeRequest) {
        try {
            GenerateExitCodeResponse generateExitCodeResponse = gateAccessService.generateExtCode(generateExitCodeRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Exit code generated successfully", generateExitCodeResponse));
        } catch (GatePassDoesNotExistException | ResidentManagementServiceException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

}
