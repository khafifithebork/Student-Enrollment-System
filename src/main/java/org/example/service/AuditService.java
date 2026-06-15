package org.example.service;

import org.example.model.AuditEntry;
import org.example.repository.AuditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {
    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)

    public void log(String action){
        auditRepository.save(new AuditEntry(action));
        System.out.println("[AUDIT] Logged "+action);
    }
}
