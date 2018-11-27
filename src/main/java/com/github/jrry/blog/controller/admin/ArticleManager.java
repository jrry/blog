package com.github.jrry.blog.controller.admin;

import com.github.jrry.blog.entity.ArticleEntity;
import com.github.jrry.blog.entity.ImageEntity;
import com.github.jrry.blog.service.CategoryService;
import com.github.jrry.blog.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.github.jrry.blog.forms.ArticleForm;
import com.github.jrry.blog.service.ArticleService;
import com.github.jrry.blog.service.ImageService;

import javax.validation.Valid;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/article")
public class ArticleManager {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    @GetMapping("/list")
    public String getArticles(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<ArticleEntity> articles = articleService.getArticles(page);
        model.addAttribute("articles", articles);
        model.addAttribute("paginationNumbers", PaginationUtil.generateThreeNumbers(articles));
        return "admin/article-list";
    }

    @GetMapping("/new")
    public String getArticleDetails(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("article", new ArticleForm());
        return "admin/article-new";
    }

    @PostMapping("/new")
    public String saveArticle(@Valid @ModelAttribute("article") ArticleForm articleForm, BindingResult bindingResult, Model model) {
        ImageEntity imageEntity = checkImageError(articleForm.getImageId(), bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories());
            return "admin/article-new";
        }
        articleService.saveArticle(articleForm, imageEntity);
        return "redirect:/adm/article/list";
    }

    @GetMapping("/edit/{id:\\d+}")
    public String editArticle(@PathVariable("id") Long articleId, Model model) {
        ArticleForm articleForm = articleService.getArticleFormById(articleId);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("article", articleForm);
        return "admin/article-edit";
    }

    @RequestMapping(value = "/edit/{id:\\d+}", method = {PUT, POST})
    public String updateArticle(@Valid @ModelAttribute("article") ArticleForm articleForm, BindingResult bindingResult, Model model) {
        ImageEntity imageEntity = checkImageError(articleForm.getImageId(), bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories());
            return "admin/article-edit";
        }
        articleService.updateArticle(articleForm, imageEntity);
        return "redirect:/adm/article/list";
    }

    private ImageEntity checkImageError(Long id, BindingResult bindingResult) {
        Optional<ImageEntity> optionalImageEntity = imageService.maybeImageById(id);
        if (!optionalImageEntity.isPresent()) {
            bindingResult.addError(new FieldError("article", "imageId", "Image not found"));
        }
        return optionalImageEntity.orElse(null);
    }
}
