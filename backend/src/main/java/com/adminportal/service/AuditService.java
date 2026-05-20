package com.adminportal.service;

import com.adminportal.entity.AuditLog;
import com.adminportal.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(String adminUsername, String action, String targetMasterId, String details) {
        AuditLog auditLog = new AuditLog(adminUsername, action, targetMasterId, details);
        auditLogRepository.save(auditLog);
    }

    public List<AuditLog> getAuditLogs(String masterId) {
        return auditLogRepository.findByTargetMasterIdOrderByTimestampDesc(masterId);
    }

    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAllByOrderByTimestampDesc();
    }
}
