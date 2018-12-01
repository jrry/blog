package com.github.jrry.blog.forms;

import com.github.jrry.blog.forms.groups.IdGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ImageForm {

    @NotNull(groups = IdGroup.class)
    private Long id;

    @NotBlank
    private String url;

    @NotBlank
    private String source;

    @NotBlank
    private String alt;
}
