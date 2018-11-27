package com.github.jrry.blog.service;

import com.github.jrry.blog.entity.ImageEntity;
import org.springframework.data.domain.Page;
import com.github.jrry.blog.forms.ImageForm;

import java.util.Optional;

public interface ImageService {
    Page<ImageEntity> getImages(int page);

    ImageEntity getImageById(Long id);

    Optional<ImageEntity> maybeImageById(Long id);

    ImageForm getImageFormById(Long id);

    void updateImage(ImageForm imageForm);

    void saveImage(ImageForm imageForm);

    ImageEntity createImage(ImageForm imageForm);
}
