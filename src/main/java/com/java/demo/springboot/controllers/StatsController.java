package com.java.demo.springboot.controllers;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.demo.springboot.models.Statistics;
import com.java.demo.springboot.security.services.CoursServiceImpl;
import com.java.demo.springboot.security.services.UserServiceImpl;
import java.util.HashMap;
@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private CoursServiceImpl courseService;
    
    

    
@GetMapping("/stats")
public ResponseEntity<Statistics> getStats() {
    Statistics stats = new Statistics(
        userService.countStudents(),
        userService.countTrainers(),
        courseService.countCourses(),
        userService.countRegistrations()
    );

    return ResponseEntity.ok(stats);
}

}
