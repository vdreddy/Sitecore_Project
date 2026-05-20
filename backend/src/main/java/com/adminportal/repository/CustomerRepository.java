package com.adminportal.repository;

import com.adminportal.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByMasterId(String masterId);

    @Query("SELECT c FROM Customer c WHERE c.ssn = :ssn AND YEAR(c.dateOfBirth) = :birthYear")
    List<Customer> findBySsnAndBirthYear(@Param("ssn") String ssn, @Param("birthYear") int birthYear);

    List<Customer> findByTaxId(String taxId);

    List<Customer> findByUsername(String username);

    List<Customer> findByEmailIgnoreCase(String email);

    @Query("SELECT c FROM Customer c WHERE " +
           "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')) AND " +
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')) AND " +
           "LOWER(c.address) LIKE LOWER(CONCAT('%', :address, '%'))")
    List<Customer> findByNameAndAddress(@Param("firstName") String firstName,
                                        @Param("lastName") String lastName,
                                        @Param("address") String address);

    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);
}
