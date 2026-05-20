package com.adminportal.repository;

import com.adminportal.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByTargetMasterIdOrderByTimestampDesc(String targetMasterId);

    List<AuditLog> findAllByOrderByTimestampDesc();
}
