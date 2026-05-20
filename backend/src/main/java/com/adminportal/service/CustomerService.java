package com.adminportal.service;

import com.adminportal.dto.*;
import com.adminportal.entity.Customer;
import com.adminportal.entity.Customer.CustomerType;
import com.adminportal.entity.Customer.UserStatus;
import com.adminportal.entity.Policy;
import com.adminportal.repository.CustomerRepository;
import com.adminportal.repository.PolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PolicyRepository policyRepository;
    private final AuditService auditService;

    public CustomerService(CustomerRepository customerRepository,
                           PolicyRepository policyRepository,
                           AuditService auditService) {
        this.customerRepository = customerRepository;
        this.policyRepository = policyRepository;
        this.auditService = auditService;
    }

    public List<CustomerSearchResult> searchCustomers(CustomerSearchRequest request) {
        List<Customer> customers;
        String searchType = request.getSearchType();

        switch (searchType) {
            case "SSN_BIRTHYEAR":
                customers = customerRepository.findBySsnAndBirthYear(request.getSsn(), request.getBirthYear());
                break;
            case "TAX_ID":
                customers = customerRepository.findByTaxId(request.getTaxId());
                break;
            case "POLICY_NUMBER":
                Optional<Policy> policy = policyRepository.findByPolicyNumber(request.getPolicyNumber());
                customers = policy.map(p -> List.of(p.getCustomer())).orElse(Collections.emptyList());
                break;
            case "NAME_ADDRESS":
                customers = customerRepository.findByNameAndAddress(
                        request.getFirstName() != null ? request.getFirstName() : "",
                        request.getLastName() != null ? request.getLastName() : "",
                        request.getAddress() != null ? request.getAddress() : "");
                break;
            case "USERNAME":
                customers = customerRepository.findByUsername(request.getUsername());
                break;
            case "EMAIL":
                customers = customerRepository.findByEmailIgnoreCase(request.getEmail());
                break;
            default:
                customers = Collections.emptyList();
        }

        return customers.stream()
                .flatMap(customer -> {
                    List<Policy> policies = policyRepository.findByCustomerId(customer.getId());
                    if (policies.isEmpty()) {
                        CustomerSearchResult result = new CustomerSearchResult();
                        result.setMasterId(customer.getMasterId());
                        result.setPolicyOwnerName(customer.getFirstName() + " " + customer.getLastName());
                        result.setAddress(formatAddress(customer));
                        result.setUsername(customer.getUsername());
                        return List.of(result).stream();
                    }
                    return policies.stream().map(policy -> {
                        CustomerSearchResult result = new CustomerSearchResult();
                        result.setMasterId(customer.getMasterId());
                        result.setPolicyOwnerName(customer.getFirstName() + " " + customer.getLastName());
                        result.setPolicyNumber(policy.getPolicyNumber());
                        result.setPolicyStatus(policy.getPolicyStatus() != null ? policy.getPolicyStatus().name() : null);
                        result.setRole(policy.getRole() != null ? policy.getRole().name() : null);
                        result.setAddress(formatAddress(customer));
                        result.setUsername(customer.getUsername());
                        return result;
                    });
                })
                .collect(Collectors.toList());
    }

    public CustomerDetailResponse getCustomerDetail(String masterId) {
        Customer customer = customerRepository.findByMasterId(masterId)
                .orElseThrow(() -> new RuntimeException("Customer not found with Master ID: " + masterId));

        CustomerDetailResponse response = new CustomerDetailResponse();

        CustomerDetailResponse.PersonalInfo personalInfo = new CustomerDetailResponse.PersonalInfo();
        personalInfo.setName(customer.getFirstName() + " " + customer.getLastName());
        personalInfo.setAddress(customer.getAddress());
        personalInfo.setCity(customer.getCity());
        personalInfo.setState(customer.getState());
        personalInfo.setZip(customer.getZip());
        personalInfo.setCountry(customer.getCountry());
        personalInfo.setDateOfBirth(customer.getDateOfBirth() != null ? customer.getDateOfBirth().toString() : null);
        personalInfo.setSsn(maskSsn(customer.getSsn()));
        personalInfo.setTaxId(maskTaxId(customer.getTaxId()));
        response.setPersonalInfo(personalInfo);

        CustomerDetailResponse.UserInfo userInfo = new CustomerDetailResponse.UserInfo();
        userInfo.setMasterId(customer.getMasterId());
        userInfo.setUsername(customer.getUsername());
        userInfo.setEmail(customer.getEmail());
        userInfo.setMobileNumber(customer.getMobileNumber());
        userInfo.setUserStatus(customer.getUserStatus() != null ? customer.getUserStatus().name() : null);
        userInfo.setDisableReason(customer.getDisableReason());
        userInfo.setCreationDate(customer.getCreationDate() != null ? customer.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null);
        userInfo.setLastLoginDate(customer.getLastLoginDate() != null ? customer.getLastLoginDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null);
        response.setUserInfo(userInfo);

        List<Policy> policies = policyRepository.findByCustomerId(customer.getId());
        List<CustomerDetailResponse.PolicyInfo> policyInfos = policies.stream().map(policy -> {
            CustomerDetailResponse.PolicyInfo policyInfo = new CustomerDetailResponse.PolicyInfo();
            policyInfo.setPolicyNumber(policy.getPolicyNumber());
            policyInfo.setPolicyStatus(policy.getPolicyStatus() != null ? policy.getPolicyStatus().name() : null);
            policyInfo.setRole(policy.getRole() != null ? policy.getRole().name() : null);
            policyInfo.setPolicyType(policy.getPolicyType() != null ? policy.getPolicyType().name() : null);
            policyInfo.setProduct(policy.getProduct());
            policyInfo.setIssueDate(policy.getIssueDate() != null ? policy.getIssueDate().toString() : null);
            policyInfo.setPortalVisibility(policy.isPortalVisibility());
            return policyInfo;
        }).collect(Collectors.toList());
        response.setPolicies(policyInfos);

        response.setRegistered(customer.getUsername() != null && !customer.getUsername().isEmpty());

        return response;
    }

    public CustomerDetailResponse registerUser(RegistrationRequest request) {
        if (customerRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }

        Customer customer;
        List<Customer> existingCustomers = customerRepository.findBySsnAndBirthYear(
                request.getSsn(), request.getYearOfBirth());

        if (!existingCustomers.isEmpty()) {
            customer = existingCustomers.get(0);
            if (customer.getUsername() != null && !customer.getUsername().isEmpty()) {
                throw new RuntimeException("Customer is already registered with username: " + customer.getUsername());
            }
        } else {
            customer = new Customer();
            customer.setMasterId("MID-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            customer.setSsn(request.getSsn());
            customer.setDateOfBirth(LocalDate.of(request.getYearOfBirth(), 1, 1));
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setAddress(request.getAddress());
            customer.setCity(request.getCity());
            customer.setState(request.getState());
            customer.setZip(request.getZip());
            customer.setCountry(request.getCountry());
            customer.setTaxId(request.getTaxId());
        }

        customer.setUsername(request.getUsername());
        customer.setEmail(request.getEmail());
        customer.setMobileNumber(request.getPhoneNumber());
        customer.setUserStatus(UserStatus.ACTIVE);
        customer.setCreationDate(LocalDateTime.now());
        customer.setCustomerType(
                "INDIVIDUAL".equalsIgnoreCase(request.getCustomerType())
                        ? CustomerType.INDIVIDUAL
                        : "TRUST".equalsIgnoreCase(request.getCustomerType())
                        ? CustomerType.TRUST
                        : CustomerType.CORPORATE);

        customerRepository.save(customer);

        auditService.log("SYSTEM", "REGISTER_USER", customer.getMasterId(),
                "User registered with username: " + customer.getUsername());

        return getCustomerDetail(customer.getMasterId());
    }

    public CustomerDetailResponse updateProfile(String masterId, ProfileUpdateRequest request) {
        Customer customer = customerRepository.findByMasterId(masterId)
                .orElseThrow(() -> new RuntimeException("Customer not found with Master ID: " + masterId));

        if (request.getPhoneNumber() != null) customer.setMobileNumber(request.getPhoneNumber());
        if (request.getEmail() != null) customer.setEmail(request.getEmail());
        if (request.getAddress() != null) customer.setAddress(request.getAddress());
        if (request.getCity() != null) customer.setCity(request.getCity());
        if (request.getState() != null) customer.setState(request.getState());
        if (request.getZip() != null) customer.setZip(request.getZip());
        if (request.getCountry() != null) customer.setCountry(request.getCountry());

        customerRepository.save(customer);

        auditService.log("ADMIN", "UPDATE_PROFILE", masterId, "Profile updated");

        return getCustomerDetail(masterId);
    }

    public CustomerDetailResponse enableUser(String masterId) {
        Customer customer = customerRepository.findByMasterId(masterId)
                .orElseThrow(() -> new RuntimeException("Customer not found with Master ID: " + masterId));

        customer.setUserStatus(UserStatus.ACTIVE);
        customer.setDisableReason(null);
        customerRepository.save(customer);

        auditService.log("ADMIN", "ENABLE_USER", masterId, "User enabled");

        return getCustomerDetail(masterId);
    }

    public CustomerDetailResponse disableUser(String masterId, String reason) {
        Customer customer = customerRepository.findByMasterId(masterId)
                .orElseThrow(() -> new RuntimeException("Customer not found with Master ID: " + masterId));

        customer.setUserStatus(UserStatus.DISABLED);
        customer.setDisableReason(reason);
        customerRepository.save(customer);

        auditService.log("ADMIN", "DISABLE_USER", masterId, "User disabled. Reason: " + reason);

        return getCustomerDetail(masterId);
    }

    public void deleteUser(String masterId) {
        Customer customer = customerRepository.findByMasterId(masterId)
                .orElseThrow(() -> new RuntimeException("Customer not found with Master ID: " + masterId));

        customer.setUserStatus(UserStatus.DELETED);
        customer.setUsername(null);
        customerRepository.save(customer);

        auditService.log("ADMIN", "DELETE_USER", masterId, "User deleted (soft delete)");
    }

    private String formatAddress(Customer customer) {
        StringBuilder sb = new StringBuilder();
        if (customer.getAddress() != null) sb.append(customer.getAddress());
        if (customer.getCity() != null) sb.append(", ").append(customer.getCity());
        if (customer.getState() != null) sb.append(", ").append(customer.getState());
        if (customer.getZip() != null) sb.append(" ").append(customer.getZip());
        return sb.toString();
    }

    private String maskSsn(String ssn) {
        if (ssn == null || ssn.length() < 4) return ssn;
        return "***-**-" + ssn.substring(ssn.length() - 4);
    }

    private String maskTaxId(String taxId) {
        if (taxId == null || taxId.length() < 4) return taxId;
        return "**-***" + taxId.substring(taxId.length() - 4);
    }
}
