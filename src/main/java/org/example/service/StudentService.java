package org.example.service;

import jakarta.transaction.Transactional;
import org.example.repository.StudentRepository;
import org.example.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository repo;
    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }
    @Transactional
    public void enroll(String name,int age) {
        repo.save(new Student(name, age));
        System.out.println("Enrolling "+name+" "+age);
    }
}
