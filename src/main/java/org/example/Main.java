package org.example;

import org.example.config.AppConfig;
import org.example.config.PersistenceConfig;
import org.example.service.StudentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.ObjectInputFilter;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        StudentService studentService = context.getBean(StudentService.class);
        studentService.enroll("Ayman",21);
        context.close();
    }
}