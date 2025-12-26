package com.calistus.caliblog.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreatePostRequest {

//    @NotBlank
    private String title;
    private String content;

    private String category;
    private List<String> tags;
}
