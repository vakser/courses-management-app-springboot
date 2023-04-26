package com.javacorner.admin.service.impl;

import com.javacorner.admin.dao.StudentDao;
import com.javacorner.admin.entity.Course;
import com.javacorner.admin.entity.Student;
import com.javacorner.admin.entity.User;
import com.javacorner.admin.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentDao studentDao;
    @Mock
    private UserService userService;
    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void testLoadStudentById() {
        Student student = new Student();
        student.setStudentId(1L);
        when(studentDao.findById(any())).thenReturn(Optional.of(student));
        Student actualStudent = studentService.loadStudentById(1L);
        assertEquals(student, actualStudent);
    }

    @Test
    void testExceptionForNotFoundUserById() {
        assertThrows(EntityNotFoundException.class, () -> studentService.loadStudentById(any()));
    }

    @Test
    void testFindStudentsByName() {
        String name = "name";
        studentService.findStudentsByName(name);
        verify(studentDao).findStudentsByName(any());
    }

    @Test
    void testLoadStudentByEmail() {
        String email = "test@gmail.com";
        studentService.loadStudentByEmail(email);
        verify(studentDao).findStudentByEmail(email);
    }

    @Test
    void testCreateStudent() {
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("pass");
        when(userService.createUser(any(), any())).thenReturn(user);
        studentService.createStudent("FN", "LN", "master", "user@gmail.com", "pass");
        verify(studentDao).save(any());
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student();
        student.setStudentId(1L);
        studentService.updateStudent(student);
        verify(studentDao).save(student);
    }

    @Test
    void testFetchStudents() {
        studentService.fetchStudents();
        verify(studentDao, times(1)).findAll();
    }

    @Test
    void testRemoveStudent() {
        Student student = new Student();
        student.setStudentId(1L);
        Course course = new Course();
        course.setCourseId(1L);
        student.getCourses().add(course);
        when(studentDao.findById(any())).thenReturn(Optional.of(student));
        studentService.removeStudent(1L);
        verify(studentDao).deleteById(any());
    }
}