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

import com.github.jrry.blog.entity.ImageEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.jrry.blog.common.errors.NotFoundException;
import com.github.jrry.blog.forms.ImageForm;
import com.github.jrry.blog.repository.ImageRepository;
import com.github.jrry.blog.utils.ValidationUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Jarosław Pawłowski
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    @Override
    public Page<ImageEntity> getImages(int page) {
        return ValidationUtils.pageValidation(imageRepository::findAllByOrderByCreatedDesc, page, 12);
    }

    @Override
    public ImageEntity getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Optional<ImageEntity> optionalImageById(Long id) {
        if (Objects.isNull(id))
            return Optional.empty();
        return imageRepository.findById(id);
    }

    @Override
    public ImageForm getImageFormById(Long id) {
        return mapper.map(getImageById(id), ImageForm.class);
    }

    @Override
    @Transactional
    public void updateImage(ImageForm imageForm) {
        ImageEntity imageEntity = getImageById(imageForm.getId());
        mapper.map(imageForm, imageEntity);
        imageEntity.setOwner(userService.getAuthenticatedUser());
        imageRepository.save(imageEntity);
    }

    @Override
    @Transactional
    public void saveImage(ImageForm imageForm) {
        ImageEntity imageEntity = mapper.map(imageForm, ImageEntity.class);
        imageEntity.setOwner(userService.getAuthenticatedUser());
        imageRepository.save(imageEntity);
    }
}
