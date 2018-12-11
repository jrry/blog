/*
 * Copyright (c) 2018 Jarosław Pawłowski
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

package com.github.jrry.blog.service;

import com.github.jrry.blog.forms.CategoryForm;
import com.github.jrry.blog.repository.CategoryRepository;
import com.github.jrry.blog.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.github.jrry.blog.common.errors.NotFoundException;
import com.github.jrry.blog.entity.CategoryEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public CategoryEntity getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public CategoryForm getCategoryFormById(Long id) {
        return mapper.map(getCategoryById(id), CategoryForm.class);
    }

    @Override
    public List<CategoryEntity> getCategories() {
        return categoryRepository.findAllByOrderBySeoName();
    }

    @Override
    public Page<CategoryEntity> getCategories(int page) {
        return ValidationUtils.pageValidation(categoryRepository::findAllByOrderBySeoName, page, 15);
    }

    @Override
    @Transactional
    public void updateCategory(CategoryForm categoryForm) {
        CategoryEntity categoryEntity = getCategoryById(categoryForm.getId());
        mapper.map(categoryForm, categoryEntity);
        categoryRepository.save(categoryEntity);
    }

    @Override
    @Transactional
    public void saveCategory(CategoryForm categoryForm) {
        CategoryEntity categoryEntity = mapper.map(categoryForm, CategoryEntity.class);
        //TODO: unique seoName
        categoryRepository.save(categoryEntity);
    }
}
