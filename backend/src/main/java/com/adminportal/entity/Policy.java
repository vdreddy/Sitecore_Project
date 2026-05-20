package com.adminportal.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String policyNumber;

    @Enumerated(EnumType.STRING)
    private PolicyStatus policyStatus;

    @Enumerated(EnumType.STRING)
    private PolicyRole role;

    @Enumerated(EnumType.STRING)
    private PolicyType policyType;

    private String product;
    private LocalDate issueDate;
    private boolean portalVisibility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public enum PolicyStatus {
        ACTIVE, LAPSED, TERMINATED, PENDING
    }

    public enum PolicyRole {
        INSURED, OWNER
    }

    public enum PolicyType {
        TERM, IUL
    }

    public Policy() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public PolicyStatus getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(PolicyStatus policyStatus) {
        this.policyStatus = policyStatus;
    }

    public PolicyRole getRole() {
        return role;
    }

    public void setRole(PolicyRole role) {
        this.role = role;
    }

    public PolicyType getPolicyType() {
        return policyType;
    }

    public void setPolicyType(PolicyType policyType) {
        this.policyType = policyType;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public boolean isPortalVisibility() {
        return portalVisibility;
    }

    public void setPortalVisibility(boolean portalVisibility) {
        this.portalVisibility = portalVisibility;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
