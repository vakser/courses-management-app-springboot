package com.javacorner.admin.dao;

import com.javacorner.admin.AbstractTest;
import com.javacorner.admin.entity.Course;
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
class CourseDaoTest extends AbstractTest {
    @Autowired
    private CourseDao courseDao;

    @Test
    void testFindCourseByCourseNameContains() {
        List<Course> courses = courseDao.findCourseByCourseNameContains("Spring");
        int expectedResult = 2;
        assertEquals(expectedResult, courses.size());
    }

    @Test
    void testGetCoursesByStudentId() {
        List<Course> courses = courseDao.getCoursesByStudentId(1L);
        int expectedResult = 1;
        assertEquals(expectedResult, courses.size());
    }
}