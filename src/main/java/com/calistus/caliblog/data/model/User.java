package com.calistus.caliblog.data.model;


import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users")
@NoArgsConstructor
public class User{

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;
    private String email;

    private Set<Role> roles;

    private Instant createdAt;
}
