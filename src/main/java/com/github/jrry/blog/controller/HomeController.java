package com.github.jrry.blog.controller;

import com.github.jrry.blog.entity.ArticleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.github.jrry.blog.service.ArticleService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String home(Model model) {
        Page<ArticleEntity> page = articleService.getPublishedArticles(0);
        model.addAttribute("articles", page.getContent());
        return "index";
    }

    @GetMapping({"index.html", "index.php"})
    public String homeIndexRedirect() {
        return "redirect:/";
    }

    @GetMapping("/page/{number}")
    public String homeWithPageNumber(Model model, @PathVariable("number") int number) {
        if (number < 1) return "redirect:/";
        Page<ArticleEntity> page = articleService.getPublishedArticles(number - 1);
        if (number > page.getTotalPages()) return "redirect:/";
        model.addAttribute("articles", page);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
