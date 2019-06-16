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

import com.github.jrry.blog.entity.Article;
import com.github.jrry.blog.entity.Image;
import com.github.jrry.blog.forms.groups.IdGroup;
import com.github.jrry.blog.service.CategoryService;
import com.github.jrry.blog.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.github.jrry.blog.forms.ArticleForm;
import com.github.jrry.blog.service.ArticleService;
import com.github.jrry.blog.service.ImageService;

import javax.validation.Valid;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * @author Jarosław Pawłowski
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/article")
public class ArticleManager {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    @GetMapping("/list")
    public String getArticles(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Article> articles = articleService.getArticles(page);
        model.addAttribute("articles", articles);
        model.addAttribute("paginationNumbers", PaginationUtils.generateThreeNumbers(articles));
        return "admin/article/article-list";
    }

    @GetMapping("/new")
    public String getArticleDetails(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("article", new ArticleForm());
        return "admin/article/article-new";
    }

    @PostMapping("/new")
    public String saveArticle(@Valid @ModelAttribute("article") ArticleForm articleForm, BindingResult bindingResult, Model model) {
        Image image = checkImageError(articleForm.getImageId(), bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories());
            return "admin/article/article-new";
        }
        articleService.saveArticle(articleForm, image);
        return "redirect:/adm/article/list";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String editArticle(@PathVariable("id") Long articleId, Model model) {
        ArticleForm articleForm = articleService.getArticleFormById(articleId);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("article", articleForm);
        return "admin/article/article-edit";
    }

    @RequestMapping(value = "/edit", method = {PUT, POST})
    public String updateArticle(@Validated(IdGroup.class) @ModelAttribute("article") ArticleForm articleForm, BindingResult bindingResult, Model model) {
        Image image = checkImageError(articleForm.getImageId(), bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories());
            return "admin/article/article-edit";
        }
        articleService.updateArticle(articleForm, image);
        return "redirect:/adm/article/list";
    }

    private Image checkImageError(Long id, BindingResult bindingResult) {
        Optional<Image> optionalImageEntity = imageService.optionalImageById(id);
        if (optionalImageEntity.isEmpty()) {
            bindingResult.addError(new FieldError("article", "imageId", "Image not found"));
        }
        return optionalImageEntity.orElse(null);
    }
}
