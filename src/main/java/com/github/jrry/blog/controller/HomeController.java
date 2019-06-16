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

package com.github.jrry.blog.controller;

import com.github.jrry.blog.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.github.jrry.blog.service.ArticleService;

/**
 * @author Jarosław Pawłowski
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String home(Model model) {
        Page<Article> page = articleService.getPublishedArticles(0);
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
        Page<Article> page = articleService.getPublishedArticles(number - 1);
        if (number > page.getTotalPages()) return "redirect:/";
        model.addAttribute("articles", page);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
