package com.adminportal.controller;

import com.adminportal.dto.*;
import com.adminportal.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<CustomerSearchResult>> searchCustomers(
            @RequestBody CustomerSearchRequest request) {
        List<CustomerSearchResult> results = customerService.searchCustomers(request);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{masterId}")
    public ResponseEntity<CustomerDetailResponse> getCustomerDetail(
            @PathVariable String masterId) {
        CustomerDetailResponse response = customerService.getCustomerDetail(masterId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDetailResponse> registerUser(
            @Valid @RequestBody RegistrationRequest request) {
        CustomerDetailResponse response = customerService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{masterId}/profile")
    public ResponseEntity<CustomerDetailResponse> updateProfile(
            @PathVariable String masterId,
            @RequestBody ProfileUpdateRequest request) {
        CustomerDetailResponse response = customerService.updateProfile(masterId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{masterId}/enable")
    public ResponseEntity<CustomerDetailResponse> enableUser(
            @PathVariable String masterId) {
        CustomerDetailResponse response = customerService.enableUser(masterId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{masterId}/disable")
    public ResponseEntity<CustomerDetailResponse> disableUser(
            @PathVariable String masterId,
            @Valid @RequestBody DisableRequest request) {
        CustomerDetailResponse response = customerService.disableUser(masterId, request.getReason());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{masterId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String masterId) {
        customerService.deleteUser(masterId);
        return ResponseEntity.noContent().build();
    }
}
