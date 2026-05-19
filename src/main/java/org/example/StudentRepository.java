package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class StudentRepository {
    @Autowired
    private StudentRepository studentRepository;
}
