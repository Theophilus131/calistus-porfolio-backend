package com.calistus.caliblog.data.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "posts")
@Getter
@Setter
public class Post {

    @Id
    private String id;
    private String title;
    private String slug;
    private String content;

    private String category;
    private List<String> tags;


    private boolean published;

    private String authorId;
    private Instant createdAt;
    private Instant updatedAt;

}
