package com.healthconnect.audit.service.impl;

import com.healthconnect.audit.entity.AuditLog;
import com.healthconnect.audit.repository.AuditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private AuditServiceImpl auditService;

    @Test
    void logEvent_ShouldSaveAuditLog() {

        when(auditRepository.save(any(AuditLog.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        auditService.logEvent(
                "USER_CREATED",
                "User",
                1L,
                "User john@test.com created.",
                "SYSTEM"
        );

        ArgumentCaptor<AuditLog> captor =
                ArgumentCaptor.forClass(AuditLog.class);

        verify(auditRepository, times(1)).save(captor.capture());

        AuditLog auditLog = captor.getValue();

        assertEquals("USER_CREATED", auditLog.getAction());
        assertEquals("User", auditLog.getEntityName());
        assertEquals(1L, auditLog.getEntityId());
        assertEquals("User john@test.com created.", auditLog.getDescription());
        assertEquals("SYSTEM", auditLog.getPerformedBy());

        assertNotNull(auditLog.getCreatedAt());
    }
}