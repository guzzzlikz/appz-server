package org.example.appzserver.services;

import org.example.appzserver.models.dtos.CourseDTO;
import org.example.appzserver.models.entities.Course;
import org.example.appzserver.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseService {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<?> addCourse(CourseDTO courseDTO, String token) {
        String ownerId = jwtService.getDataFromToken(token.replace("Bearer ", ""));
        Course course = Course.builder()
                .title(courseDTO.getTitle())
                .id(UUID.randomUUID().toString())
                .rating(0)
                .rates(0)
                .description(courseDTO.getDescription())
                .ownerId(ownerId)
                .build();
        courseRepository.save(course);
        return ResponseEntity.ok(course);
    }
}
