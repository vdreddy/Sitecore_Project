package com.adminportal.dto;

import java.util.List;

public class CustomerDetailResponse {

    private PersonalInfo personalInfo;
    private UserInfo userInfo;
    private List<PolicyInfo> policies;
    private boolean registered;

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<PolicyInfo> getPolicies() {
        return policies;
    }

    public void setPolicies(List<PolicyInfo> policies) {
        this.policies = policies;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public static class PersonalInfo {
        private String name;
        private String address;
        private String city;
        private String state;
        private String zip;
        private String country;
        private String dateOfBirth;
        private String ssn;
        private String taxId;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getZip() { return zip; }
        public void setZip(String zip) { this.zip = zip; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getDateOfBirth() { return dateOfBirth; }
        public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
        public String getSsn() { return ssn; }
        public void setSsn(String ssn) { this.ssn = ssn; }
        public String getTaxId() { return taxId; }
        public void setTaxId(String taxId) { this.taxId = taxId; }
    }

    public static class UserInfo {
        private String masterId;
        private String username;
        private String email;
        private String mobileNumber;
        private String userStatus;
        private String disableReason;
        private String creationDate;
        private String lastLoginDate;

        public String getMasterId() { return masterId; }
        public void setMasterId(String masterId) { this.masterId = masterId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getMobileNumber() { return mobileNumber; }
        public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
        public String getUserStatus() { return userStatus; }
        public void setUserStatus(String userStatus) { this.userStatus = userStatus; }
        public String getDisableReason() { return disableReason; }
        public void setDisableReason(String disableReason) { this.disableReason = disableReason; }
        public String getCreationDate() { return creationDate; }
        public void setCreationDate(String creationDate) { this.creationDate = creationDate; }
        public String getLastLoginDate() { return lastLoginDate; }
        public void setLastLoginDate(String lastLoginDate) { this.lastLoginDate = lastLoginDate; }
    }

    public static class PolicyInfo {
        private String policyNumber;
        private String policyStatus;
        private String role;
        private String policyType;
        private String product;
        private String issueDate;
        private boolean portalVisibility;

        public String getPolicyNumber() { return policyNumber; }
        public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }
        public String getPolicyStatus() { return policyStatus; }
        public void setPolicyStatus(String policyStatus) { this.policyStatus = policyStatus; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getPolicyType() { return policyType; }
        public void setPolicyType(String policyType) { this.policyType = policyType; }
        public String getProduct() { return product; }
        public void setProduct(String product) { this.product = product; }
        public String getIssueDate() { return issueDate; }
        public void setIssueDate(String issueDate) { this.issueDate = issueDate; }
        public boolean isPortalVisibility() { return portalVisibility; }
        public void setPortalVisibility(boolean portalVisibility) { this.portalVisibility = portalVisibility; }
    }
}
