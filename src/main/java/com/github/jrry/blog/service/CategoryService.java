package com.github.jrry.blog.service;

import com.github.jrry.blog.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    //TODO: exceptiony mogłby być bardziej szczegółowe
    CategoryEntity getCategoryById(Long id);

    List<CategoryEntity> getCategories();
}
