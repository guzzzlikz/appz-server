package org.example.appzserver.controllers;

import org.example.appzserver.models.dtos.CourseDTO;
import org.example.appzserver.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("add")
    public ResponseEntity<?> addCourse(@RequestBody CourseDTO courseDTO,
                                       @RequestHeader("Authorization") String token) {
        return courseService.addCourse(courseDTO, token);
    }
}
