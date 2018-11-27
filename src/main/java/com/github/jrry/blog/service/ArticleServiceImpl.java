package com.github.jrry.blog.service;

import com.github.jrry.blog.entity.ArticleEntity;
import com.github.jrry.blog.entity.ImageEntity;
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

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final UserService userService;
    private final ModelMapper mapper;

    @Override
    public Page<ArticleEntity> getArticles(int page) {
        return ValidationUtils.pageValidation(articleRepository::findAllByOrderByCreatedDesc, page);
    }

    @Override
    public Page<ArticleEntity> getPublishedArticles(int page) {
        return articleRepository.findByStatusOrderByCreatedDesc(ArticleStatusEnum.PUBLISHED, PageRequest.of(page, 10));
    }

    @Override
    public ArticleEntity getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public ArticleEntity getArticleByIdWithSeoLinkCheck(Long id, String seoLink) {
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
    public void updateArticle(ArticleForm articleForm, ImageEntity imageEntity) {
        ArticleEntity articleEntity = getArticleById(articleForm.getId());
        mapper.map(articleForm, articleEntity);
        articleEntity.setImage(imageEntity);
        articleEntity.setCategory(categoryService.getCategoryById(Long.parseLong(articleForm.getCategory())));
        articleEntity.setTags(tagService.createOrGetTags(articleForm.getTags()));
        articleRepository.save(articleEntity);
    }

    @Override
    @Transactional
    public void saveArticle(ArticleForm articleForm, ImageEntity imageEntity) {
        ArticleEntity articleEntity = mapper.map(articleForm, ArticleEntity.class);
        articleEntity.setStatus(ArticleStatusEnum.DRAFT);
        articleEntity.setCategory(categoryService.getCategoryById(Long.parseLong(articleForm.getCategory())));
        articleEntity.setTags(tagService.createOrGetTags(articleForm.getTags()));
        articleEntity.setImage(imageEntity);
        articleEntity.setAuthor(userService.getAuthenticatedUser());
        articleRepository.save(articleEntity);
    }
}
