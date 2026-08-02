package com.healthconnect.audit.repository;

import com.healthconnect.audit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<AuditLog, Long> {
}