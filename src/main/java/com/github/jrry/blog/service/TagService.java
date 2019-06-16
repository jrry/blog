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

package com.github.jrry.blog.service;

import com.github.jrry.blog.entity.Tag;
import com.github.jrry.blog.forms.TagForm;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

/**
 * @author Jarosław Pawłowski
 */
public interface TagService {
    Tag getTagById(Long id);

    TagForm getTagFormById(Long id);

    Page<Tag> getTags(int page);

    Set<Tag> createOrGetTags(String names);

    Optional<Tag> updateTag(TagForm tagForm);

    Optional<Tag> saveTag(TagForm tagForm);
}
