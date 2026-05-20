package com.adminportal.dto;

public class CustomerSearchResult {

    private String masterId;
    private String policyOwnerName;
    private String policyNumber;
    private String policyStatus;
    private String role;
    private String address;
    private String username;

    public CustomerSearchResult() {
    }

    public CustomerSearchResult(String masterId, String policyOwnerName, String policyNumber,
                                 String policyStatus, String role, String address, String username) {
        this.masterId = masterId;
        this.policyOwnerName = policyOwnerName;
        this.policyNumber = policyNumber;
        this.policyStatus = policyStatus;
        this.role = role;
        this.address = address;
        this.username = username;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getPolicyOwnerName() {
        return policyOwnerName;
    }

    public void setPolicyOwnerName(String policyOwnerName) {
        this.policyOwnerName = policyOwnerName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
