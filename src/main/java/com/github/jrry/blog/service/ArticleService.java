package com.github.jrry.blog.service;

import com.github.jrry.blog.entity.ArticleEntity;
import com.github.jrry.blog.entity.ImageEntity;
import org.springframework.data.domain.Page;
import com.github.jrry.blog.forms.ArticleForm;

public interface ArticleService {
    Page<ArticleEntity> getArticles(int page);

    Page<ArticleEntity> getPublishedArticles(int page);

    ArticleEntity getArticleById(Long id);

    ArticleEntity getArticleByIdWithSeoLinkCheck(Long id, String seoname);

    ArticleForm getArticleFormById(Long id);

    void updateArticle(ArticleForm articleForm, ImageEntity imageEntity);

    void saveArticle(ArticleForm articleForm, ImageEntity imageEntity);
}
