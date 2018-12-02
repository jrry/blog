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

package com.github.jrry.blog.repository;

import com.github.jrry.blog.entity.ArticleEntity;
import com.github.jrry.blog.enums.ArticleStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Jarosław Pawłowski
 */
@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<ArticleEntity> findById(Long id);

    Page<ArticleEntity> findAllByOrderByCreatedDesc(Pageable pageable);

    Page<ArticleEntity> findByStatusOrderByCreatedDesc(ArticleStatusEnum status, Pageable pageable);
}
