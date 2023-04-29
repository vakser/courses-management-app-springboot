package com.javacorner.admin.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('Admin', 'Instructor', 'Student')")
    public String home() {
        return "home";
    }
}
