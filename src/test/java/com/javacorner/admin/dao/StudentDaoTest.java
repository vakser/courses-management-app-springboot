package com.javacorner.admin.dao;

import com.javacorner.admin.AbstractTest;
import com.javacorner.admin.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"file:src/test/resources/db/clear-all.sql", "file:src/test/resources/db/javacorner-admin-db.sql"})
class StudentDaoTest extends AbstractTest {
    @Autowired
    private StudentDao studentDao;

    @Test
    void testFindStudentsByName() {
        List<Student> students = studentDao.findStudentsByName("student2LN");
        assertEquals(1, students.size());
    }

    @Test
    void testFindStudentByEmail() {
        Student expectedStudent = new Student();
        expectedStudent.setStudentId(1L);
        expectedStudent.setFirstName("student1FN");
        expectedStudent.setLastName("student1LN");
        expectedStudent.setLevel("beginner");
        Student student = studentDao.findStudentByEmail("stdUser1@gmail.com");
        assertEquals(expectedStudent, student);
    }
}