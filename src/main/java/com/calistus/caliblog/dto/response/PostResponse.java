package com.calistus.caliblog.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostResponse {
    private String title;
    private String slug;
    private String content;
    private String category;
    private List<String> tags;
    private String createdAt;
}
