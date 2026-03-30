package org.example.appzserver.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="courses")
public class Course {
    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;
    private double rating;
    private int rates;
}
