package com.github.jrry.blog.repository;

import com.github.jrry.blog.entity.ImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    Page<ImageEntity> findAllByOrderByCreatedDesc(Pageable pageable);
}
