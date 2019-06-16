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

package com.github.jrry.blog.controller.admin;

import com.github.jrry.blog.entity.Category;
import com.github.jrry.blog.forms.CategoryForm;
import com.github.jrry.blog.forms.groups.IdGroup;
import com.github.jrry.blog.service.CategoryService;
import com.github.jrry.blog.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * @author Jarosław Pawłowski
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/category")
public class CategoryManager {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public String getCategories(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Category> categories = categoryService.getCategories(page);
        model.addAttribute("categories", categories);
        model.addAttribute("paginationNumbers", PaginationUtils.generateThreeNumbers(categories));
        return "admin/category/category-list";
    }

    @GetMapping("/new")
    public String getCategoryDetails(Model model) {
        model.addAttribute("category", new CategoryForm());
        return "admin/category/category-new";
    }

    @PostMapping("/new")
    public String saveCategory(@Valid @ModelAttribute("category") CategoryForm categoryForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/category/category-new";
        }
        categoryService.saveCategory(categoryForm);
        return "redirect:/adm/category/list";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String editCategory(@PathVariable("id") Long categoryId, Model model) {
        model.addAttribute("category", categoryService.getCategoryFormById(categoryId));
        return "admin/category/category-edit";
    }

    @RequestMapping(value = "/edit", method = {PUT, POST})
    public String updateCategory(@Validated(IdGroup.class) @ModelAttribute("category") CategoryForm categoryForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/category/category-edit";
        }
        categoryService.updateCategory(categoryForm);
        return "redirect:/adm/category/list";
    }
}
