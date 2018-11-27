package com.github.jrry.blog.service;

import com.github.jrry.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.github.jrry.blog.common.errors.NotFoundException;
import com.github.jrry.blog.entity.CategoryEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    //TODO: exceptiony mogłby być bardziej szczegółowe
    @Override
    public CategoryEntity getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<CategoryEntity> getCategories() {
        return categoryRepository.findAll();
    }
}
