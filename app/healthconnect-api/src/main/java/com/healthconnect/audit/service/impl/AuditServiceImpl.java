package com.healthconnect.audit.service.impl;

import com.healthconnect.audit.entity.AuditLog;
import com.healthconnect.audit.repository.AuditRepository;
import com.healthconnect.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Override
    public void logEvent(
            String action,
            String entityName,
            Long entityId,
            String description,
            String performedBy) {

        AuditLog auditLog = new AuditLog();

        auditLog.setAction(action);
        auditLog.setEntityName(entityName);
        auditLog.setEntityId(entityId);
        auditLog.setDescription(description);
        auditLog.setPerformedBy(performedBy);
        auditLog.setCreatedAt(LocalDateTime.now());

        auditRepository.save(auditLog);
    }
}