package com.github.jrry.blog.repository;

import com.github.jrry.blog.entity.ArticleEntity;
import com.github.jrry.blog.enums.ArticleStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<ArticleEntity> findById(Long id);

    Page<ArticleEntity> findAllByOrderByCreatedDesc(Pageable pageable);

    Page<ArticleEntity> findByStatusOrderByCreatedDesc(ArticleStatusEnum status, Pageable pageable);
}
