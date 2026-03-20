package com.sammyCee.controllers;

import com.sammyCee.dtos.requests.ValidateCodeRequest;
import com.sammyCee.dtos.responses.ApiResponse;
import com.sammyCee.dtos.responses.ValidateCodeResponse;
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

@RequestMapping("/api/gateman")
@RestController
public class GatemanControllers {

    @Autowired
    private GateAccessService gateAccessService;
    @PostMapping("/validate-gatepass")
    public ResponseEntity<ApiResponse> validateGatePass(@RequestBody ValidateCodeRequest validateCodeRequest) {
        try {
            ValidateCodeResponse validateCodeResponse = gateAccessService.validateCode(validateCodeRequest);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponse(true, "Gate pass validated successfully", validateCodeResponse));
        } catch (ResidentManagementServiceException | GatePassDoesNotExistException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
