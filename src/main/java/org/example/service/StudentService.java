package org.example.service;
import org.example.model.Student;
import org.example.repository.StudentRepository;
import org.example.repository.AuditRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository repo;
    private final AuditService auditService;
    private final AuditRepository auditRepository;

    public StudentService(StudentRepository repo, AuditService auditService,  AuditRepository auditRepository) {
        this.auditService = auditService;
        this.auditRepository = auditRepository;
        this.repo = repo;
    }
    @Transactional
    public void enroll(String name,int age) {
        repo.save(new Student(name, age));
        auditService.log("Enrolled Student: "+name);
        System.out.println("Enrolling "+name+" "+age);
    }
}
