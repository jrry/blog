/*
 * Copyright (c) 2019 Jarosław Pawłowski
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.jrry.blog.service.impl;

import com.github.jrry.blog.forms.CategoryForm;
import com.github.jrry.blog.repository.CategoryRepository;
import com.github.jrry.blog.service.CategoryService;
import com.github.jrry.blog.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.github.jrry.blog.common.errors.NotFoundException;
import com.github.jrry.blog.entity.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Jarosław Pawłowski
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    //TODO: exceptiony mogłby być bardziej szczegółowe
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public CategoryForm getCategoryFormById(Long id) {
        return mapper.map(getCategoryById(id), CategoryForm.class);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAllByOrderBySeoName();
    }

    @Override
    public Page<Category> getCategories(int page) {
        return ValidationUtils.pageValidation(categoryRepository::findAllByOrderBySeoName, page, 15);
    }

    @Override
    @Transactional
    public Optional<Category> updateCategory(CategoryForm categoryForm) {
        Category category = getCategoryById(categoryForm.getId());
        if (!categoryForm.getSeoName().equals(category.getSeoName())
                && categoryRepository.findBySeoName(categoryForm.getSeoName()).isPresent()) {
            return Optional.empty();
        }
        mapper.map(categoryForm, category);
        return Optional.of(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public Optional<Category> saveCategory(CategoryForm categoryForm) {
        if (categoryRepository.findBySeoName(categoryForm.getSeoName()).isPresent()) {
            return Optional.empty();
        }
        Category category = mapper.map(categoryForm, Category.class);
        return Optional.of(categoryRepository.save(category));
    }
}
