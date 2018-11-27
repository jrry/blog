package com.github.jrry.blog.service;

import com.github.jrry.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.jrry.blog.entity.TagEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<TagEntity> getTags() {
        return tagRepository.findAll();
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
}
