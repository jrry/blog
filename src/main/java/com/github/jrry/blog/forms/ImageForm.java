package com.github.jrry.blog.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ImageForm {

    private Long id;

    @NotBlank
    private String url;

    @NotBlank
    private String source;

    @NotBlank
    private String alt;
}
