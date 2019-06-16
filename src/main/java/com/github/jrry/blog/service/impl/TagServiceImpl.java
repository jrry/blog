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

import com.github.jrry.blog.common.errors.NotFoundException;
import com.github.jrry.blog.forms.TagForm;
import com.github.jrry.blog.repository.TagRepository;
import com.github.jrry.blog.service.TagService;
import com.github.jrry.blog.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.jrry.blog.entity.Tag;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Jarosław Pawłowski
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper mapper;

    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public TagForm getTagFormById(Long id) {
        return mapper.map(getTagById(id), TagForm.class);
    }

    @Override
    public Page<Tag> getTags(int page) {
        return ValidationUtils.pageValidation(tagRepository::findAllByOrderByName, page, 15);
    }

    @Override
    @Transactional
    public Set<Tag> createOrGetTags(String names) {
        Set<Tag> tagSet = new HashSet<>();
        String[] tagsArray = names.split(",");
        for (String tag : tagsArray) {
            tagSet.add(tagRepository.findByName(tag).orElseGet(() -> tagRepository.save(new Tag(tag))));
        }
        return tagSet;
    }

    @Override
    @Transactional
    public Optional<Tag> updateTag(TagForm tagForm) {
        Tag tag = getTagById(tagForm.getId());
        if (!tagForm.getName().equals(tag.getName())
                && tagRepository.findByName(tagForm.getName()).isPresent()) {
            return Optional.empty();
        }
        mapper.map(tagForm, tag);
        return Optional.of(tagRepository.save(tag));
    }

    @Override
    @Transactional
    public Optional<Tag> saveTag(TagForm tagForm) {
        if (tagRepository.findByName(tagForm.getName()).isPresent()) {
            return Optional.empty();
        }
        Tag tag = mapper.map(tagForm, Tag.class);
        return Optional.of(tagRepository.save(tag));
    }
}
