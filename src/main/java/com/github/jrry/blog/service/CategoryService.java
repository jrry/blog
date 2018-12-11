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

import com.github.jrry.blog.entity.CategoryEntity;
import com.github.jrry.blog.forms.CategoryForm;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Jarosław Pawłowski
 */
public interface CategoryService {
    CategoryEntity getCategoryById(Long id);

    CategoryForm getCategoryFormById(Long id);

    Page<CategoryEntity> getCategories(int page);

    List<CategoryEntity> getCategories();

    void updateCategory(CategoryForm categoryForm);

    void saveCategory(CategoryForm categoryForm);
}
