package com.github.jrry.blog.forms;

import com.github.jrry.blog.forms.groups.IdGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ArticleForm {

    @NotNull(groups = IdGroup.class)
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
