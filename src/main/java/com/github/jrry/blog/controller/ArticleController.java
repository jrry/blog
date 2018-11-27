package com.github.jrry.blog.controller;

import com.github.jrry.blog.entity.ArticleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.github.jrry.blog.service.ArticleService;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{seoname:[a-z0-9-]+}/{id:\\d+}")
    public String article(@PathVariable("seoname") String seoname, @PathVariable("id") Long id, Model model) {
        ArticleEntity post = articleService.getArticleByIdWithSeoLinkCheck(id, seoname);
        model.addAttribute("article", post);
        return "article";
    }
}
