package org.example.appzserver.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection="users")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private int lvl;
    private int xp;
}
