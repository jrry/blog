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

import com.github.jrry.blog.forms.TagForm;
import com.github.jrry.blog.repository.TagRepository;
import com.github.jrry.blog.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.jrry.blog.entity.TagEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jarosław Pawłowski
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Page<TagEntity> getTags(int page) {
        return ValidationUtils.pageValidation(tagRepository::findAllByOrderByName, page);
    }

    @Override
    @Transactional
    public Set<TagEntity> createOrGetTags(String names) {
        Set<TagEntity> tagEntitySet = new HashSet<>();
        String[] tagsArray = names.split(",");
        for (String tag : tagsArray) {
            tagEntitySet.add(tagRepository.findByName(tag).orElseGet(() -> tagRepository.save(new TagEntity(tag))));
        }
        return tagEntitySet;
    }

    @Override
    public void updateTag(TagForm tagForm) {

    }

    @Override
    public void saveTag(TagForm tagForm) {

    }
}
