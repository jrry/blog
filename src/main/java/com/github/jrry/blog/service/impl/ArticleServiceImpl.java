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

import com.github.jrry.blog.entity.Article;
import com.github.jrry.blog.entity.Image;
import com.github.jrry.blog.service.ArticleService;
import com.github.jrry.blog.service.CategoryService;
import com.github.jrry.blog.service.TagService;
import com.github.jrry.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.jrry.blog.common.errors.NotFoundException;
import com.github.jrry.blog.enums.ArticleStatusEnum;
import com.github.jrry.blog.forms.ArticleForm;
import com.github.jrry.blog.repository.ArticleRepository;
import com.github.jrry.blog.utils.ValidationUtils;

/**
 * @author Jarosław Pawłowski
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final UserService userService;
    private final ModelMapper mapper;

    @Override
    public Page<Article> getArticles(int page) {
        return ValidationUtils.pageValidation(articleRepository::findAllByOrderByCreatedDesc, page, 15);
    }

    @Override
    public Page<Article> getPublishedArticles(int page) {
        return articleRepository.findByStatusOrderByCreatedDesc(ArticleStatusEnum.PUBLISHED, PageRequest.of(page, 15));
    }

    @Override
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Article getArticleByIdWithSeoLinkCheck(Long id, String seoLink) {
        return articleRepository.findById(id)
                .filter(p -> p.getSeoLink().equals(seoLink))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public ArticleForm getArticleFormById(Long id) {
        return mapper.map(getArticleById(id), ArticleForm.class);
    }

    @Override
    @Transactional
    public void updateArticle(ArticleForm articleForm, Image image) {
        Article article = getArticleById(articleForm.getId());
        mapper.map(articleForm, article);
        article.setImage(image);
        article.setCategory(categoryService.getCategoryById(Long.parseLong(articleForm.getCategory())));
        article.setTags(tagService.createOrGetTags(articleForm.getTags()));
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void saveArticle(ArticleForm articleForm, Image image) {
        Article article = mapper.map(articleForm, Article.class);
        article.setStatus(ArticleStatusEnum.DRAFT);
        article.setCategory(categoryService.getCategoryById(Long.parseLong(articleForm.getCategory())));
        article.setTags(tagService.createOrGetTags(articleForm.getTags()));
        article.setImage(image);
        article.setAuthor(userService.getAuthenticatedUser());
        articleRepository.save(article);
    }
}
