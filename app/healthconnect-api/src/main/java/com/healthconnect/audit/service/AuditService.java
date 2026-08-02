package com.healthconnect.audit.service;

public interface AuditService {

    void logEvent(
            String action,
            String entityName,
            Long entityId,
            String description,
            String performedBy);
}
