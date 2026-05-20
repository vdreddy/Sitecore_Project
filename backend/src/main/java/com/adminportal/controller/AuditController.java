package com.adminportal.controller;

import com.adminportal.entity.AuditLog;
import com.adminportal.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@CrossOrigin(origins = "*")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllAuditLogs() {
        return ResponseEntity.ok(auditService.getAllAuditLogs());
    }

    @GetMapping("/{masterId}")
    public ResponseEntity<List<AuditLog>> getAuditLogs(@PathVariable String masterId) {
        return ResponseEntity.ok(auditService.getAuditLogs(masterId));
    }
}
