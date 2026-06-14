package org.example.repository;

import org.example.model.Student;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {
    private final SessionFactory  sessionFactory;
    public StudentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public void save(Student student) {
        sessionFactory.getCurrentSession().save(student);
    }
}
