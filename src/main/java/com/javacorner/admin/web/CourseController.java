package com.javacorner.admin.web;

import com.javacorner.admin.entity.Course;
import com.javacorner.admin.entity.Instructor;
import com.javacorner.admin.entity.User;
import com.javacorner.admin.service.CourseService;
import com.javacorner.admin.service.InstructorService;
import com.javacorner.admin.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

import static com.javacorner.admin.constants.JavaCornerConstants.*;

@Controller
@RequestMapping(value = "/courses")
public class CourseController {
    private CourseService courseService;
    private InstructorService instructorService;
    private UserService userService;

    public CourseController(CourseService courseService, InstructorService instructorService, UserService userService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.userService = userService;
    }

    @GetMapping(value = "/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String courses(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Course> courses = courseService.findCoursesByCourseName(keyword);
        model.addAttribute(LIST_COURSES, courses);
        model.addAttribute(KEYWORD, keyword);
        return "course-views/courses";
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteCourse(Long courseId, String keyword) {
        courseService.removeCourse(courseId);
        return "redirect:/courses/index?keyword=" + keyword;
    }

    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAnyAuthority('Admin', 'Instructor')")
    public String updateCourse(Model model, Long courseId, Principal principal) {
        if (userService.doesCurrentUserHasRole(INSTRUCTOR)) {
            Instructor instructor = instructorService.loadInstructorByEmail(principal.getName());
            model.addAttribute(CURRENT_INSTRUCTOR, instructor);
        }
        Course course = courseService.loadCourseById(courseId);
        List<Instructor> instructors = instructorService.fetchInstructors();
        model.addAttribute(COURSE, course);
        model.addAttribute(LIST_INSTRUCTORS, instructors);
        return "course-views/formUpdate";
    }

    @GetMapping(value = "/formCreate")
    @PreAuthorize("hasAnyAuthority('Admin', 'Instructor')")
    public String formCourses(Model model, Principal principal) {
        if (userService.doesCurrentUserHasRole(INSTRUCTOR)) {
            Instructor instructor = instructorService.loadInstructorByEmail(principal.getName());
            model.addAttribute(CURRENT_INSTRUCTOR, instructor);
        }
        List<Instructor> instructors = instructorService.fetchInstructors();
        model.addAttribute(LIST_INSTRUCTORS, instructors);
        model.addAttribute(COURSE, new Course());
        return "course-views/formCreate";
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAnyAuthority('Admin', 'Instructor')")
    public String save(Course course) {
        courseService.createOrUpdateCourse(course);
        return userService.doesCurrentUserHasRole(INSTRUCTOR) ? "redirect:/courses/index/instructor" : "redirect:/courses/index";
    }

    @GetMapping(value = "/index/student")
    @PreAuthorize("hasAuthority('Student')")
    public String coursesForCurrentStudent(Model model, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        List<Course> subscribedCourses = courseService.fetchCoursesForStudent(user.getStudent().getStudentId());
        List<Course> otherCourses = courseService.fetchAll().stream().filter(course -> !subscribedCourses.contains(course)).toList();
        model.addAttribute(LIST_COURSES, subscribedCourses);
        model.addAttribute(OTHER_COURSES, otherCourses);
        model.addAttribute(FIRST_NAME, user.getStudent().getFirstName());
        model.addAttribute(LAST_NAME, user.getStudent().getLastName());
        return "course-views/student-courses";
    }

    @GetMapping(value = "/enrollStudent")
    @PreAuthorize("hasAuthority('Student')")
    public String enrollCurrentStudentInCourse(Long courseId, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        courseService.assignStudentToCourse(courseId, user.getStudent().getStudentId());
        return "redirect:/courses/index/student";
    }

    @GetMapping(value = "/index/instructor")
    @PreAuthorize("hasAuthority('Instructor')")
    public String coursesForCurrentInstructor(Model model, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        Instructor instructor = instructorService.loadInstructorById(user.getInstructor().getInstructorId());
        model.addAttribute(LIST_COURSES, instructor.getCourses());
        model.addAttribute(FIRST_NAME, instructor.getFirstName());
        model.addAttribute(LAST_NAME, instructor.getLastName());
        return "course-views/instructor-courses";
    }

    @GetMapping(value = "/instructor")
    @PreAuthorize("hasAuthority('Admin')")
    public String coursesByInstructorId(Model model, Long instructorId) {
        Instructor instructor = instructorService.loadInstructorById(instructorId);
        model.addAttribute(LIST_COURSES, instructor.getCourses());
        model.addAttribute(FIRST_NAME, instructor.getFirstName());
        model.addAttribute(LAST_NAME, instructor.getLastName());
        return "course-views/instructor-courses";
    }
}
