package org.example.repository;
import org.example.model.AuditEntry;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AuditRepository {
    private final SessionFactory sessionFactory;
    public AuditRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public void save(AuditEntry auditEntry) {
        sessionFactory.getCurrentSession().persist(auditEntry);
    }
}
