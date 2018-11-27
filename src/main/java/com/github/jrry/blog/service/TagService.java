package com.github.jrry.blog.service;

import com.github.jrry.blog.entity.TagEntity;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<TagEntity> getTags();

    Set<TagEntity> createOrGetTags(String names);
}
