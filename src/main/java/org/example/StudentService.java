package org.example;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class StudentService {

    public void enroll(String name,int age) {
        System.out.println("Enrolling "+name+" "+age);
    }
}
