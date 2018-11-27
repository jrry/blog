package com.github.jrry.blog.forms;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ArticleForm {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String seoLink;

    @NotBlank
    private String content;

    @NotBlank
    private String description;

    private Long imageId;

    @NotBlank
    private String category;

    @NotBlank
    private String tags;
}
